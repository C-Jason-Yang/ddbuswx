package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by noxn on 2018/1/10.
 */
@Controller
@RequestMapping("api")
@ApiIgnore
public class ManagementController {

    @Autowired
    private IWxBusDataInitService iWxBusDataInitService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "wxBusDataInit", method = RequestMethod.GET)
    @ResponseBody
    public String wxBusDataInit() {
        iWxBusDataInitService.wxBusDataInit();
        return "success";
    }

    @RequestMapping(value = "loginOut")
    public ModelAndView loginOut(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String tokenStr = "";
        ModelAndView model = new ModelAndView();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    tokenStr = cookie.getValue();
                }
            }
        }
        if (!tokenStr.equals("")) {
            iTokenService.deleteToken(tokenStr);
        }
        model.setViewName("loginErrorPage");
        return model;
    }
}
