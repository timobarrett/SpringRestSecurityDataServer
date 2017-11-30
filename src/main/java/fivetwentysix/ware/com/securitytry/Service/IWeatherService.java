package fivetwentysix.ware.com.securitytry.Service;

import fivetwentysix.ware.com.securitytry.entity.Weather;

import java.util.List;

public interface IWeatherService {
    List<Weather> getAllWeather();
    Weather getWeatherById(int weatherId);
    boolean addWeather(Weather weather);
//    //    @Secured ({"ROLE_ADMIN"})
//    void updateWeather(Weather weather);
//    // @Secured ({"ROLE_ADMIN"})
//    void deleteWeather(int weatherId);

}
