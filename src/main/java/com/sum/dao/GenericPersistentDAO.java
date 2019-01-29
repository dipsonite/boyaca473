package com.sum.dao;

public interface GenericPersistentDAO<ENTITY, IDENTIFIER> {

    ENTITY create(ENTITY entity);

    ENTITY retrieve(IDENTIFIER id);

    ENTITY update(ENTITY entity);

    void delete(IDENTIFIER id);
}
