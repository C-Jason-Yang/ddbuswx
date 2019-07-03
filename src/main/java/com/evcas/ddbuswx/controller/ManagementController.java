package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.DateTimeUtil;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ILoginService;
import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.IWxBusDataInitService;
import com.google.gson.Gson;
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
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    private ILoginService iLoginService;

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
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("token")) {
                    tokenStr = cookies[i].getValue();
                }
            }
        }
        if (tokenStr != "") {
            iTokenService.deleteToken(tokenStr);
        }
        model.setViewName("loginErrorPage");
        return model;
    }
}
