package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.ThirdPartyAuth;

/**
 * 第三方权限
 * Created by noxn on 2018/8/11.
 */
public interface IThirdPartyAuthDAO {

    ThirdPartyAuth getThirdPartyAuth(String ip, String pid);
}
