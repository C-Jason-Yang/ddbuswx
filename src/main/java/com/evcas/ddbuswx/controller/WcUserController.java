package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.WcUser;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.impl.WcUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by noxn on 2018/9/18.
 */
@Controller
@RequestMapping(value = "wcUser")
@ApiIgnore
public class WcUserController {

    @Autowired
    private WcUserService wcUserService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "page")
    public ModelAndView page(Long pageNum, WcUser wcUser, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token")) {
                tokenStr = cookies[i].getValue();
            }
        }
        if (tokenStr != "") {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && token.getUserId() != "") {
                DwzPageModel dwzPageModel = new DwzPageModel();
                if (pageNum == null) {
                    pageNum = Long.valueOf(1);
                }
                dwzPageModel.setCurrentPage(pageNum);

                dwzPageModel = wcUserService.page(wcUser, dwzPageModel);
                model.setViewName("wcUser/wcUserPage");
                model.addObject("dwzPage", dwzPageModel);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "getWcUserInfoById")
    public ModelAndView getWcUserInfoById(String id, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token")) {
                tokenStr = cookies[i].getValue();
            }
        }
        if (tokenStr != "") {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && token.getUserId() != "") {

                WcUser wcUser = wcUserService.getWcUserInfoById(id);
                model.setViewName("wcUser/wcUserInfo");
                model.addObject("wcUser", wcUser);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "removeBusCardBinding")
    @ResponseBody
    public String removeBusCardBinding(String wcOpenid, HttpServletRequest request) {
        DwzCallBackResult result = new DwzCallBackResult();
        Cookie[] cookies = request.getCookies();
        String tokenStr = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token")) {
                tokenStr = cookies[i].getValue();
            }
        }
        if (tokenStr != "") {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && token.getUserId() != "") {
                wcUserService.removeBusCardBinding(wcOpenid, token.getUserId());
                result.setStatusCode(200);
                return JsonTools.gson.toJson(result);
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }
}
