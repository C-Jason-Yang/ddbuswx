package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IInfoFeedBackDAO;
import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.InfoFeedBack;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.IInfoFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
@Service
public class InfoFeedBackServiceImpl implements IInfoFeedBackService {

    @Autowired
    private IInfoFeedBackDAO iInfoFeedBackDAO;

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public void addInfoFeedBack(InfoFeedBack infoFeedBack) {
        iInfoFeedBackDAO.addInfoFeedBack(infoFeedBack);
    }

    @Override
    public void deleteInfoFeedBackById(String id) {
        iInfoFeedBackDAO.deleteInfoFeedBackById(id);
    }

    @Override
    public DwzPageModel findInfoFeedBackList(DwzPageModel pageModel, String userId) {
        User user = iUserDAO.findUserById(userId);
        return iInfoFeedBackDAO.findInfoFeedBackList(pageModel, user.getAreaMark());
    }

    @Override
    public InfoFeedBack findInfoFeedBackById(String id) {
        return iInfoFeedBackDAO.findInfoFeedBackById(id);
    }

    @Override
    public void batchDeleteInfoFeedBackById(List<String> listId) {
        iInfoFeedBackDAO.batchDeleteInfoFeedBackById(listId);
    }
}
