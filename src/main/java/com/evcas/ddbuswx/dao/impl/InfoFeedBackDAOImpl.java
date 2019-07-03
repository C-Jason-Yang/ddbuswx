package com.evcas.ddbuswx.dao.impl;

import com.evcas.ddbuswx.dao.IInfoFeedBackDAO;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.InfoFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
@Repository
public class InfoFeedBackDAOImpl implements IInfoFeedBackDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addInfoFeedBack(InfoFeedBack infoFeedBack) {
        mongoTemplate.insert(infoFeedBack);
    }

    @Override
    public void deleteInfoFeedBackById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, InfoFeedBack.class);
    }

    @Override
    public DwzPageModel findInfoFeedBackList(DwzPageModel dwzPageModel, String areaMark) {
        Query query = new Query();
        query.addCriteria(Criteria.where("areaMark").is(areaMark));
        dwzPageModel.setTotalCount(mongoTemplate.count(query, InfoFeedBack.class));
        query.skip((dwzPageModel.getCurrentPage() - 1) * dwzPageModel.getNumPerPage());
        query.limit(dwzPageModel.getNumPerPage());
        query.with(new Sort(Sort.Direction.DESC, "submitTime"));
        List<InfoFeedBack> infoFeedBackList = mongoTemplate.find(query, InfoFeedBack.class);
        dwzPageModel.setDataList(infoFeedBackList);
        BigDecimal totalCount = new BigDecimal(dwzPageModel.getTotalCount());
        BigDecimal numPerPage = new BigDecimal(dwzPageModel.getNumPerPage());
        dwzPageModel.setTotalPageNum(totalCount.divide(numPerPage, 0, BigDecimal.ROUND_UP).longValue());
        return dwzPageModel;
    }

    @Override
    public InfoFeedBack findInfoFeedBackById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, InfoFeedBack.class);
    }

    @Override
    public void batchDeleteInfoFeedBackById(List<String> listId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(listId));
        mongoTemplate.findAllAndRemove(query, InfoFeedBack.class);
    }
}
