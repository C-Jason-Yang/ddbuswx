package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;

import java.util.List;

/**
 * Created by noxn on 2018/3/21.
 */
public interface IUserDAO {

    User findUserByUserName(String userName);

    void addUser(User user);

    DwzPageModel findUserListPage(DwzPageModel pageModel);

    void deleteUserById(String id);

    User findUserById(String id);
}
