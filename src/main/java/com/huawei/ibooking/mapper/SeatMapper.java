package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.SeatDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeatMapper {
    List<SeatDO> findAll();
    List<SeatDO> findById(@Param("id")int id);
    int createSeat(@Param("seat") SeatDO seat);
    int deleteById(@Param("id") int id);
    int updateSeat(@Param("seat") SeatDO seat);
    List<SeatDO> selectByTimeslot(@Param("timeslot") String timeslot);
}