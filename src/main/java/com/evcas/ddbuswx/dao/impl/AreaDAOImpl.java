package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IAreaDAO;
import com.evcas.ddbuswx.model.mongo.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/8/11.
 */
@Repository
public class AreaDAOImpl implements IAreaDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Area getAreaByAreaCode(String areaCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaCode").is(areaCode));
        return mongoTemplate.findOne(query, Area.class);
    }

    @Override
    public Area getAreaByAreaId(String areaId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaOldCode").is(areaId));
        return mongoTemplate.findOne(query, Area.class);
    }

    @Override
    public void initArea(List<Area> list) {
        mongoTemplate.dropCollection(Area.class);
        mongoTemplate.insertAll(list);
    }
}
