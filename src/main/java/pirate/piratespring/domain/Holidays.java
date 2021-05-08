package pirate.piratespring.domain;

import java.sql.Date;

public class Holidays {

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
