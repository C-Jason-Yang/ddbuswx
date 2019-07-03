package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by noxn on 2018/3/21.
 */
@Controller
@RequestMapping(value = "api")
@ApiIgnore
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(User user) {
        iUserService.addUser(user);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "toAddUser", method = RequestMethod.GET)
    public ModelAndView toAddUser() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user/addUserPage");
        return model;
    }

    @RequestMapping(value = "findUserListPage")
    public ModelAndView findUserListPage(DwzPageModel dwzPageModel) {
        ModelAndView model = new ModelAndView();
        dwzPageModel = iUserService.findUserListPage(dwzPageModel);
        model.setViewName("user/userPage");
        model.addObject("dwzPage", dwzPageModel);
        return model;
    }

    @RequestMapping(value = "deleteUserById", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUserById(String id) {
        iUserService.deleteUserById(id);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }
}
