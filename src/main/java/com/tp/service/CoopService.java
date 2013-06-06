package com.tp.service;

import com.tp.dao.CoopDao;
import com.tp.entity.Coop;
import com.tp.orm.Page;
import com.tp.orm.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: ken.cui
 * Date: 13-6-6
 * Time: 上午11:13
 */
@Component
@Transactional
public class CoopService {
    private CoopDao coopDao;

    public Coop get(Long id){
        return coopDao.get(id);
    }
    public void save(Coop entity){
        coopDao.save(entity);
    }
    public void delete(Long id){
        coopDao.delete(id);
    }

    public Page<Coop> searchPage(final Page<Coop> page,final List<PropertyFilter> filters){
        return coopDao.findPage(page,filters);
    }

    public boolean isCoopUnique(String newValue,String oldValue){
        return coopDao.isPropertyUnique("value",newValue,oldValue);
    }

    @Autowired
    public void setCoopDao(CoopDao coopDao) {
        this.coopDao = coopDao;
    }
}
