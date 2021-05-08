package pirate.piratespring.controller;


import java.sql.Date;

public class HolidayForm {

    private Long level;
    private Date holiday;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Date getHoliday() {
        return holiday;
    }

    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }
}