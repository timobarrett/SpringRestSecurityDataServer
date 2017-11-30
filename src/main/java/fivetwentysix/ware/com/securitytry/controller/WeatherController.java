package fivetwentysix.ware.com.securitytry.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fivetwentysix.ware.com.securitytry.Service.IWeatherService;
import fivetwentysix.ware.com.securitytry.dao.WeatherDao;
import fivetwentysix.ware.com.securitytry.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tim on 6/26/2017.
 */
//@RepositoryRestController
@Controller
//@Transactional  //this is needed for the get?id=760 to work without the lazy exception error
@RequestMapping(value="/weather")
public class WeatherController {//implements ErrorController {
    private static final String PATH = "/weather";
    //private final WeatherRepository weatherRepository;
    @Autowired
    private IWeatherService weatherService;

    private final Logger log = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherDao _weatherDao;
    //public WeatherTableController(WeatherRepository repository) {
    //   weatherRepository = repository;
    //}

//        @Autowired
//        LocationTableController(){}

    @RequestMapping(value = PATH)
    public String weather() {
        return "Weather handling";
    }

//    //this is to override the whitelabel screen display
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/delete")
    //   @ResponseBody
    public String delete(int id) {
        try {
            Weather weather = new Weather(id);
            _weatherDao.delete(weather);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "Weather Succesfully deleted!";
    }


    /* Name mismatch between params list and RequestParam caused no saves to occur */
    //@Secured({"ROLE_ADMIN","ROLE_USER"})
   // @PreAuthorize("hasAuthority('ROLE_USER')")
    //>curl -i -d "@addw.txt" -u tim:Dial0gicRots! --request POST  http://localhost:8080/weather/save - works
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value="save",method=RequestMethod.POST,
            params = {"location","date","short_desc","temp","humidity","pressure","wind","degrees","storm"})
    //@Transactional
    @ResponseBody
    public String create(@RequestParam("location")Integer location,
                         @RequestParam("date")Long date,
                         @RequestParam("short_desc")String short_desc,
                         @RequestParam("temp")Double temp,
                         @RequestParam("humidity")Double humidity,
                         @RequestParam("pressure")Double pressure,
                         @RequestParam("wind")Double wind,
                         @RequestParam("degrees")String degrees,
                         @RequestParam("storm")Integer storm) {

        try {
            Weather weather = new Weather(location,date,short_desc,temp,humidity,pressure,wind,degrees,storm);
            _weatherDao.save(weather);
        }
        catch(Exception ex) {
            System.out.println("Stack trace = "+ex.getStackTrace());
            return ex.getMessage();
        }
        return "Weather succesfully saved!";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/getAll")
    @JsonIgnore
    //  @Transactional
//    @ResponseBody
//    public String getAll(){
    public  ResponseEntity<List<Weather>> getAll(){
        List<Weather> weatherData = null;
        try{
            weatherData = _weatherDao.getAll();
        }catch(Exception e){
            System.out.println("getAll Exception = "+e.getMessage());
        }
        return new ResponseEntity<List<Weather>>(weatherData,HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("get/{id}")
    @JsonIgnore
    //  @SuppressWarnings("unchecked")
    //    @Transactional
    public ResponseEntity<Weather> get(@PathVariable("id") Integer id){
        Weather weatherData = null;
        log.info("IN weatherTable get ");
        try{
            weatherData =  _weatherDao.getWeatherById(id);
        }catch(Exception e){
            System.out.println("get Exception = "+e.getMessage());
            //return e.getMessage();
        }
        return new ResponseEntity<Weather>(weatherData, HttpStatus.OK);
    }


} // class LocationController

