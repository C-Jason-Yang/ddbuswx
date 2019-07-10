package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.mongo.HotSite;

import java.util.List;

/**
 * Created by noxn on 2018/3/7.
 */
public interface ISiteService {

    public List<HotSite> findHotSiteListById(String areaId);

    public void addHotSite(HotSite hotSite, String userId);

    public void deleteHotSiteById(String id);
}
