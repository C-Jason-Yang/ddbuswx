package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IBusDAO;
import com.evcas.ddbuswx.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/1/11.
 */
@Repository
public class BusDAOImpl implements IBusDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addBusInfoList(List<Bus> busList) {
        mongoTemplate.insertAll(busList);
    }

    @Override
    public void deleteBusInfoByAreaId(String areaId, String fromSys) {
        Query query = new Query(Criteria.where("areaId").is(areaId).and("fromSys").is(fromSys));
        mongoTemplate.remove(query, Bus.class);
    }
}
