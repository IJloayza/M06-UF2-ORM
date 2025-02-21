package com.iticbcn.loayzaignacio.DAO;

import java.util.List;

public interface GenericInterfaceDAO<T> {
    void save(T t);
    void update(T t);
    void delete(T t);
    T findById(int id);
    List<T> findAll();
}
