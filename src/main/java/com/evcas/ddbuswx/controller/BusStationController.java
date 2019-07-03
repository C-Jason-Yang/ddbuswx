package com.evcas.ddbuswx.controller;

import com.evcas.ddbuswx.common.commonEnum.ResFlagEnum;
import com.evcas.ddbuswx.entity.ResVo;
import com.evcas.ddbuswx.model.BusStation;
import com.evcas.ddbuswx.model.HotSite;
import com.evcas.ddbuswx.service.IBusStationService;
import com.evcas.ddbuswx.service.ISiteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by noxn on 2019/5/9.
 */
@Controller
@RequestMapping("busStation")
@Api(value = "站点", tags = "站点接口类")
public class BusStationController {

    @Autowired
    private IBusStationService iBusStationService;
    @Autowired
    private ISiteService iSiteService;

    @ApiOperation(value="获取附近的站点", notes="根据经纬度获取附近的站点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "String"),
            @ApiImplicitParam(name = "lon", value = "经度", required = true, dataType = "String")
    })
    @RequestMapping(value = "nearStation", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<BusStation>> getNearStationByGps(String lat, String lon) {
        ResVo res = new ResVo();
        List<BusStation> busStationList = iBusStationService.getNearStationByGps(lat, lon);
        if (busStationList != null && busStationList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(busStationList);
        return res;
    }

    @ApiOperation(value="查询热门站点", notes="根据区域id获取热门站点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String")
    })
    @RequestMapping(value = "findPopularStation", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<HotSite>> findPopularStation(String areaId) {
        ResVo res = new ResVo();
        List<HotSite> hotSiteList = iSiteService.findHotSiteListById(areaId);
        if (hotSiteList != null) {
            for (int i = 0; i < hotSiteList.size(); i++) {
                hotSiteList.get(i).setAreaId(hotSiteList.get(i).getAreaMark());
            }
        }
        if (hotSiteList != null && hotSiteList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(hotSiteList);
        return res;
    }

    @ApiOperation(value="查询站点", notes="根据区域id和站点名称查询站点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationName", value = "站点名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaId", value = "区域id", required = true, dataType = "String")
    })
    @RequestMapping(value = "findBusStationByStationName", method = RequestMethod.GET)
    @ResponseBody
    public ResVo<List<BusStation>> findBusStationByStationName(String stationName, String areaId) {
        ResVo<List<BusStation>> res = new ResVo();
        List<BusStation> busStationList = iBusStationService.queryStationLikeName(stationName, areaId);
        if (busStationList != null && busStationList.size() > 0) {
            res.setFlag(ResFlagEnum.Normal.getFlag());
        } else {
            res.setFlag(ResFlagEnum.Empty.getFlag());
        }
        res.setData(busStationList);
        return res;
    }
}
