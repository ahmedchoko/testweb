package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.Requester;
import com.wevioo.pi.rest.dto.request.RequesterForPostDto;
import com.wevioo.pi.rest.dto.response.RequesterForGetDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RequesterService {
        /**
         * Retrieves a requester by ID.
         *
         * @param id The ID of the requester to find.
         * @return The RequesterForGetDto object representing the found requester.
         */
        RequesterForGetDto findById(String id);
        /**
         * Updates an existing requester.
         *
         * @param requesterDto The DTO containing updated requester information.
         * @param user          The user performing the update.
         * @param file          The multipart file associated with the requester.
         * @param result        The result of data binding.
         * @return The updated Requester object.
         * @throws IOException If an I/O error occurs.
         */
        Requester updateExistingRequester(RequesterForPostDto requesterDto, User user ,MultipartFile file, BindingResult result) throws IOException;
        /**
         * Saves a new requester.
         *
         * @param requesterDto The DTO containing new requester information.
         * @param user          The user creating the requester.
         * @param file          The multipart file associated with the requester.
         * @param result        The result of data binding.
         * @return The newly saved Requester object.
         * @throws IOException If an I/O error occurs.
         */
        Requester  saveNewRequester(RequesterForPostDto requesterDto, User user   , MultipartFile file  , BindingResult result) throws IOException;
        /**
         * Deletes a requester by ID.
         *
         * @param id The ID of the requester to delete.
         */
        void  deleteRequesterById(String id);
}
