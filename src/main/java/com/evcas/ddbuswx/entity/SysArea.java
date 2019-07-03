package com.evcas.ddbuswx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2019/5/13.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysArea {

    private String id;
    private String code;
    private String name;
    private String parentId;
    private String parentIds;
    private String latitude;
    private String longitude;
    private String type;

}
