package com.wevioo.pi.service;

import java.util.List;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;

/**
 * Utility class
 *
 *
 *
 */
public interface UtilityService {

	/**
	 * creates a pageable object if no sort attribute is specified no sort is
	 * applied
	 *
	 * @param page     page number
	 * @param pageSize page size
	 * @param sort     Sort
	 * @return Pageable
	 */
	Pageable createPageable(Integer page, Integer pageSize, Sort sort);

	/**
	 * Generates a Sort object based on the sorting criteria, direction, and default
	 * properties.
	 *
	 * @param sort              The criteria for sorting.
	 * @param direction         The direction of sorting (ASCENDING or DESCENDING).
	 * @param defaultProperties The default properties to be used if sort is null.
	 * @return A Sort object based on the provided criteria, direction, and default
	 *         properties.
	 */
	Sort sortingCriteria(Sort sort, Sort.Direction direction, String defaultProperties);

	/**
	 * Checks if the user is authorized based on a list of user types.
	 *
	 * @param userTypeList The list of user types to check authorization against.
	 */
	void isAuthorized(List<UserTypeEnum> userTypeList);

	/**
	 * Checks if the user is authorized based on a list of user types.
	 *
	 * @param userTypeList The list of user types to check authorization against.
	 * @param user         {@link User}
	 */
	void isAuthorized(User user, List<UserTypeEnum> userTypeList);

	void isAuthorizedForRequest(User connectedUser, User creatorUser, Banker affectedTo, RequestStatusEnum requestStatus);
}
