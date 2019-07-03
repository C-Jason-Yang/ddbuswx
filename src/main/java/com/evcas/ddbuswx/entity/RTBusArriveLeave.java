package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

/**
 * Created by noxn on 2018/8/16.
 */

@Data
@AllArgsConstructor
public class RTBusArriveLeave {
    private String areaid;
    private Integer vehicleid;
    private Integer line;
    private Integer biztype;
    private Integer iostatus;
    private Integer sid;
    private Integer sno;
    private float gpslat;
    private float gpslon;
    private Integer time;
    private String bugtag;
    private String fromSys;
    private Integer currentTime;
    private String areaCode;

    public RTBusArriveLeave() {
        Calendar cal = Calendar.getInstance();
        this.currentTime = Integer.valueOf(String.valueOf(cal.getTimeInMillis() / 1000));
    }
}
