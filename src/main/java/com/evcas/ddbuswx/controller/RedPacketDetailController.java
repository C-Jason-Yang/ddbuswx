package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.DwzCallBackResult;
import com.evcas.ddbuswx.common.utils.ExcelUtil.ExcelObject;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.RedPacketDetail;
import com.evcas.ddbuswx.model.DwzPageModel;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ITokenService;
import com.evcas.ddbuswx.service.impl.FileUpLoadService;
import com.evcas.ddbuswx.service.impl.RedPacketDetailService;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/9/19.
 */
@Controller
@RequestMapping("redPacketDetail")
@ApiIgnore
public class RedPacketDetailController {

    @Autowired
    private RedPacketDetailService redPacketDetailService;

    @Autowired
    private FileUpLoadService fileUpLoadService;

    @Autowired
    private ITokenService iTokenService;


    @RequestMapping(value = "importData", method = RequestMethod.POST)
    @ResponseBody
    public String importData(String fileBase64Code, String fileName) {
        String tempFilePath = fileUpLoadService.fileBase64UpLoad(fileBase64Code, fileName);
        DwzCallBackResult result = new DwzCallBackResult();
        Map<String, Object> data = new HashMap<>();
        ExcelObject ec = new ExcelObject(tempFilePath);
        List<RedPacketDetail> redPacketDetailList = ec.getFileDataList(RedPacketDetail.class, 0, 1);
        String redPacketActiviId = redPacketDetailService.batchAddRedPacketDetail(redPacketDetailList);
        BigDecimal totalAmount = new BigDecimal("0.00");
        if (redPacketDetailList != null) {
            for (int i = 0; i < redPacketDetailList.size(); i++) {
                BigDecimal tempAmount = new BigDecimal(redPacketDetailList.get(i).getAmount());
                tempAmount = tempAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                totalAmount = totalAmount.add(tempAmount);
            }
        }
        //获取Excel表格中数据的总行数
        data.put("sheetLineNum", ec.currentImportSheetLineNum - 1);
        data.put("realLineNum", redPacketDetailList.size());
        data.put("redPacketActiviId", redPacketActiviId);
        data.put("totalAmount", String.valueOf(totalAmount));
        data.put("filePath", tempFilePath.substring(5));
        data.put("statusCode", "200");
        return JsonTools.gson.toJson(data);
    }

    @RequestMapping(value = "findWcUserRedPacketPage")
    public ModelAndView findWcUserRedPacketPage(String openid) {
        ModelAndView model = new ModelAndView();
        model.setViewName("wcUser/redPacketPage");
        return model;
    }

    @RequestMapping(value = "page")
    public ModelAndView page(Long pageNum, RedPacketDetail redPacketDetail, HttpServletRequest request) {
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

                dwzPageModel = redPacketDetailService.page(redPacketDetail, dwzPageModel);
                model.setViewName("redPacketDetail/redPacketDetailPage");
                model.addObject("dwzPage", dwzPageModel);
                model.addObject("openid", redPacketDetail.getOpenid());
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "pageByOpenid")
    public ModelAndView pageByOpenid(Long pageNum, RedPacketDetail redPacketDetail, HttpServletRequest request) {
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

                dwzPageModel = redPacketDetailService.page(redPacketDetail, dwzPageModel);
                model.setViewName("wcUser/personalRedPacketPage");
                model.addObject("openid", redPacketDetail.getOpenid());
                model.addObject("dwzPage", dwzPageModel);
                return model;
            }
        }
        model.setViewName("redirect:/toReLogin");
        return model;
    }

    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, RedPacketDetail redPacketDetail) {
        try {
            String fileName = "红包明细.xls";
            String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            String headStr = "attachment; filename=\"" + codedFileName + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            HSSFWorkbook workbook = redPacketDetailService.export(redPacketDetail);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "test")
    @ResponseBody
    public String test() {
        redPacketDetailService.queryRedPacketAcceptInfo();
        return "ok";
    }
}
