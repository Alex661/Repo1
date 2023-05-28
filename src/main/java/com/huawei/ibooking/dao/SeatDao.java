package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.SeatMapper;
import com.huawei.ibooking.model.SeatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeatDao {
    @Autowired
    private SeatMapper seatMapper;
    public List<SeatDO> findAll() {
        return seatMapper.findAll();
    }

    public List<SeatDO> getSeatById(int id) {
        return seatMapper.findById(id);
    }

    public boolean createSeat(SeatDO seat) {
        return seatMapper.createSeat(seat)>0;
    }

    public boolean deleteSeat(int id) {
        return seatMapper.deleteById(id) > 0;
    }

    public boolean updateSeat(SeatDO seat) {
        return seatMapper.updateSeat(seat) > 0;
    }

    public List<SeatDO> getSeatsByTimeslot(int timeslot) {
        return seatMapper.selectByTimeslot(String.valueOf(timeslot));
    }

    private boolean isTimeslotAvailable(SeatDO seat, int timeslot) {
        if (seat.getReservedTimeslots()==null||!seat.getReservedTimeslots().contains(""+timeslot) ) {
            return true;
        }
        return false;
    }

    private boolean isTimeFrozen(int timeslot) {
        int currentHour = LocalTime.now().getHour();
        return currentHour >= 22 || currentHour < 8;
    }


}