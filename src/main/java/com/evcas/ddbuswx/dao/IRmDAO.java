package com.evcas.ddbuswx.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by noxn on 2018/1/10.
 */
public interface IRmDAO {

    public String getBusLineAndStation(String regKey);

    public String rmLogin(String loginName, String password);

    public List<Map<String, Object>> getBusInfoList(String regKey);

    public String getBusShiftList(String regKey, Integer lineCode);
}
