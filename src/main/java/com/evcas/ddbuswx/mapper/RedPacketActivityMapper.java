package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.RedPacketActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by noxn on 2018/9/20.
 */
@Repository
@Mapper
public interface RedPacketActivityMapper {

    void insertRedPacketActivity(@Param("redPacketActivity") RedPacketActivity redPacketActivity);

    void updateRedPacketActivityStatus(@Param("id") String id, @Param("status") String status,
                                       @Param("cancelDate") Date cancelDate,
                                       @Param("confirmDate") Date confirmDate,
                                       @Param("sendDate") Date sendDate,
                                       @Param("cancelUserName") String cancelUserName,
                                       @Param("confirmUserName") String confirmUserName,
                                       @Param("sendUserName") String sendUserName);

    Integer countTotal(@Param("redPacketActivity") RedPacketActivity redPacketActivity);

    List<RedPacketActivity> page(@Param("offset") Long offset, @Param("pageSize") Integer pageSize,
                                 @Param("redPacketActivity") RedPacketActivity redPacketActivity);

    RedPacketActivity getById(@Param("id") String id);

    void update(@Param("redPacketActivity") RedPacketActivity redPacketActivity);
}
