package com.evcas.ddbuswx.model;

import com.evcas.ddbuswx.common.utils.UuidUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by noxn on 2018/3/21.
 */

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String areaMark;
    private String userName;
    private String password;


    public User() {
        this.id = UuidUtil.getUuid();
    }
}
