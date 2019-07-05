package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by noxn on 2018/3/21.
 */
@SuppressWarnings("unchecked")
@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User findUserByUserName(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public void addUser(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public DwzPageModel findUserListPage(DwzPageModel dwzPageModel) {
        Query query = new Query();

//        dwzPageModel.setEntity(lostAndFound);
        dwzPageModel.setTotalCount(mongoTemplate.count(query, User.class));
        query.skip((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage());
        query.limit(dwzPageModel.getNumPerPage());
        List<User> userList = mongoTemplate.find(query, User.class);
        dwzPageModel.setDataList(userList);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }

    @Override
    public void deleteUserById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, User.class);
    }

    @Override
    public User findUserById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }
}
