package fivetwentysix.ware.com.securitytry.controller;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.Service.IDistanceService;
import fivetwentysix.ware.com.securitytry.entity.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("user")
public class DistanceController {
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);
    @Autowired
    private IDistanceService distanceService;
    @GetMapping("distance/{id}")
    public ResponseEntity<Distance> getDistanceById(@PathVariable("id") Integer id) {
        log.info("getDistanceById");
        Distance distance = distanceService.getDistanceById(id);
        return new ResponseEntity<Distance>(distance, HttpStatus.OK);
    }
    @GetMapping("distances")
    public ResponseEntity<List<Distance>> getAllDistances() {
        log.info("getAllDistances");
        List<Distance> list = distanceService.getAllDistance();
        return new ResponseEntity<List<Distance>>(list, HttpStatus.OK);
    }
    @PostMapping("distance")
    public ResponseEntity<Void> addDistance(@RequestBody Distance distance, UriComponentsBuilder builder) {
        log.info("addDistance");
        boolean flag = distanceService.addDistance(distance);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/distance/{id}").buildAndExpand(distance.getLocation_id()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    @PutMapping("distance")
    public ResponseEntity<Distance> updateDistance(@RequestBody Distance distance) {
        distanceService.updateDistance(distance);
        return new ResponseEntity<Distance>(distance, HttpStatus.OK);
    }
    @DeleteMapping("distance/{id}")
    public ResponseEntity<Void> deleteDistance(@PathVariable("id") Integer id) {
        distanceService.deleteDistance(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
