package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.model.HotSite;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ISiteService;
import com.evcas.ddbuswx.service.ITokenService;
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
 * Created by noxn on 2018/3/7.
 */
@Controller
@RequestMapping("api")
@ApiIgnore
public class SiteController {

    @Autowired
    private ISiteService iSiteService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "findAllHotSite")
    public ModelAndView findAllHotSite(HttpServletRequest request) {
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
//                List<HotSite> siteList = iSiteService.findHotSiteList(token.getUserId());
                model.setViewName("hotSiteList");
//                model.addObject("siteList", siteList);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "addHotSite",method = RequestMethod.POST)
    @ResponseBody
    public String addHotSite(HotSite hotSite, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        DwzCallBackResult result = new DwzCallBackResult();
        String tokenStr = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                tokenStr = cookie.getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                iSiteService.addHotSite(hotSite, token.getUserId());
                result.setStatusCode(200);
                result.setMessage("添加成功");
                return JsonTools.gson.toJson(result);
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "deleteHotSiteById", method = RequestMethod.POST)
    @ResponseBody
    public String deleteHotSiteById(String id) {
        iSiteService.deleteHotSiteById(id);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        result.setMessage("删除成功");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "toAddHotSitePage", method = RequestMethod.GET)
    public ModelAndView toAddHotSitePage() {
        return new ModelAndView("addHotSite");
    }
}
