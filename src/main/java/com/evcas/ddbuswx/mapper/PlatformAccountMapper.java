package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.PlatformAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/6/13.
 */
@Repository
@Mapper
public interface PlatformAccountMapper {

    List<PlatformAccount> findAllPlatformAccount();

    PlatformAccount findPlatformAccountByAreaCode(String areaCode);
}
