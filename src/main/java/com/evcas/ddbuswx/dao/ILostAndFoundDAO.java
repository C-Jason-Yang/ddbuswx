package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.LostAndFound;
import com.evcas.ddbuswx.model.DwzPageModel;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
public interface ILostAndFoundDAO {

    void addLostAndFound(LostAndFound lostAndFound);

    void deleteLostAndFoundById(String id);

//    DwzPageModel findLostAndFoundList(DwzPageModel dwzPageModel, LostAndFound lostAndFound);
    List<LostAndFound> findLostAndFoundList(String pageNum, String areaId);

    LostAndFound findLostAndFoundById(String id);

    void updateLostAndFoundById(LostAndFound lostAndFound);

    void batchDeleteLostAndFoundById(List<String> listId);
}
