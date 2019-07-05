package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.commonEnum.ResFlagEnum;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.ResVo;
import com.evcas.ddbuswx.model.LostAndFound;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ILostAndFoundService;
import com.evcas.ddbuswx.service.ITokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
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
@RequestMapping("lostAndFound")
@ApiIgnore
//@Api(value = "失物招领", tags = "失物招领接口类")
public class LostAndFoundController {

    @Autowired
    private ILostAndFoundService iLostAndFoundService;

    @Autowired
    private ITokenService iTokenService;

    @RequestMapping(value = "addLostAndFound", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String addLostAndFound(LostAndFound lostAndFound, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        DwzCallBackResult result = new DwzCallBackResult();
        String tokenStr = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token")) {
                tokenStr = cookies[i].getValue();
            }
        }
        if (!tokenStr.equals("")) {
            Token token = iTokenService.findTokenByToken(tokenStr);
            if (token != null && token.getUserId() != null && !token.getUserId().equals("")) {
                iLostAndFoundService.addLostAndFound(lostAndFound, token.getUserId());
                result.setStatusCode(200);
                return JsonTools.gson.toJson(result);
            }
        }
        result.setStatusCode(301);
        result.setMessage("回话超时");
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "deleteLostAndFoundById", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String deleteLostAndFoundById(String id, HttpServletRequest request, HttpServletResponse response) {
        iLostAndFoundService.deleteLostAndFoundById(id);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "findLostAndFoundList", method = RequestMethod.GET)
    @ResponseBody
//    @ApiOperation(value="查询失物招领", notes="分页，默认10条一页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNum", value = "分页页数", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String")
//    })
    @ApiIgnore
    public ResVo findLostAndFoundList(String pageNum, String areaId) {
        ResVo res = new ResVo();
        List<LostAndFound> lostAndFoundList = iLostAndFoundService.findLostAndFoundList(pageNum, areaId);
        res.setFlag(ResFlagEnum.Normal.getFlag());
        res.setData(lostAndFoundList);
        return res;
//    public ModelAndView findLostAndFoundList(Long pageNum, LostAndFound lostAndFound, HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//        ModelAndView model = new ModelAndView();
//        String tokenStr = "";
//        for (int i = 0; i < cookies.length; i++) {
//            if (cookies[i].getName().equals("token")) {
//                tokenStr = cookies[i].getValue();
//            }
//        }
//        if (tokenStr != "") {
//            Token token = iTokenService.findTokenByToken(tokenStr);
//            if (token != null && token.getUserId() != null && token.getUserId() != "") {
//                DwzPageModel dwzPageModel = new DwzPageModel();
//                if (pageNum == null) {
//                    pageNum = Long.valueOf(1);
//                }
//                dwzPageModel.setCurrentPage(pageNum);
//                dwzPageModel = iLostAndFoundService.findLostAndFoundList(dwzPageModel, lostAndFound, token.getUserId());
//                model.setViewName("lostAndFound/lostAndFoundPage");
//                model.addObject("dwzPage", dwzPageModel);
//                return model;
//            }
//        }
//        model.setViewName("redirect:/toReLogin");
//        return model;
    }

    @RequestMapping(value = "toAddLostAndFoundPage", method = RequestMethod.GET)
    @ApiIgnore
    public ModelAndView toAddLostAndFoundPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("lostAndFound/addLostAndFoundPage");
    }

    @RequestMapping(value = "toUpdateLostAndFoundPage", method = RequestMethod.GET)
    @ApiIgnore
    public ModelAndView toUpdateLostAndFoundPage(String id, HttpServletRequest request, HttpServletResponse response) {
        LostAndFound lostAndFound = iLostAndFoundService.findLostAndFoundById(id);
        ModelAndView model = new ModelAndView("lostAndFound/updateLostAndFoundPage");
        model.addObject("lostAndFound", lostAndFound);
        return model;
    }

    @RequestMapping(value = "updateLostAndFoundById", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String updateLostAndFoundById(LostAndFound lostAndFound, HttpServletRequest request, HttpServletResponse response) {
        iLostAndFoundService.updateLostAndFoundById(lostAndFound);
        DwzCallBackResult result = new DwzCallBackResult();
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }

    @RequestMapping(value = "batchDeleteLostAndFoundById", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String batchDeleteLostAndFoundById(String listIdJson) {
        DwzCallBackResult result = new DwzCallBackResult();
        List<String> listId = JsonTools.gson.fromJson(listIdJson, ArrayList.class);
        if (listId != null && listId.size() > 0) {
            iLostAndFoundService.batchDeleteLostAndFoundById(listId);
        }
        result.setStatusCode(200);
        return JsonTools.gson.toJson(result);
    }
}
