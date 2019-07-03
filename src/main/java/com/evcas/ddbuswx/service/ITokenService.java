package com.evcas.ddbuswx.service;

import com.evcas.ddbuswx.model.Token;

/**
 * Created by noxn on 2018/3/28.
 */
public interface ITokenService {

    public Token findTokenByToken(String token);

    public void addToken(Token token);

    public void deleteToken(String token);
}
