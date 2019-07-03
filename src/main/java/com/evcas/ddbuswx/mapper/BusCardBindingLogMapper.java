package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.BusCardBindingLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2018/9/23.
 */
@Repository
@Mapper
public interface BusCardBindingLogMapper {

    void insertBusCardBindingLog(@Param("busCardBindingLog") BusCardBindingLog busCardBindingLog);

    List<BusCardBindingLog> page(@Param("offset") Long offset, @Param("pageSize") Integer pageSize,
                                 @Param("wcOpenid") String wcOpenid);

    Integer countTotal(@Param("wcOpenid") String wcOpenid);
}
