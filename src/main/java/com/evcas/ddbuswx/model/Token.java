package com.evcas.ddbuswx.model;

import com.evcas.ddbuswx.common.utils.UuidUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by noxn on 2018/3/28.
 */

@Data
@AllArgsConstructor
public class Token {

    private String id;
    private String userId;
    private String token;
    private String createTime;


    public Token() {
        this.id = UuidUtil.getUuid();
    }
}
