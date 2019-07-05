package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.IUserService;
import com.google.common.base.Strings;
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
        if (!Strings.isNullOrEmpty(user.getUserName()) && !Strings.isNullOrEmpty(user.getPassword())) {
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
