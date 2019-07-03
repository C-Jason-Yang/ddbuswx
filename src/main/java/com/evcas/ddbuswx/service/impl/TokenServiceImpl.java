package com.evcas.ddbuswx.service.impl;

import com.evcas.ddbuswx.dao.ITokenDAO;
import com.evcas.ddbuswx.model.Token;
import com.evcas.ddbuswx.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by noxn on 2018/3/28.
 */
@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private ITokenDAO iTokenDAO;

    @Override
    public Token findTokenByToken(String token) {
        return iTokenDAO.findTokenByToken(token);
    }

    @Override
    public void addToken(Token token) {
        iTokenDAO.deleteTokenByUserId(token.getUserId());
        iTokenDAO.insertToken(token);
    }

    @Override
    public void deleteToken(String token) {
        iTokenDAO.deleteTokenByToken(token);
    }
}
