package it.spasia.dao;

import java.util.List;

public interface DAO<T> {
	T findById(long id);

	List<T> findAll();

	boolean delete(long id, String tableName);

	boolean update(T object);

	long insert(T object);
}
