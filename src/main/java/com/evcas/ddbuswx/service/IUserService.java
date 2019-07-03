package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;

import java.util.List;

/**
 * Created by noxn on 2018/3/21.
 */
public interface IUserService {

    void addUser(User user);

    DwzPageModel findUserListPage(DwzPageModel dwzPageModel);

    void deleteUserById(String id);

    User findUserById(String id);
}
