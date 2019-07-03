package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/3/8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoFeedBack {

    private String id;
    private String submitName;
    private String submitTime;
    private String contact;
    private String contactType;
    private String message;
    private String areaMark;

}
