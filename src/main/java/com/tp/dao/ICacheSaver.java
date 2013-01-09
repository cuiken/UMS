package com.tp.dao;

import java.util.List;

public interface ICacheSaver<E> {
	
	abstract public void saveList(List<E> e) throws Exception;

}
