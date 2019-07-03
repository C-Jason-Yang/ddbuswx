package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IHotSiteDAO;
import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.HotSite;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noxn on 2018/3/7.
 */
@Service
public class SiteServiceImpl implements ISiteService {

    @Autowired
    private IHotSiteDAO iHotSiteDAO;

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public List<HotSite> findHotSiteListById(String areaId) {
//    public List<HotSite> findHotSiteList(String userId) {
//        User user = iUserDAO.findUserById(userId);
        return iHotSiteDAO.findHotSiteList(areaId);
    }

    @Override
    public void addHotSite(HotSite hotSite, String userId) {
        User user = iUserDAO.findUserById(userId);
        hotSite.setAreaMark(user.getAreaMark());
        iHotSiteDAO.addHotSite(hotSite);
    }

    @Override
    public void deleteHotSiteById(String id) {
        iHotSiteDAO.deleteHotSiteById(id);
    }
}
