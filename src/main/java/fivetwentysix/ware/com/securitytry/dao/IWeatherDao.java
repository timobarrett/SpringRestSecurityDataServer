package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.entity.Weather;

import java.util.List;

public interface IWeatherDao {
    void save(Weather weather);
    void delete(Weather weather);
    List<Weather> getAll();
    // @Override
    Weather getWeatherById(int weatherId);
    boolean weatherExists(double weather, long date);

}
