package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.IUserDAO;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by noxn on 2018/3/21.
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public Map userLogin(String userName, String password) {
        User user = iUserDAO.findUserByUserName(userName);
        Map<String, Object> result = new HashMap<String, Object>();
        if (user != null) {
            if (user.getPassword() != null && !user.getPassword().equals("") && user.getPassword().equals(password)) {
                result.put("userName", userName);
                result.put("userId", user.getId());
                result.put("mark", 1);
            } else {
                //密码不正确
                result.put("mark", 2);
            }
        } else {
            //用户不存在
            result.put("mark", 3);
        }
        return result;
    }
}
