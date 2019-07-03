package com.evcas.ddbuswx.dao;

/**
 * Created by noxn on 2018/1/12.
 */
public interface IHyYsDAO {

    public String getBusLine();

    public String getBusStation(String lineCode);

    public String getBusLineShift(String lineCode);
}
