package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.entity.PlatformAccount;

/**
 * Created by noxn on 2018/6/13.
 */
public interface PlatformAccountService {

    void findAllPlatformAccount();

    PlatformAccount findPlatformAccountByAreaCode(String areaCode);
}
