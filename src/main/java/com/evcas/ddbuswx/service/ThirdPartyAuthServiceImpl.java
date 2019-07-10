package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.dao.IThirdPartyAuthDAO;
import com.evcas.ddbuswx.model.mongo.ThirdPartyAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/8/11.
 */
@Service
public class ThirdPartyAuthServiceImpl implements IThirdPartyAuthService {

    @Autowired
    private IThirdPartyAuthDAO iThirdPartyAuthDAO;

    @Override
    public boolean checkAreaAuth(String ip, String pid, String areaCode) {
        ThirdPartyAuth thirdPartyAuth = iThirdPartyAuthDAO.getThirdPartyAuth(ip, pid);
        if (thirdPartyAuth != null) {
            String areaAuth = thirdPartyAuth.getAreaAuth();
            String[] areaArr = areaAuth.split(",");
            for (String s : areaArr) {
                if (s.equals(areaCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
