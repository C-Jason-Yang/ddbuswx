package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.model.mongo.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by noxn on 2019/5/13.
 */
@Repository
@Mapper
public interface AreaMapper {

    List<Area> findAll();
}
