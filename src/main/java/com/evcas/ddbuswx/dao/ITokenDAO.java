package com.evcas.ddbuswx.dao;

import com.evcas.ddbuswx.model.Token;

/**
 * Created by noxn on 2018/3/28.
 */
public interface ITokenDAO {

    public void insertToken(Token token);

    public void deleteAllToken();

    public void deleteTokenByUserId(String userId);

    public Token findTokenByToken(String token);

    public void deleteTokenByToken(String token);
}
