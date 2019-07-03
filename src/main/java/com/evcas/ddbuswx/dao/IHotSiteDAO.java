package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.HotSite;

import java.util.List;

/**
 * Created by noxn on 2018/3/7.
 */
public interface IHotSiteDAO {

    public List<HotSite> findHotSiteList(String areaMark);

    public void addHotSite(HotSite hotSite);

    public void deleteHotSiteById(String id);
}
