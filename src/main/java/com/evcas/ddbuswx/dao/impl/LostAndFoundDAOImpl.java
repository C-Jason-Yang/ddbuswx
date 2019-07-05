package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.ILostAndFoundDAO;
import com.evcas.ddbuswx.model.LostAndFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
@Repository
public class LostAndFoundDAOImpl implements ILostAndFoundDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addLostAndFound(LostAndFound lostAndFound) {
        mongoTemplate.insert(lostAndFound);
    }

    @Override
    public void deleteLostAndFoundById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAndRemove(query, LostAndFound.class);
    }

    @Override
//    public DwzPageModel findLostAndFoundList(DwzPageModel dwzPageModel, LostAndFound lostAndFound) {
    public List<LostAndFound> findLostAndFoundList(String pageNum, String areaId) {
        Query query = new Query();
//        if (!StringUtil.isEmpty(lostAndFound.getTitle())) {
//            query.addCriteria(Criteria.where("title").regex(lostAndFound.getTitle()));
//        }
//        if (!StringUtil.isEmpty(lostAndFound.getLostTime().trim())) {
//
//        }
        query.addCriteria(Criteria.where("areaMark").is(areaId));
//        dwzPageModel.setEntity(lostAndFound);
//        dwzPageModel.setTotalCount(mongoTemplate.count(query, LostAndFound.class));
        query.skip((Integer.parseInt(pageNum) - 1) * 10);
        query.limit(10);
        query.with(new Sort(Sort.Direction.DESC, "releaseTime"));
        return mongoTemplate.find(query, LostAndFound.class);
//        dwzPageModel.setDataList(lostAndFoundList);
//        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
//        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
//        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
//        return dwzPageModel;
    }

    @Override
    public LostAndFound findLostAndFoundById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, LostAndFound.class);
    }

    @Override
    public void updateLostAndFoundById(LostAndFound lostAndFound) {
        Query query = new Query();
        Update update = new Update();
        query.addCriteria(Criteria.where("id").is(lostAndFound.getId()));
//        update.set("areaMark", lostAndFound.getAreaMark());
        update.set("content", lostAndFound.getContent());
        update.set("downShelvesTime", lostAndFound.getDownShelvesTime());
        update.set("title", lostAndFound.getTitle());
        update.set("lostTime", lostAndFound.getLostTime());
        update.set("status", lostAndFound.getStatus());
        mongoTemplate.updateFirst(query, update, LostAndFound.class);
    }

    @Override
    public void batchDeleteLostAndFoundById(List<String> listId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(listId));
        mongoTemplate.findAllAndRemove(query, LostAndFound.class);
    }
}
