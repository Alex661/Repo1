package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.SeatDao;
import com.huawei.ibooking.model.SeatDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeatBusiness {
    @Autowired
    private  SeatDao seatDao;

    public List<SeatDO> findAll() {
        return seatDao.findAll();
    }

    public Optional<SeatDO> getSeatById(int id) {
        List<SeatDO> seats = seatDao.getSeatById(id);
        if (seats.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(seats.get(0));
    }

    public boolean createSeat(SeatDO seat) {
        return seatDao.createSeat(seat);
    }

    public boolean deleteSeat(int id) {
        return seatDao.deleteSeat(id);
    }
    public boolean updateSeat(SeatDO seatDO){
        return seatDao.updateSeat(seatDO);
    }

    public boolean reserveSeat(int id) {
        List<SeatDO> optionalSeat = seatDao.getSeatById(id);
        if (!optionalSeat.isEmpty()) {
            SeatDO seat = optionalSeat.get(0);
            if (!seat.isReserved()) {
                seat.setReserved(true);
                return seatDao.updateSeat(seat);
            }
        }
        return false;
    }

    public boolean freezeSeat(int id) {
        List<SeatDO> seatById = seatDao.getSeatById(id);
        if (!seatById.isEmpty()) {
            SeatDO seat = seatById.get(0);
            boolean frozen = !seat.isFrozen();
            seat.setFrozen(frozen);
            return seatDao.updateSeat(seat);
        }
        return false;
    }

    public List<SeatDO> getSeatsByTimeslot(int timeslot) {
        return seatDao.getSeatsByTimeslot(timeslot);
    }


    public boolean reserveSeat(int seatId, int startTimeslot, int num) {
        SeatDO seat = seatDao.getSeatById(seatId).get(0);
        String reservedTimeslots = seat.getReservedTimeslots();
        for (int i = 0; i < num;i++) {
            if (reservedTimeslots.contains(""+(startTimeslot+i))||startTimeslot>21||startTimeslot<8)
                return false;
            reservedTimeslots += startTimeslot;
            startTimeslot++;
        }
        seat.setReservedTimeslots(reservedTimeslots.trim());
        seatDao.updateSeat(seat);
        return true;

    }
}
