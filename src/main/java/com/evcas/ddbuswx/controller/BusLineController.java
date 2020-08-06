package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.commonEnum.ResFlagEnum;
import com.evcas.ddbuswx.config.cache.SysCache;
import com.evcas.ddbuswx.entity.ResVo;
import com.evcas.ddbuswx.model.mongo.BusLine;
import com.evcas.ddbuswx.model.RTBusArriveLeave;
import com.evcas.ddbuswx.model.Router;
import com.evcas.ddbuswx.service.IBusLineService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by noxn on 2019/5/10.
 */
@Log4j2
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("busLine")
@Api(value = "线路", tags = "线路接口类")
public class BusLineController {

    @Autowired
    private IBusLineService iBusLineService;

    @ApiOperation(value = "查询线路", notes = "根据关键词和区域id查询线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineName", value = "关键词", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String")
    })
    @RequestMapping(value = "getLineByName", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<BusLine>> getLineByName(String lineName, String areaId) {
        return getBusLineList(lineName, areaId);
    }


    private ResVo<List<BusLine>> getBusLineList(String lineName, String areaId) {
        String key = SysCache.BUS_LINE + areaId;
        ResVo<List<BusLine>> res;
        if (Strings.isNullOrEmpty(lineName)) {
            res = (ResVo<List<BusLine>>) SysCache.getInstance().getCache(key);
            if (res != null) {
                return res;
            }
        }
        res = new ResVo<List<BusLine>>();
        List<BusLine> busLineList = iBusLineService.queryBusLineByLikeLineName(lineName, areaId);
        if (busLineList != null && busLineList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(busLineList);
        if (Strings.isNullOrEmpty(lineName)) {
            SysCache.getInstance().addCache(key, res);
        }
        return res;
    }


    @ApiOperation(value = "查询线路详情", notes = "根据线路编码和区域id查询线路详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineCode", value = "线路编码", required = true, dataType = "String", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String", defaultValue = "341222", paramType = "query")
    })
    @RequestMapping(value = "getLineInfoByLineCode", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<BusLine> getLineInfoByLineCode(String lineCode, String areaId) {
        ResVo res = new ResVo();
        res.setFlag(ResFlagEnum.Normal.getFlag());
        BusLine busLine = iBusLineService.getLineInfoById(lineCode, areaId);
        if (busLine != null) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(busLine);
        return res;
    }

    @ApiOperation(value = "查询线路列表", notes = "根据站点名称和区域id查询经过这个站点的线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationName", value = "站点名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String")
    })
    @RequestMapping(value = "findLineByStationName", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<BusLine>> findLineByStationName(String stationName, String areaId) {
        ResVo res = new ResVo();
        List<BusLine> busLineList = iBusLineService.findLineByStationName(stationName, areaId);
        if (busLineList != null && busLineList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(busLineList);
        return res;
    }

    @ApiOperation(value = "公交换乘查询", notes = "根据起始站点和结束站点或者起始点坐标查询公交换乘线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startStationName", value = "起始站点名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endStationName", value = "结束站点名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startLat", value = "起始纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startLon", value = "起始经度", required = true, dataType = "String"),

            @ApiImplicitParam(name = "endLat", value = "结束纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endLon", value = "结束经度", required = true, dataType = "String")
    })
    @RequestMapping(value = "findBusRouters", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<Router>> findBusRouters(String startStationName, String endStationName, String areaId,
                                              String startLat, String startLon, String endLat, String endLon) {
        ResVo res = new ResVo();
        log.info("startStationName:" + startStationName + "| endStationName:" + endStationName + "| areaId:" + areaId + "| startLat:" + startLat + "| startLon:" + startLon + "| endLat:" + endLat + "| endLon:" + endLon);
        List<Router> routerList = iBusLineService.findBusRouters(startStationName, endStationName, areaId, startLat, startLon, endLat, endLon);
        if (routerList != null && routerList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(routerList);
        return res;
    }

    @ApiOperation(value = "车辆到离站信息", notes = "车辆到离站信息信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "lineCode", value = "线路编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "direction", value = "上下行标志 1：上行  2：下行", required = true, dataType = "String")
    })
    @RequestMapping(value = "findRTBusArriveLeave", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<RTBusArriveLeave>> findRTBusArriveLeaveBy(String areaId, String lineCode, String direction) {
        ResVo res = new ResVo();
        List<RTBusArriveLeave> rtBusArriveLeaveList = iBusLineService.findRTBusArriveLeaveBy(areaId, lineCode, direction);
        if (rtBusArriveLeaveList != null && rtBusArriveLeaveList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(rtBusArriveLeaveList);
        return res;
    }
}
