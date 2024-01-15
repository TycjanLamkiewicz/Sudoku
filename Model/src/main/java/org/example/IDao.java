package org.example;

import org.example.exceptions.DaoException;

public interface IDao<T> extends AutoCloseable {
    T read() throws DaoException;

    void write(T obj) throws DaoException;
}
