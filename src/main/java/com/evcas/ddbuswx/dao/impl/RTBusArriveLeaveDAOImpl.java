package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IRTBusArriveLeaveDAO;
import com.evcas.ddbuswx.model.RTBusArriveLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/1/31.
 */
@Repository
public class RTBusArriveLeaveDAOImpl implements IRTBusArriveLeaveDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<RTBusArriveLeave> findAllRTBusArriveLeave() {
        return mongoTemplate.findAll(RTBusArriveLeave.class, "rTBusArriveLeave");
    }

    @Override
    public void deleteRTBusArriveLeaveByBusTag(String areaId, String fromSys, String busTag) {
        Query query = new Query(Criteria.where("areaId").is(areaId).and("fromSys").is(fromSys).and("bugtag").is(busTag));
        mongoTemplate.remove(query, "rTBusArriveLeave");
    }

    @Override
    public List<RTBusArriveLeave> findRTBusArriveLeaveBy(String areaId, String lineCode, String direction) {
        Query query = new Query(Criteria.where("line").is(Integer.valueOf(lineCode)).and("areaId").is(areaId).and("biztype").is(Integer.valueOf(direction)));
        return mongoTemplate.find(query, RTBusArriveLeave.class);
    }
}
