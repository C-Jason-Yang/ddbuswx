package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IHotSiteDAO;
import com.evcas.ddbuswx.model.mongo.HotSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/3/7.
 */
@Repository
public class HotSiteDAOImpl implements IHotSiteDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<HotSite> findHotSiteList(String areaMark) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaMark").is(areaMark));
        return mongoTemplate.find(query, HotSite.class, "evcas_hot_bus_site");
    }

    @Override
    public void addHotSite(HotSite hotSite) {
        mongoTemplate.insert(hotSite, "evcas_hot_bus_site");
    }

    @Override
    public void deleteHotSiteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAndRemove(query, HotSite.class, "evcas_hot_bus_site");
    }
}
