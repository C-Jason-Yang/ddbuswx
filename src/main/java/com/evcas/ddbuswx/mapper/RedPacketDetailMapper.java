package com.evcas.ddbuswx.mapper;

import com.evcas.ddbuswx.entity.RedPacketDetail;
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
public interface RedPacketDetailMapper {

    void batchInsertRedPacketDetail(@Param("redPacketDetailList") List<RedPacketDetail> redPacketDetailList);

    Integer countRedPacketDetailNumByActivityId(@Param("activityId") String activityId);

    void updateRedPacketDetailActivityId(@Param("tempActivityId") String tempActivityId, @Param("activityId") String activityId);

    List<RedPacketDetail> findRedPacketDetailByActivityId(@Param("activityId") String activityId);

    void updateRedPacketDetailStatusById(@Param("id") String id, @Param("status") String status);

    void updateRedPacketDetailById(@Param("redPacketDetail") RedPacketDetail redPacketDetail);

    List<RedPacketDetail> queryRedPacketAcceptInfo(@Param("today") Date today,
                                                   @Param("yesterday") Date yesterday,
                                                   @Param("dayBeforeYesterday") Date dayBeforeYesterday);

    void updateRedPacketDetailReceiveById(@Param("id") String id, @Param("receive") String receive);

    List<RedPacketDetail> page(@Param("offset") Long offset, @Param("pageSize") Integer pageSize,
                               @Param("redPacketDetail") RedPacketDetail redPacketDetail);

    Integer countTotal(@Param("redPacketDetail") RedPacketDetail redPacketDetail);

    void update(@Param("redPacketDetail") RedPacketDetail redPacketDetail);

    String sumAmount(@Param("redPacketDetail") RedPacketDetail redPacketDetail);
}
