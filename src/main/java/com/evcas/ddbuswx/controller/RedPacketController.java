package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.SystemParameter;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.RedPacketActivity;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.model.User;
import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.IUserService;
import com.evcas.ddbuswx.service.impl.RedPacketActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by noxn on 2018/9/18.
 */
@Controller
@RequestMapping("redPacket")
@EnableAsync
@ApiIgnore
public class RedPacketController {

    @Autowired
    private RedPacketActivityService redPacketActivityService;

    @Autowired
    private ITokenService iTokenService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "toAddPage", method = RequestMethod.GET)
    public ModelAndView toAddPage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                model.setViewName("redPacket/addRedPacket");
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(RedPacketActivity redPacketActivity, String tempRedPacketActivityId, HttpServletRequest request) {
        DwzCallBackResult result = new DwzCallBackResult();
        Cookie[] cookies = request.getCookies();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                redPacketActivityService.addRedPacketActivity(redPacketActivity, tempRedPacketActivityId, token.getUserId());
                result.setStatusCode(200);
                return JsonTools.gson.toJson(result);
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "page")
    public ModelAndView page(Long pageNum, RedPacketActivity redPacketActivity, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                DwzPageModel dwzPageModel = new DwzPageModel();
                if (pageNum == null) {
                    pageNum = 1L;
                }
                dwzPageModel.setCurrentPage(pageNum);
                User user = iUserService.findUserById(token.getUserId());
                dwzPageModel = redPacketActivityService.page(redPacketActivity, dwzPageModel);
                model.setViewName("redPacket/redPacketPage");
                model.addObject("dwzPage", dwzPageModel);
                model.addObject("userName", user.getUserName());
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(String id, String status, HttpServletRequest request) {
        DwzCallBackResult result = new DwzCallBackResult();
        Cookie[] cookies = request.getCookies();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                redPacketActivityService.updateRedPacketActivityStatus(id, status, token.getUserId());
                result.setStatusCode(200);
                return JsonTools.gson.toJson(result);
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "sendRedPacket", method = RequestMethod.POST)
    @ResponseBody
    public String sendRedPacket(String id, HttpServletRequest request) {
        DwzCallBackResult result = new DwzCallBackResult();
        Cookie[] cookies = request.getCookies();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                if (!SystemParameter.RED_PACKET_ID_LIST.contains(id)) {
                    SystemParameter.RED_PACKET_ID_LIST.add(id);
                    redPacketActivityService.sendRedPacket(id, token.getUserId());
                    result.setStatusCode(200);
                    return JsonTools.gson.toJson(result);
                } else {
                    result.setStatusCode(2000);
                    return JsonTools.gson.toJson(result);
                }
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "getInfo")
    public ModelAndView getInfo(String id, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                RedPacketActivity redPacketActivity = redPacketActivityService.getRedPacketInfo(id);
                model.setViewName("redPacket/info");
                model.addObject("redPacketActivity", redPacketActivity);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }
}
