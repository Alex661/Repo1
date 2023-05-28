package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.SeatBusiness;
import com.huawei.ibooking.model.SeatDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SeatController {
    @Autowired
    private SeatBusiness seatBusiness;


    @GetMapping(value = "/seat")
    public ResponseEntity<List<SeatDO>> findAll() {
        final List<SeatDO> seats = seatBusiness.findAll();
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }
    @GetMapping("/seat/{id}")
    public ResponseEntity<SeatDO> getSeat(@PathVariable("id") int id) {
        System.out.println(id);
        Optional<SeatDO> seat = seatBusiness.getSeatById(id);
        return seat.map(seatDO -> new ResponseEntity<>( seatDO,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping ("/seat/create")
    public ResponseEntity<Void> createSeat(@RequestBody SeatDO seat) {
        System.out.println(seat.toString());
        boolean result = seatBusiness.createSeat(seat);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping (value = "/seat/{id}")
    public  ResponseEntity<Void> deleteSeat(@PathVariable("id") int id) {
        boolean result = seatBusiness.deleteSeat(id);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/timeslot/{timeslot}")
    public ResponseEntity<List<SeatDO>> getSeatsByTimeslot(@PathVariable("timeslot") int timeslot) {
        if (timeslot==0)
            timeslot = LocalTime.now().getHour();
        System.out.println(timeslot);
        return new ResponseEntity<>(seatBusiness.getSeatsByTimeslot(timeslot),HttpStatus.OK);
    }
    @PutMapping(value = "/seat")
    public ResponseEntity<Void> save(@RequestBody SeatDO seat){
        boolean result = seatBusiness.updateSeat(seat);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/seat/frozen/{id}")
    public ResponseEntity<Void> frozen(@PathVariable int id){
        boolean result = seatBusiness.freezeSeat(id);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "/seat/reversion/{id}/{time}/{num}")
    public ResponseEntity<Void> rev(@PathVariable int id,@PathVariable int time,@PathVariable int num){
        boolean result = seatBusiness.reserveSeat(id, time, num);
        return new ResponseEntity<>(result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
