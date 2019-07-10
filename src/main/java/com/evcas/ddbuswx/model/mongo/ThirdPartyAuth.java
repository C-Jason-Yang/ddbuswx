package com.evcas.ddbuswx.model.mongo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 第三方权限
 * Created by noxn on 2018/8/11.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "thirdPartyAuth")
public class ThirdPartyAuth {

    private String ip;
    private String pid;
    private String areaAuth;
    private String createTime = DateUtil.now();
}
