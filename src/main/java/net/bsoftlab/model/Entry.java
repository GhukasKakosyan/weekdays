package net.bsoftlab.model;

public class Entry {

    private Integer id = null;
    private Integer weekday = null;
    private Integer day = null;
    private Long quantity = null;

    public Entry() {}

    public Integer getId() {
        return this.id;
    }
    public Integer getWeekday() {
        return this.weekday;
    }
    public Integer getDay() {
        return this.day;
    }
    public Long getQuantity() {
        return this.quantity;
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
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        Entry entry = (Entry) object;
        return this.id.equals(entry.id)
                && this.weekday.equals(entry.weekday)
                && this.day.equals(entry.day)
                && this.quantity.equals(entry.quantity);
    }

    @Override
    public String toString() {
        return "Entry [" +
                this.weekday + ", " +
                this.day + ", " +
                this.quantity + "]";
    }
}
