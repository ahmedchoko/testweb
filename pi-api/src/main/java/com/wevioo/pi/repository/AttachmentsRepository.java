package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.Attachment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentsRepository extends CrudRepository<Attachment, String> {

	/**
	 * Delete attachment related to requester
	 * 
	 * @param id requester's id
	 */
	@Modifying
	@Query("DELETE FROM   Attachment a WHERE a.requester.id = :id")
	void deleteAllByRequesterId(String id);

	/**
	 * Find all attachment related to requester
	 * 
	 * @param id requester's id
	 * @return List<Attachment>
	 */
	List<Attachment> findAllByRequesterId(String id);

	/**
	 * Find   attachment related to requester
	 *
	 * @param id requester's id
	 * @return Attachment
	 */
	Attachment  findByRequesterId (String id);




	/**
	 * Find all attachment related to Request
	 * 
	 * @param id request's id
	 * @return List<Attachment>
	 */
	List<Attachment> findByIdDeclarationNature(String id);

	/**
	 * Find all attachment related to section/document/ Request,
	 * 
	 * @param documentId          Document's id
	 * @param sectionId           Section's id
	 * @param idDeclarationNature Request's id
	 * @return List<Attachment>
	 */
	List<Attachment> findByDocumentIdAndSectionIdAndIdDeclarationNature(Long documentId, Long sectionId,
			String idDeclarationNature);

}
