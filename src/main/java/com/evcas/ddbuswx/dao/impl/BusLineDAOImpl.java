package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IBusLineDAO;
import com.evcas.ddbuswx.model.mongo.BusLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by noxn on 2018/1/10.
 */
@Repository
public class BusLineDAOImpl implements IBusLineDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addBusLineList(List<BusLine> busLineList) {
        busLineList.forEach(b -> {
            mongoTemplate.remove(b);
            mongoTemplate.insert(b);
        });


        //mongoTemplate.insertAll(busLineList);
    }

    @Override
    public void deleteBusLineByAreaId(String areaId, String fromSys) {
        Query query = new Query(Criteria.where("areaId").is(areaId).and("fromSys").is(fromSys));
        mongoTemplate.remove(query, BusLine.class);
    }

    @Override
    public List<BusLine> getBusLineByAreaCode(String areaCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaId").is(areaCode).and("fromSys").is("RM"));
        return mongoTemplate.find(query, BusLine.class);
    }

    @Override
    public BusLine getBusLineByLineCode(String lineCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lineCode").is(lineCode));
        return mongoTemplate.findOne(query, BusLine.class);
    }

    @Override
    public BusLine getBusLineByLineName(String lineName, String areaCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lineName").is(lineName).and("fromSys").is("RM").and("areaCode").is(areaCode));
        return mongoTemplate.findOne(query, BusLine.class);
    }

    @Override
    public List<BusLine> queryBusLineByLikeLineName(String lineName, String areaId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("areaId").is(areaId).and("fromSys").is("RM");
        if (lineName != null) {
            Pattern pattern = Pattern.compile("^.*" + lineName + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("lineName").regex(pattern);
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, BusLine.class);
    }

    @Override
    public BusLine queryBusLineByLineCodeAndAreaId(String lineCode, String areaId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lineCode").is(lineCode).and("areaId").is(areaId).and("fromSys").is("RM"));
        return mongoTemplate.findOne(query, BusLine.class);
    }
}
