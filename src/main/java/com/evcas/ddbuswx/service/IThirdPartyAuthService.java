package com.evcas.ddbuswx.service;

/**
 * Created by noxn on 2018/8/11.
 */
public interface IThirdPartyAuthService {

    boolean checkAreaAuth(String ip, String pid, String areaCode);
}
