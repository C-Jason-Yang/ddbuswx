package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.LostAndFound;
import com.evcas.ddbuswx.model.DwzPageModel;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
public interface ILostAndFoundService {

    void addLostAndFound(LostAndFound lostAndFound, String userId);

    void deleteLostAndFoundById(String id);

//    public DwzPageModel findLostAndFoundList(DwzPageModel dwzPageModel, LostAndFound lostAndFound, String userId);
    List<LostAndFound> findLostAndFoundList(String pageNum, String areaId);

    LostAndFound findLostAndFoundById(String id);

    void updateLostAndFoundById(LostAndFound lostAndFound);

    void batchDeleteLostAndFoundById(List<String> listId);
}
