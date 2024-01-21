package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.Representative;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepository extends CrudRepository<Representative, Long> {
}
