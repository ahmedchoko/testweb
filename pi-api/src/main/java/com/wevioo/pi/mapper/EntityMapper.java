package com.wevioo.pi.mapper;

import java.util.List;
import java.util.Set;

/**
 * Generic Mapper
 * @param <D> dto class
 * @param <E> entity class
 */
public interface EntityMapper <D, E>{

    D toDto(E entity);
    E toEntity(D dto);
    List<D> toDto(List<E> entityList);
    List<E>  toEntity(List<D> dtoList);
    Set<D> toDto(Set<E> entityList);
    Set<E>  toEntity(Set<D> dtoList);
}
