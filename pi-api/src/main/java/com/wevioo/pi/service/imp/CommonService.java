package com.wevioo.pi.service.imp;

import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.service.KeyGenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Value("${upload-path}")
    private String basePath;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link AttachmentsRepository}
     */
    @Autowired
    private AttachmentsRepository attachmentsRepository;


    /**
     * @param powerOfAattorneyFile
     * @param investor
     * @param userType
     * @return
     * @throws IOException
     */
    public void createAttachment(MultipartFile powerOfAattorneyFile,
                                                 Investor investor,
                                                 UserTypeEnum userType) throws IOException {


        Path uploadDir = null;
        if (userType != null && investor != null && investor.getId() != null) {
            uploadDir = Paths.get(basePath + File.separator + userType.toString() + File.separator + investor.getId());
        }

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        if (powerOfAattorneyFile != null) {
            String fileName = org.springframework.util.StringUtils
                    .cleanPath(powerOfAattorneyFile.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(powerOfAattorneyFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Files.write(filePath, powerOfAattorneyFile.getBytes());
            Attachment attachment = new Attachment();
            attachment.setId(keyGenService.getNextKey(KeyGenType.ATTACHMENT_KEY , true , null));
            attachment.setFileName(fileName);
            attachment.setContentType(powerOfAattorneyFile.getContentType());
            attachment.setCreationDate(new Date());
            attachment.setFileSize(powerOfAattorneyFile.getInputStream().available());
            attachment.setFileUrl(uploadDir.toString());
            attachment.setInvestor(investor);
            powerOfAattorneyFile.getInputStream().close();
        }
    }

    public Attachment createAttachmentListForRequester(MultipartFile  file,
                                                        Requester  requester) throws IOException {

        Attachment attachment = new Attachment();

        Path uploadDir = null;
        if (requester != null && requester.getId() != null) {
            uploadDir = Paths.get(basePath  + File.separator + requester.getId());
        } else {
            return attachment;
        }

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        if (file != null) {
            String fileName = org.springframework.util.StringUtils
                    .cleanPath(file.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Files.write(filePath, file.getBytes());
            attachment.setId(keyGenService.getNextKey(KeyGenType.ATTACHMENT_KEY , true , null));
            attachment.setFileName(fileName);
            attachment.setContentType(file.getContentType());
            attachment.setCreationDate(new Date());
            attachment.setFileSize(file.getInputStream().available());
            attachment.setFileUrl(uploadDir.toString());
            attachment.setRequester(requester);
            file.getInputStream().close();
        }
        return attachment;
    }

    public void deleteAttachment(Attachment attachment) throws IOException {

        String filePath = attachment.getFileUrl(); // Chemin du fichier
        Path fileToDelete = Paths.get(filePath);
        // Supprimer le fichier
        if (Files.exists(fileToDelete)) {
            FileUtils.deleteDirectory(new File(fileToDelete.toUri()));
        }
    }

    public void deleteAttachmentList(List<Attachment> attachmentList) throws IOException {
       if(attachmentList != null && ! attachmentList.isEmpty()) {
           for (Attachment attachment : attachmentList) {
               deleteAttachment(attachment);
           }
       }
    }

}
