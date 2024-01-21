package com.wevioo.pi.repository;

import org.springframework.data.repository.CrudRepository;

import com.wevioo.pi.domain.entity.config.Document;

/**
*
* This interface represents a repository for the Document entity, providing data access and persistence operations.
*
*/
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
