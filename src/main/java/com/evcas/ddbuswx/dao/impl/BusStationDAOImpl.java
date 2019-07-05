package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.model.BusStation;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by noxn on 2018/1/10.
 */
@Repository
public class BusStationDAOImpl implements IBusStationDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addBusStation(List<BusStation> busStationList) {
        mongoTemplate.insertAll(busStationList);
    }

    @Override
    public void deleteBusStationByAreaId(String areaId, String fromSys) {
        Query query = new Query(Criteria.where("areaId").is(areaId).and("fromSys").is(fromSys));
        mongoTemplate.remove(query, BusStation.class);
    }

    @Override
    public List<BusStation> getBusStationByLineCode(String lineCode, String direction, String areaCode) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("lineCode").is(lineCode).and("areaId").is(areaCode)
                    .and("direction").is(Integer.valueOf(direction)).and("fromSys").is("RM")).with(new Sort(new Sort.Order(Sort.Direction.ASC,"sitenum")));
            List<BusStation> busStationList = mongoTemplate.find(query, BusStation.class);
            return busStationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BusStation> getNearStationByGps(String lat, String lon, String distance) {
        BasicDBObject basicDBObject = new BasicDBObject("location",
                new BasicDBObject("$near",
                        new BasicDBObject("$geometry",
                                new BasicDBObject("type", "Point")
                                .append("coordinates", new double[] { Double.valueOf(lon), Double.valueOf(lat) })
                                ).append("$maxDistance", Integer.valueOf(distance))));
        Query query = new BasicQuery(String.valueOf(basicDBObject));

//        Point point = new Point(Double.valueOf(lon), Double.valueOf(lat));

//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("location").nearSphere(point).maxDistance(Integer.valueOf(distance))),
//                Aggregation.group("siteName"),
//                Aggregation.limit(10)
//        );
//        AggregationResults<BasicDBObject> outputType = mongoTemplate.aggregate(agg, "busStation", BasicDBObject.class);
//        List<BasicDBObject> busStationList = outputType.getMappedResults();
//        Query query = new Query();
//        query.addCriteria(Criteria.where("location").nearSphere(point).maxDistance(Integer.valueOf(distance)/111.12)).limit(50);
        Set<String> set = new HashSet<>();
        List<BusStation> busStationList = mongoTemplate.find(query, BusStation.class);
        List<BusStation> resultBbusStationList = new ArrayList<>();
        for (BusStation busStation : busStationList) {
            if (set.contains(busStation.getSiteName())) {
                continue;
            }
            if (resultBbusStationList.size() == 10) {
                break;
            }
            resultBbusStationList.add(busStation);
            set.add(busStation.getSiteName());
        }
        return resultBbusStationList;
    }

    @Override
    public List<BusStation> queryStationLikeName(String stationName, String areaId) {
        Criteria criteria = Criteria.where("areaId").is(areaId).and("fromSys").is("RM");
        if (stationName != null) {
            Pattern pattern = Pattern.compile("^.*" + stationName + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("siteName").regex(pattern);
        }

        Aggregation agg = Aggregation.newAggregation(
            Aggregation.match(criteria),
            Aggregation.group("siteName")
        );
        AggregationResults<Document> outputType = mongoTemplate.aggregate(agg, "busStation", Document.class);
        List<Document> busStationList = outputType.getMappedResults();
        List<BusStation> resultBusStationList = new ArrayList<>();
        if (busStationList != null) {
            for (Document document : busStationList) {
                BusStation busStation = new BusStation();
                busStation.setSiteName(document.get("_id").toString());
                resultBusStationList.add(busStation);
            }
        }
//        Query query = Query.query(criteria);
            return resultBusStationList;
    }

    @Override
    public List<BusStation> findAllBusStation(String areaId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaId").is(areaId).and("fromSys").is("RM"));
        return mongoTemplate.find(query, BusStation.class);
    }

    @Override
    public List<BusStation> findBusStationByStationName(String stationName, String direction, String areaId) {
        Criteria criteria = Criteria.where("areaId").is(areaId).and("direction").is(Integer.valueOf(direction)).and("siteName").is(stationName).and("fromSys").is("RM");
//        if (stationName != null) {
//            Pattern pattern = Pattern.compile("^.*" + stationName + ".*$", Pattern.CASE_INSENSITIVE);
//            criteria.and("siteName").regex(pattern);
//        }
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, BusStation.class);
    }

    @Override
    public List<BusStation> findBusStationByStationName(String stationName, String areaId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("siteName").is(stationName).and("areaId").is(areaId).and("fromSys").is("RM"));
        return mongoTemplate.find(query, BusStation.class);
    }


}