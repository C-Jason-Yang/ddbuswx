package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.SysArea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by noxn on 2019/5/13.
 */
@Repository
@Mapper
public interface SysAreaMapper {

    SysArea findAreaById(String id);
}
