package com.evcas.ddbuswx.model;

import com.evcas.ddbuswx.common.utils.UuidUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by noxn on 2018/3/8.
 */
@Data
@AllArgsConstructor
public class LostAndFound {

    private String id;
    private String title;
    private Integer status;
    private String content;
    private String areaMark;
    private String lostTime;
    private String releaseTime;
    private String downShelvesTime;


    public LostAndFound() {
        this.id = UuidUtil.getUuid();
        this.status = 1;
    }
}