package com.evcas.ddbuswx.common.init;

import com.evcas.ddbuswx.dao.IBusStationDAO;
import com.evcas.ddbuswx.model.BusStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;

/**
 * Created by noxn on 2019/5/8.
 */
@Order(2)
public class MongoInit implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... strings) throws Exception {
//        mongoTemplate.indexOps(BusStation.class).ensureIndex(new GeospatialIndex("location.coordinates"));
//        boolean isGeoIndex = true;
//        //查询实时GPS信息表索引
//        List<IndexInfo> indexInfoList = mongoTemplate.indexOps(BusStation.class).getIndexInfo();
//        for (int i = 0; i < indexInfoList.size(); i++) {
//            IndexInfo indexInfo = indexInfoList.get(i);
//            //已存在区域空间索引
//            System.out.println(indexInfo.getName());
//            if ("location.coordinates_2d".equals(indexInfo.getName())) {
//                isGeoIndex = false;
//            }
//        }
//        if (isGeoIndex) {
//            //不存在空间索引，创建空间索引
//            mongoTemplate.indexOps(BusStation.class).ensureIndex(new GeospatialIndex("location.coordinates"));
//        }
    }
}
