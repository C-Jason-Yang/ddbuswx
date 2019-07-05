package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.entity.WcUser;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.impl.BusCardBindingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static java.lang.Long.valueOf;

/**
 * Created by noxn on 2018/9/23.
 */
@Controller
@RequestMapping("busCardBindingLog")
@ApiIgnore
public class BusCardBindingLogController {

    @Autowired
    private BusCardBindingLogService busCardBindingLogService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "page")
    public ModelAndView page(Long pageNum, String openid, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ModelAndView model = new ModelAndView();
        String tokenStr = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token")) {
                tokenStr = cookies[i].getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null &&  !"".equals(token.getUserId())) {
                DwzPageModel dwzPageModel = new DwzPageModel();
                if (pageNum == null) {
                    pageNum = 1L;
                }
                dwzPageModel.setCurrentPage(pageNum);

                dwzPageModel = busCardBindingLogService.page(dwzPageModel, openid);
                model.setViewName("wcUser/busCardBindingLogPage");
                model.addObject("dwzPage", dwzPageModel);
                model.addObject("openid", openid);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }
}
