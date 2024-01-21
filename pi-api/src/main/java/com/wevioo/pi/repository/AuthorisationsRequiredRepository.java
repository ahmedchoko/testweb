package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.AuthorisationsRequired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorisationsRequiredRepository extends CrudRepository<AuthorisationsRequired, String> {
}
