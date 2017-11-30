package fivetwentysix.ware.com.securitytry.Service;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.dao.IWeatherDao;
import fivetwentysix.ware.com.securitytry.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService implements IWeatherService {
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);

    @Autowired
    private IWeatherDao weatherDao;

    @Override
    public Weather getWeatherById(int weatherId) {
        log.info("getDistanceById");
        Weather obj = weatherDao.getWeatherById(weatherId);
        return obj;
    }

    @Override
    public List<Weather> getAllWeather() {
        log.info("getALlDistance");
        return weatherDao.getAll();
    }

    @Override
    public boolean addWeather(Weather weather){
        log.info("addWeather");
        if (weatherDao.weatherExists(weather.getTemp(), weather.getDate())) {
            return false;
        } else {
            weatherDao.save(weather);
            return true;
        }
    }


}
