package com.tp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tp.entity.Shelf;
import com.tp.orm.hibernate.HibernateDao;

@Component
public class ShelfDao extends HibernateDao<Shelf, Long> {

	private static final String QUERY_BY_NAME="select s from Shelf s where s.name=? and s.store.id=?";
	
	@SuppressWarnings("unchecked")
	public boolean isShelfNameUnique(String newName,String oldName,Long id){
		if(newName.equals(oldName)){
			return true;
		}
		List<Shelf> shelfs=createQuery(QUERY_BY_NAME,newName,id).list();
		if(shelfs.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
}
