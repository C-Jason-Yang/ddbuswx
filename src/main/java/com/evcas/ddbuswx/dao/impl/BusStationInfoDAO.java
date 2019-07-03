package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.model.BusStationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2019/5/15.
 */
@Repository
public class BusStationInfoDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void batchInsert(List<BusStationInfo> busStationInfoList) {
        mongoTemplate.insertAll(busStationInfoList);
    }
}
