package fivetwentysix.ware.com.securitytry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by tim on 6/26/2017.
 */
@Entity//(name = "weather") //needed to add to get table defined and avoid nested exception is org.hibernate.hql.internal.ast.QuerySyntaxException: is not mapped [from ]
@Table(name = "weather")
public class Weather implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name="location_id")
    private int location_id;
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="location_id")
//    private LocationData location;

    //FetchType.EAGER caus`es  Exception = More than one row with the given identifier was found: error
    /* @OneToOne(fetch=FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="distancedate", referencedColumnName = "weatherdate", insertable=false,updatable = false)*/
    //next is needed to avoid -  Failed to write HTTP message: org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON:
//    @JsonBackReference
//    @OneToOne(fetch=FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.MERGE)
//    @JoinColumn(name="weatherdate", referencedColumnName = "distancedate", insertable=false,updatable = false)
//    private DistanceData distanceData;

    @Column(name="weatherdate")
    @NotNull
    private long weatherdate;

    @Lob // this is needed because the column is defined as longtext in the database
    @NotNull
    @Column(name="shortdesc")
    private String shortdesc;

    @NotNull
    private double temp;

    @NotNull

    private double humidity;

    @NotNull
    private double pressure;

    @NotNull
    private double wind;

    @NotNull
    private String degrees;

    @NotNull
    private int storm;

    public Weather() {}

    public Weather(int id){
        this.id = id;
    }

    public long getWeatherdate() {
        return weatherdate;
    }

    public void setWeatherdate(long weather_date) {
        this.weatherdate = weather_date;
    }

    public Weather(int location_id, long date, String short_desc, Double temp,
                       Double humidity, Double pressure, Double wind, String degrees, int storm ){
        this.location_id=location_id;
        //   this.distance.setDate(date);
        this.weatherdate = date;
        this.shortdesc = short_desc;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
        this.storm = storm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //    public LocationData getLocation_id() {
//        return location;
//    }
    public int getLocation_id(){return location_id;}

    //    public void setLocation_id(LocationData location ) {
//        this.location = location;
//    }
    public void setLocation_id(int location_id){this.location_id = location_id;}

    public long getDate() {
        return weatherdate;
    }

    public void setDate(long date) {
        //   this.date=date;
        weatherdate = date;
    }

    public String getShortDesc() {
        return shortdesc;
    }

    public void setShortDesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public int getStorm() {
        return storm;
    }

    public void setStorm(int storm) {
        this.storm = storm;
    }

    //public DistanceData getDistanceData(){
      //  return distanceData;
   // }

    @Override
    public String toString(){
        return String.format(
                "weather [description=%s, temp=%f, humidity=%f]",//, pressure=%f, wind=%f, direct=%s, storm=%d]",
                getShortDesc(),getTemp(),getHumidity());//,getPressure(),getDegrees(),getStorm());
    }
}


