package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.ThirdPartyReturnStatus;
import com.evcas.ddbuswx.common.utils.IpUtil;
import com.evcas.ddbuswx.common.utils.JsonTools;
import com.evcas.ddbuswx.entity.PlatformAccount;
import com.evcas.ddbuswx.entity.RTBusArriveLeave;
import com.evcas.ddbuswx.model.Area;
import com.evcas.ddbuswx.model.BusLine;
import com.evcas.ddbuswx.model.ThirdPartyReturnModel;
import com.evcas.ddbuswx.service.*;
import com.evcas.ddbuswx.tcp.DiscardServerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/8/9.
 */
@Controller
@RequestMapping("busApi")
@ApiIgnore
public class BusApiController {

    @Autowired
    private IBusLineService iBusLineService;

    @Autowired
    private IThirdPartyAuthService iThirdPartyAuthService;

    @Autowired
    private IBusStationService iBusStationService;

    @Autowired
    private IAreaService iAreaService;

    @Autowired
    private PlatformAccountService platformAccountService;

    @RequestMapping(value = "busLine", method = RequestMethod.POST)
    @ResponseBody
    public String getBusLine(String pid, String areaCode, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        ThirdPartyReturnModel model = new ThirdPartyReturnModel();
        System.err.println("ip, pid, areaCode = " + ip + "|" + pid + "|" + areaCode);
        boolean result = iThirdPartyAuthService.checkAreaAuth(ip, pid, areaCode);
        if (result) {
            PlatformAccount platformAccount = platformAccountService.findPlatformAccountByAreaCode(areaCode);
            if (platformAccount != null) {
                areaCode = platformAccount.getAreaMark();
            }
            List<BusLine> busLineList = iBusLineService.getBusLineByAreaCode(areaCode);
            model.setStatus(ThirdPartyReturnStatus.Normal.getKey());
            model.setMsg(ThirdPartyReturnStatus.Normal.getValue());
            model.setData(busLineList);
        } else {
            model.setStatus(ThirdPartyReturnStatus.NoAuth.getKey());
            model.setMsg(ThirdPartyReturnStatus.NoAuth.getValue());
        }
        return JsonTools.gson.toJson(model);
    }

    @RequestMapping(value = "busStation", method = RequestMethod.POST)
    @ResponseBody
    public String getBusStation(String pid, String areaCode, String lineCode, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        ThirdPartyReturnModel model = new ThirdPartyReturnModel();
        System.err.println("ip, pid, areaCode = " + ip + "|" + pid + "|" + areaCode);
        boolean result = iThirdPartyAuthService.checkAreaAuth(ip, pid, areaCode);
        if (result) {
            PlatformAccount platformAccount = platformAccountService.findPlatformAccountByAreaCode(areaCode);
            if (platformAccount != null) {
                areaCode = platformAccount.getAreaMark();
            }
            Map<String, List> resultMap = iBusStationService.getBusStationByLineCode(areaCode, lineCode);
            model.setStatus(ThirdPartyReturnStatus.Normal.getKey());
            model.setMsg(ThirdPartyReturnStatus.Normal.getValue());
            model.setData(resultMap);
        } else {
            model.setStatus(ThirdPartyReturnStatus.NoAuth.getKey());
            model.setMsg(ThirdPartyReturnStatus.NoAuth.getValue());
        }
        return JsonTools.gson.toJson(model);
    }

    @RequestMapping(value = "receiveBusInfo", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestBody RTBusArriveLeave rtBusArriveLeave) {
        System.err.println(rtBusArriveLeave.getAreaid());

        if (rtBusArriveLeave.getAreaid().equals("341226")) {
            rtBusArriveLeave.setAreaid("YS");
            Area area = iAreaService.getAreaByAreaId(rtBusArriveLeave.getAreaid());
            rtBusArriveLeave.setAreaCode(area.getAreaCode());
            BusLine hyBusLine = iBusLineService.getBusLineByLineCode(String.valueOf(rtBusArriveLeave.getLine()));
            BusLine rmBusLine = iBusLineService.getBusLineByLineName(hyBusLine.getLineName(), "341226");
            rtBusArriveLeave.setLine(Integer.valueOf(rmBusLine.getLineCode()));
            DiscardServerHandler.send(rtBusArriveLeave);
        }
        if (rtBusArriveLeave.getAreaid().equals("341222")) {
            rtBusArriveLeave.setAreaid("TH");
            Area area = iAreaService.getAreaByAreaId(rtBusArriveLeave.getAreaid());
            rtBusArriveLeave.setAreaCode(area.getAreaCode());
            DiscardServerHandler.send(rtBusArriveLeave);
        }
        return "ok";
    }
}
