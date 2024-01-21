package com.wevioo.pi.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Claim;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import com.wevioo.pi.domain.enumeration.Language;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.ConflictException;
import com.wevioo.pi.mapper.AttachmentsMapper;
import com.wevioo.pi.mapper.ClaimMapper;
import com.wevioo.pi.repository.ClaimRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.rest.dto.ClaimForGetDto;
import com.wevioo.pi.rest.dto.ClaimForPostDto;
import com.wevioo.pi.rest.dto.ClaimPublicForPostDto;
import com.wevioo.pi.rest.dto.ClaimForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.AttachmentService;
import com.wevioo.pi.service.ClaimService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParameterService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.ClaimValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * service  managing claims.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class ClaimServiceImpl implements ClaimService {

    /**
     * Injected bean {@link SecurityUtils }
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * Injected bean {@link UserRepository }
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injected bean {@link AttachmentService }
     */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * Injected bean {@link ObjectMapper }
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Injected bean {@link ClaimRepository }
     */
    @Autowired
    private ClaimRepository claimRepository;

    /**
     * Injected bean {@link KeyGenService }
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link ClaimMapper }
     */
    @Autowired
    private ClaimMapper claimMapper;

    /**
     * Injected bean {@link AttachmentsMapper }
     */
    @Autowired
    private AttachmentsMapper attachmentsMapper;

    /**
     * Injected bean {@link UserService }
     */
    @Autowired
    private UserService userService;

    /**
     * Injected bean {@link UtilityService }
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Injected bean {@link ParameterService }
     */
    @Autowired
    private ParameterService parameterService;


    @Override
    @Transactional
    public ClaimForGetDto addClaim(String paramClaimForPostDto, MultipartFile file) throws IOException {
        log.info("start creation of claim");
        String idUser = securityUtils.getCurrentUserId();
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + idUser));

        // admin bct not allowed to create new claim
        if (UserTypeEnum.BCT_ADMIN == user.getUserType()) {
            throw new UnauthorizedException("403");
        }

        ClaimForPostDto claimForPostDto = objectMapper.readValue(paramClaimForPostDto, ClaimForPostDto.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(claimForPostDto, "claimForPostDto");

        ClaimValidator.validateClaim(claimForPostDto, bindingResult);

        if (file != null) {
            Parameter paramSize = parameterService.getByKey(ApplicationConstants.FILE_SIZE_LIMIT_PROPERTY);
            int limitSize = Integer.parseInt(paramSize.getParameterValue());

            // Check if the file size is larger than  X MB
            if (file.getInputStream().available() >= limitSize * 1024 * 1024) { // X MB in bytes
                throw new ConflictException(ApplicationConstants.FILE_SIZE_LIMIT_EXCEEDED, ApplicationConstants.ERROR_IN_SIZE_FILE + Math.floor((double) file.getInputStream().available() / (1024 * 1024)) + " MB.");
            }
        }

        Claim claim = claimMapper.toClaim(claimForPostDto);
        claim.setDepository(user);
        claim.setEmail(user.getLogin());

        final String claimId = keyGenService.getNextKey(KeyGenType.CLAIM_KEY, true, null);
        claim.setId(claimId);


        claim.setReason(claimForPostDto.getReason());
        claim.setCellPhone(claimForPostDto.getCellPhone());
        claim.setDescription(claimForPostDto.getDescription());
        claim.setStatus(ClaimStatusEnum.IN_PROGRESS);
        claim.setUserNatureFr(userService.getNatureForClaim(user, Language.FR));
        claim.setUserNatureEn(userService.getNatureForClaim(user, Language.EN));
        claim.setFirstName(user.getFirstName());
        claim.setLastName(user.getLastName());

        Claim savedClaim = claimRepository.save(claim);
        if (file != null) {
            Attachment attachment = attachmentService.createAttachmentForClaim(file, savedClaim.getId());
            savedClaim.setAttachment(attachment);
            savedClaim = claimRepository.save(savedClaim);
            return claimMapper.toClaimDto(claimRepository.save(savedClaim));
        }
        return claimMapper.toClaimDto(savedClaim);
    }

    @Override
    @Transactional
    public ClaimForGetDto addPublicClaim(String paramClaimForPostDto, MultipartFile file) throws IOException {
        log.info("start creation of public claim");

        ClaimPublicForPostDto claimForPostDto = objectMapper.readValue(paramClaimForPostDto,
                ClaimPublicForPostDto.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(claimForPostDto, "claimForPostDto");

        ClaimValidator.validateClaimPublic(claimForPostDto, bindingResult);

        if (file != null) {
            Parameter paramSize = parameterService.getByKey(ApplicationConstants.FILE_SIZE_LIMIT_PROPERTY);
            int limitSize = Integer.parseInt(paramSize.getParameterValue());

            // Check if the file size is larger than  X MB
            if (file.getInputStream().available() >= limitSize * 1024 * 1024) { // X MB in bytes
                throw new ConflictException(ApplicationConstants.FILE_SIZE_LIMIT_EXCEEDED, ApplicationConstants.ERROR_IN_SIZE_FILE + Math.floor((double) file.getInputStream().available() / (1024 * 1024)) + " MB.");
            }
        }

        Claim claim = claimMapper.toClaim(claimForPostDto);
        final String claimId = keyGenService.getNextKey(KeyGenType.CLAIM_KEY, true, null);
        claim.setId(claimId);

        claim.setReason(claimForPostDto.getReason());
        claim.setDescription(claimForPostDto.getDescription());
        claim.setEmail(claimForPostDto.getEmail());
        claim.setStatus(ClaimStatusEnum.IN_PROGRESS);
        claim.setUserNatureFr("Visiteur étrangers");
        claim.setUserNatureEn("Public visitors");
        claim.setFirstName(claimForPostDto.getFirstName());
        claim.setLastName(claimForPostDto.getLastName());

        claim.setId(keyGenService.getNextKey(KeyGenType.CLAIM_KEY, true, null));
        Claim savedClaim = claimRepository.save(claim);
        if (file != null) {
            Attachment attachment = attachmentService.createAttachmentForClaim(file, claim.getId());
            savedClaim.setAttachment(attachment);
            return claimMapper.toClaimDto(claimRepository.save(savedClaim));
        }
        return claimMapper.toClaimDto(savedClaim);
    }

    @Override
    @Transactional
    public ClaimForGetDto treatClaim(ClaimForPutDto claimForPutDto, BindingResult result) {
        Claim claim = claimRepository.findById(claimForPutDto.getId())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.CLAIM_NOT_FOUND,
                        ApplicationConstants.CLAIM_NOT_FOUND_ERROR + claimForPutDto.getId()));

        String idUser = securityUtils.getCurrentUserId();

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + idUser));

        // only agent_bct is allowed to treat a claim
        if (UserTypeEnum.BCT_ADMIN != user.getUserType()) {
            throw new UnauthorizedException("403");
        }

        if (StringUtils.isEmpty(claimForPutDto.getResponse())) {
            result.rejectValue("response", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            throw new BadRequestException("400", result);
        }

        claim.setResponse(claimForPutDto.getResponse());
        claim.setModificationDate(new Date());
        claim.setStatus(ClaimStatusEnum.RESOLVED);
        claim.setModifiedBy(user);

        claimMapper.toClaimDto(claimRepository.save(claim));
        ClaimForGetDto claimDto = claimMapper.toClaimDto(claimRepository.save(claim));
        claimDto.setAttachment(attachmentsMapper.toAttachmentRequestDto(claim.getAttachment()));
        return claimDto;
    }

    @Override
    public ClaimForGetDto getClaim(String id) {
        String idUser = securityUtils.getCurrentUserId();
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,

                        ApplicationConstants.USER_NOT_FOUND + idUser));
        Optional<Claim> claim = claimRepository.findById(id);
        if (!claim.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.CLAIM_NOT_FOUND,
                    ApplicationConstants.CLAIM_NOT_FOUND_ERROR + id);
        }
        if (UserTypeEnum.BCT_ADMIN.equals(user.getUserType())
                || (Objects.equals(user.getId(), claim.get().getDepository() != null ? claim.get().getDepository().getId() : ""))) {
            ClaimForGetDto claimDto = claimMapper.toClaimDto(claim.get());
            claimDto.setAttachment(attachmentsMapper.toAttachmentRequestDto(claim.get().getAttachment()));
            return claimDto;
        } else {
            throw new UnauthorizedException("403");
        }
    }

    @Override
    @Transactional
    public void changeStatusClaim(String claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.CLAIM_NOT_FOUND,
                        ApplicationConstants.CLAIM_NOT_FOUND_ERROR + claimId));

        claim.setStatus(ClaimStatusEnum.RESOLVED);
        claimRepository.save(claim);
    }


    @Override
    public PaginatedResponse<ClaimForGetDto> getAllClaims(UserTypeEnum userType, String ref, ClaimStatusEnum status, String creationDate, Integer page, Integer pageSize, Sort sort) {
        String idUser = securityUtils.getCurrentUserId();
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + idUser));

        log.info("start getting closed claim list");
        PaginatedResponse<ClaimForGetDto> response = new PaginatedResponse<>();
        Sort sortingCriteria = utilityService.sortingCriteria(sort, Sort.Direction.DESC,
                ApplicationConstants.CREATION_DATE);
        Pageable pageable = utilityService.createPageable(page != null ? page : 1, pageSize != null ? pageSize : 10,
                sortingCriteria);
        if (ref != null)
            ref = ref.toLowerCase();
        Page<Claim> claimList;
        if (UserTypeEnum.BCT_ADMIN == user.getUserType()) {
            if (userType != null) {
                if (userType == UserTypeEnum.BANKER) {
                    claimList = claimRepository.findByCriteriaForBankers(ref, status, creationDate, null, pageable);
                } else {
                    claimList = claimRepository.findByCriteria(userType, ref, status, creationDate, null, pageable);
                }
            } else {
                claimList = claimRepository.findByCriteriaToPublicClaims(ref, status, creationDate, pageable);
            }

        } else {
            claimList = claimRepository.findByCriteria(null, ref, status, creationDate, user.getId(), pageable);
        }

        response.setTotalElement(claimList.getTotalElements());
        response.setTotalPage(claimList.getTotalPages());
        response.setPageSize(claimList.getSize());
        response.setPage(claimList.getNumber());
        response.setContent(claimMapper.toClaimForGetDtoList(claimList.getContent()));
        return response;
    }

}
