package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.entity.PlatformAccount;
import com.evcas.ddbuswx.mapper.PlatformAccountMapper;
import com.evcas.ddbuswx.service.PlatformAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by noxn on 2018/6/13.
 */
@Service
public class PlatformAccountServiceImpl implements PlatformAccountService {

    @Autowired
    private PlatformAccountMapper platformAccountMapper;

    @Override
    public void findAllPlatformAccount() {
        List<PlatformAccount> platformAccountList = platformAccountMapper.findAllPlatformAccount();
        System.out.println(platformAccountList);
    }

    @Override
    public PlatformAccount findPlatformAccountByAreaCode(String areaCode) {
        return platformAccountMapper.findPlatformAccountByAreaCode(areaCode);
    }
}
