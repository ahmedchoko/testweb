package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * action repository for
 */

@Repository
public interface ActionRepository  extends CrudRepository<Action, Long> {

 /**
  * find First By Fiche Id
  * @param ficheId fiche id
  * @return  Optional  Action
  */
 Optional<Action> findFirstByFicheId(String ficheId);

 /**
  *  find   action by user id
  * @return  Page of action
  */
 @Query("SELECT  action  FROM Action  action " +
         "                WHERE action.userId = :userId " +
         "                ORDER BY COALESCE(action.modificationDate, action.creationDate) DESC, action.creationDate DESC ")

  Page<Action> findByUserIdOrderByDate(@Param("userId") String userId, Pageable pageable);

  void  deleteAllByFicheId(String ficheId);

}
