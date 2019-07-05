package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.common.utils.DateTimeUtil;
import com.evcas.ddbuswx.dao.ILostAndFoundDAO;
import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.LostAndFound;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.ILostAndFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
@Service
public class LostAndFoundServiceImpl implements ILostAndFoundService {

    @Autowired
    private ILostAndFoundDAO iLostAndFoundDAO;

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public void addLostAndFound(LostAndFound lostAndFound, String userId) {
        User user = iUserDAO.findUserById(userId);
        lostAndFound.setAreaMark(user.getAreaMark());
        lostAndFound.setReleaseTime(DateTimeUtil.getCurrentTime(false));
        String content = lostAndFound.getContent();
        content = content.replace("/", "");
        content = content.replace("\\", "");
        content = content.replace(">", "");
        content = content.replace("<", "");
        lostAndFound.setContent(content);
        iLostAndFoundDAO.addLostAndFound(lostAndFound);
    }

    @Override
    public void deleteLostAndFoundById(String id) {
        iLostAndFoundDAO.deleteLostAndFoundById(id);
    }

    @Override
//    public DwzPageModel findLostAndFoundList(DwzPageModel dwzPageModel, LostAndFound lostAndFound, String userId) {
        public List<LostAndFound> findLostAndFoundList(String pageNum, String areaId) {
//        User user = iUserDAO.findUserById(userId);
//        lostAndFound.setAreaMark(user.getAreaMark());
        return iLostAndFoundDAO.findLostAndFoundList(pageNum, areaId);
    }

    @Override
    public LostAndFound findLostAndFoundById(String id) {
        return iLostAndFoundDAO.findLostAndFoundById(id);
    }

    @Override
    public void updateLostAndFoundById(LostAndFound lostAndFound) {
        iLostAndFoundDAO.updateLostAndFoundById(lostAndFound);
    }

    @Override
    public void batchDeleteLostAndFoundById(List<String> listId) {
        iLostAndFoundDAO.batchDeleteLostAndFoundById(listId);
    }


    public static void main(String args[]) {
        String kk = "qweqweqweqw/\\/";
        kk = kk.replace("/", "");
        kk = kk.replace("\\", "");
        System.out.println(kk);
    }
}
