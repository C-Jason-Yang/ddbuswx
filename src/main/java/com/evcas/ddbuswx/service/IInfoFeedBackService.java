package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.InfoFeedBack;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
public interface IInfoFeedBackService {

    public void addInfoFeedBack(InfoFeedBack infoFeedBack);

    public void deleteInfoFeedBackById(String id);

    public DwzPageModel findInfoFeedBackList(DwzPageModel pageModel, String userId);

    public InfoFeedBack findInfoFeedBackById(String id);

    public void batchDeleteInfoFeedBackById(List<String> listId);
}
