package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.commonEnum.ResFlagEnum;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.common.utils.UuidUtil;
import com.evcas.ddbuswx.entity.ResVo;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.InfoFeedBack;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.IInfoFeedBackService;
import com.evcas.ddbuswx.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noxn on 2018/3/8.
 */
@Controller
@RequestMapping("infoFeedBack")
@ApiIgnore
//@Api(value = "意见反馈", tags = "意见反馈接口类")
public class InfoFeedBackController {

    @Autowired
    private IInfoFeedBackService iInfoFeedBackService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "deleteInfoFeedBackById", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String deleteInfoFeedBackById(String id, HttpServletRequest request, HttpServletResponse response) {
        iInfoFeedBackService.deleteInfoFeedBackById(id);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "findInfoFeedBackList")
    @ApiIgnore
    public ModelAndView findInfoFeedBackList(Long pageNum, HttpServletRequest request, HttpServletResponse response) {
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
                DwzPageModel pageModel = new DwzPageModel();
                if (pageNum == null) {
                    pageNum = 1L;
                }
                pageModel.setCurrentPage(pageNum);
                pageModel = iInfoFeedBackService.findInfoFeedBackList(pageModel, token.getUserId());
                model.setViewName("infoFeedBack/infoFeedBackPage");
                model.addObject("dwzPage", pageModel);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "findInfoFeedBackById")
    @ApiIgnore
    public ModelAndView findInfoFeedBackById(String id) {
        InfoFeedBack infoFeedBack = iInfoFeedBackService.findInfoFeedBackById(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("infoFeedBack/infoFeedBackDetail");
        model.addObject("result", infoFeedBack);
        return model;
    }

    @RequestMapping(value = "batchDeleteInfoFeedBackById", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String batchDeleteInfoFeedBackById(String listIdJson) {
        DwzCallBackResult result = new DwzCallBackResult();
        List<String> listId = JsonTools.gson.fromJson(listIdJson, ArrayList.class);
        if (listId != null && listId.size() > 0) {
            iInfoFeedBackService.batchDeleteInfoFeedBackById(listId);
        }
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }

//    @ApiOperation(value="添加意见反馈")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public ResVo add(@RequestBody InfoFeedBack infoFeedBack) {
        ResVo res = new ResVo();
        infoFeedBack.setId(UuidUtil.getUuid());
        iInfoFeedBackService.addInfoFeedBack(infoFeedBack);
        res.setFlag(ResFlagEnum.Normal.getFlag());
        return res;
    }
}
