package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by noxn on 2018/8/11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyReturnModel<T> {

    private String status;
    private String msg;
    private T data;

}