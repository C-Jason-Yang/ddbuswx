package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IBusShiftDAO;
import com.evcas.ddbuswx.model.mongo.BusShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/1/15.
 */
@Repository
public class BusShiftDAOImpl implements IBusShiftDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addBufShiftList(List<BusShift> busShiftList) {
        mongoTemplate.insertAll(busShiftList);
    }

    @Override
    public void deleteBusShift(String areaId, String fromSys) {
        Query query = new Query(Criteria.where("areaid").is(areaId).and("fromSys").is(fromSys));
        mongoTemplate.remove(query, BusShift.class);
    }

    @Override
    public List<BusShift> getBusShiftByLineId(String areaId, String lineId, Integer direction) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lineId").is(lineId).and("areaid").is(areaId).and("biztype").is(direction));
        query.with(new Sort(Sort.Direction.ASC, "stime"));
        return mongoTemplate.find(query, BusShift.class);
    }
}
