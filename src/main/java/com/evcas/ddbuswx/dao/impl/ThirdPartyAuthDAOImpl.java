package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IThirdPartyAuthDAO;
import com.evcas.ddbuswx.model.ThirdPartyAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by noxn on 2018/8/11.
 */
@Repository
public class ThirdPartyAuthDAOImpl implements IThirdPartyAuthDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ThirdPartyAuth getThirdPartyAuth(String ip, String pid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ip").is(ip).and("pid").is(pid));
        return mongoTemplate.findOne(query, ThirdPartyAuth.class);
    }
}
