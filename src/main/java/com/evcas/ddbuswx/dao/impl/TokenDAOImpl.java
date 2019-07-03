package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.ITokenDAO;
import com.evcas.ddbuswx.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by noxn on 2018/3/28.
 */
@Repository
public class TokenDAOImpl implements ITokenDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertToken(Token token) {
        mongoTemplate.insert(token);
    }

    @Override
    public void deleteAllToken() {
        mongoTemplate.remove(Token.class);
    }

    @Override
    public void deleteTokenByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, Token.class);
    }

    @Override
    public Token findTokenByToken(String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));
        return mongoTemplate.findOne(query, Token.class);
    }

    @Override
    public void deleteTokenByToken(String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));
        mongoTemplate.findAndRemove(query, Token.class);
    }
}
