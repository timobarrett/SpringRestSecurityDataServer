package fivetwentysix.ware.com.securitytry.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="distance")
public class Distance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int locationid;

    @NotNull
    private long distancedate;

    @NotNull
    private double mileage;

    @NotNull
    private long runduration;

    @NotNull
    private long totalduration;

    public int getLocation_id(){return locationid;}
    public void setLocation_id(int location_id){this.locationid = location_id;}

    public long getDate() {return distancedate;}

    public void setDate(long date) {
        this.distancedate = date;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public long getRun_duration() {
        return runduration;
    }

    public void setRun_duration(int run_duration) {
        this.runduration = run_duration;
    }

    public long getTotal_duration() {
        return totalduration;
    }

    public void setTotal_duration(int total_duration) {
        this.totalduration = total_duration;
    }

    @Override
    public String toString(){
        return String.format(
                "Distance [date=%d,mileage=%f,Run Duration=%d, Total Duration=%d]",
                getDate(),getMileage(),getRun_duration(),getTotal_duration());
    }
}
