package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.DateTimeUtil;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ILoginService;
import com.evcas.ddbuswx.service.ITokenService;
import com.google.common.base.Strings;
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
 * Created by noxn on 2018/3/6.
 */
@Controller
@ApiIgnore
public class LoginController {

    @Autowired
    private ITokenService iTokenService;

    @Autowired
    private ILoginService iLoginService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "toReLogin")
    @ResponseBody
    public String toReLogin() {
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ModelAndView index(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        Map loginResult = null;
        String tokenStr = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    tokenStr = cookie.getValue();
                }
            }
        }
        if (!Strings.isNullOrEmpty(tokenStr)) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token == null || token.getToken() == null || token.getToken().equals("")) {
                tokenStr = "";
            } else {
                tokenStr = token.getToken();
            }
        }
        if (tokenStr.equals("")) {
            loginResult = iLoginService.userLogin(userName, password);
            if (loginResult.get("mark").equals(1)) {
                tokenStr = UuidUtil.getUuid();
                Token token = new Token();
                token.setCreateTime(DateTimeUtil.getCurrentTime(true));
                token.setToken(tokenStr);
                token.setUserId(loginResult.get("userId").toString());
                iTokenService.addToken(token);
            }
        }
        if (!tokenStr.equals("") || loginResult.get("mark").equals(1)) {
            model.addObject("userName", userName);
            model.setViewName("index");
        } else if (loginResult.get("mark").equals(2)) {
            model.setViewName("loginErrorPage");
            model.addObject("loginError", 1);
        } else {
            model.setViewName("loginErrorPage");
            model.addObject("loginError", 2);
        }
        Cookie cookie = new Cookie("token", tokenStr);
        cookie.setPath("ddbus/api");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return model;
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "relogin")
    @ResponseBody
    public String relogin(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Map loginResult = null;
        String tokenStr = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    tokenStr = cookie.getValue();
                }
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token == null || token.getToken() == null || token.getToken().equals("")) {
                tokenStr = "";
            } else {
                tokenStr = token.getToken();
            }
        }
        Token token = null;
        if (tokenStr.equals("")) {
            loginResult = iLoginService.userLogin(userName, password);
            if (loginResult.get("mark").equals(1)) {
                tokenStr = UuidUtil.getUuid();
                token = new Token();
                token.setCreateTime(DateTimeUtil.getCurrentTime(true));
                token.setToken(tokenStr);
                token.setUserId(loginResult.get("userId").toString());
                iTokenService.addToken(token);
            }
        }
        DwzCallBackResult result = new DwzCallBackResult();
        if (!tokenStr.equals("") || loginResult.get("mark").equals(1)) {
            result.setStatusCode(200);
            result.setMessage("登录成功");
            result.setCallbackType("closeCurrent");
            if (token == null) {
                throw new NullPointerException("token is null");
            }
            result.setRel(token.getToken());
        } else {
            result.setStatusCode(300);
            result.setMessage("登录失败");
            result.setCallbackType("closeCurrent");
        }
        Cookie cookie = new Cookie("token", tokenStr);
        cookie.setPath("/ddbus/api");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return JsonTools.gson.toJson(result);
    }
}
