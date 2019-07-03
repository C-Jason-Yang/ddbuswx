package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.WcUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/9/18.
 */
@Repository
@Mapper
public interface WcUserMapper {

    List<WcUser> page(@Param("offset") Long offset, @Param("pageSize") Integer pageSize, @Param("wcUser") WcUser wcUser);

    WcUser findUserByBusCardNo(@Param("busCardNo") String busCardNo, @Param("areaCode") String areaCode);

    Integer countTotal(@Param("wcUser") WcUser wcUser);

    WcUser getWcUserInfoById(@Param("id") String id);

    void removeBusCardBinding(@Param("wcOpenid") String wcOpenid);

    WcUser getWcUserInfoByOpenid(@Param("wcOpenid") String id);
}
