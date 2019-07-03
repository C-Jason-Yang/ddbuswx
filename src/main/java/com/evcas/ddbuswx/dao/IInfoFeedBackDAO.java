package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.InfoFeedBack;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
public interface IInfoFeedBackDAO {

    void addInfoFeedBack(InfoFeedBack infoFeedBack);

    void deleteInfoFeedBackById(String id);

    DwzPageModel findInfoFeedBackList(DwzPageModel pageModel, String areaMark);

    InfoFeedBack findInfoFeedBackById(String id);

    void batchDeleteInfoFeedBackById(List<String> listId);
}
