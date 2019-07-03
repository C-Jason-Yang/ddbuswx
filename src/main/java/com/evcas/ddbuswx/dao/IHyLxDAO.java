package com.evcas.ddbuswx.dao;

/**
 * Created by noxn on 2018/1/17.
 */
public interface IHyLxDAO {

    public String getBusLine();

    public String getBusStation(String lineCode);

    public String getBusLineShift(String lineCode);
}
