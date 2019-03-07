package net.bsoftlab.model;

public class Workday {

    private Integer id = null;
    private Integer weekday = null;
    private Integer day = null;
    private Integer month = null;
    private Integer year = null;

    public Workday(){}

    public Integer getId() {
        return this.id;
    }
    public Integer getWeekday() {
        return this.weekday;
    }
    public Integer getDay() {
        return this.day;
    }
    public Integer getMonth() {
        return this.month;
    }
    public Integer getYear() {
        return this.year;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }
    public void setDay(Integer day) {
        this.day = day;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        Workday workday = (Workday) object;
        return this.id.equals(workday.id)
                && this.weekday.equals(workday.weekday)
                && this.day.equals(workday.day)
                && this.month.equals(workday.month)
                && this.year.equals(workday.year);
    }


    @Override
    public String toString() {
        return "Workday [" + this.id + ", " +
                this.weekday + ", " +
                this.day + ", " +
                this.month + ", " +
                this.year + "]";
    }
}
