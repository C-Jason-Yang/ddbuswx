package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noxn on 2018/3/21.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public void addUser(User user) {
        if (user.getUserName() != null && user.getUserName().trim() != "" &&
                user.getPassword() != null && user.getPassword().trim() != "") {
            iUserDAO.addUser(user);
        }
    }

    @Override
    public DwzPageModel findUserListPage(DwzPageModel dwzPageModel) {
        return iUserDAO.findUserListPage(dwzPageModel);
    }

    @Override
    public void deleteUserById(String id) {
        iUserDAO.deleteUserById(id);
    }

    @Override
    public User findUserById(String id) {
        return iUserDAO.findUserById(id);
    }
}
