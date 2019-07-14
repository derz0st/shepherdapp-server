package com.paazl.transformer;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface Transformer<T, V> {
    V toDto(T entity);

    default List<V> toDto(List<T> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(toList());
    }
}
