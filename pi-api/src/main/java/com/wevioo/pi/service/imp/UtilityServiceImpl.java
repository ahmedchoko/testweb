package com.wevioo.pi.service.imp;

import java.util.List;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;

/**
 * UtilityServiceImpl
 */

@Service
public class UtilityServiceImpl implements UtilityService {

	/**
	 * Injected bean {@link SecurityUtils}
	 */
	@Autowired
	private SecurityUtils securityUtils;

	/**
	 * Injected bean {@link UserService}
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pageable createPageable(Integer page, Integer pageSize, Sort sort) {
		Pageable pageable = null;
		if (page != null && pageSize != null) {
			if (pageSize < 1 || page < 1)
				throw new BadRequestException(ApplicationConstants.ERROR_INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER);
			pageable = PageRequest.of(page - 1, pageSize, sort);
		} else {
			pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
		}
		return pageable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void isAuthorized(List<UserTypeEnum> userTypeList) {
		String userId = securityUtils.getCurrentUserId();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
						ApplicationConstants.USER_NOT_FOUND + userId));
		if (!(userTypeList.contains(user.getUserType()))) {
			throw new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST);
		}
	}

	/**
	 * @param sort              sort
	 * @param direction         direction
	 * @param defaultProperties defaultProperties
	 * @return Sort
	 */
	@Override
	public Sort sortingCriteria(Sort sort, Sort.Direction direction, String defaultProperties) {
		return Sort.unsorted().equals(sort) || sort == null ? Sort.by(direction, defaultProperties) : sort;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void isAuthorized(User user, List<UserTypeEnum> userTypeList) {
		if (!(userTypeList.contains(user.getUserType()))) {
			throw new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST);
		}

	}

	@Override
	public void isAuthorizedForRequest(User connectedUser, User creatorUser, Banker affectedTo,
									   RequestStatusEnum requestStatus) {
		if ((!connectedUser.equals(creatorUser) && requestStatus == RequestStatusEnum.DRAFT)
				|| (!connectedUser.equals(affectedTo) && requestStatus == RequestStatusEnum.IN_PROGRESS)) {
			throw  new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST,  ApplicationConstants.UNAUTHORIZED_MSG );
		}
	}
}
