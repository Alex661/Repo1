package com.huawei.ibooking.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SeatDO {
    private int id;
    private String name;
    private boolean isReserved;
    private boolean isFrozen;
    private String reservedTimeslots;

    public SeatDO(int id, String name) {
        this.id = id;
        this.name = name;
        this.isReserved = false;
        this.isFrozen = isTimeFrozen();
        this.reservedTimeslots = reservedTimeslots;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    public String getReservedTimeslots() {
        return reservedTimeslots;
    }

    public void setReservedTimeslots(String reservedTimeslots) {
        this.reservedTimeslots = reservedTimeslots;
    }

    @Override
    public String toString() {
        return "SeatDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isReserved=" + isReserved +
                ", isFrozen=" + isFrozen +
                ", reservedTimeslots=" + reservedTimeslots +
                '}';
    }

    private boolean isTimeFrozen() {
        LocalTime currentTime = LocalTime.now();
        LocalTime frozenStartTime = LocalTime.of(22, 0); // 晚上10点
        LocalTime frozenEndTime = LocalTime.of(8, 0);   // 早上8点

        // 判断当前时间是否在晚上10点到早上8点之间
        // 座位可用
        return currentTime.isAfter(frozenStartTime) || currentTime.isBefore(frozenEndTime); // 座位冻结
    }
}
