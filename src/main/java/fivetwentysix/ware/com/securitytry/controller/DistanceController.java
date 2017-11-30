package fivetwentysix.ware.com.securitytry.controller;

import antlr.RecognitionException;
import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.Service.IDistanceService;
import fivetwentysix.ware.com.securitytry.config.CustomErrorMsg;
import fivetwentysix.ware.com.securitytry.entity.Distance;
import org.hibernate.hql.internal.ast.ErrorReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.ResponseWrapper;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
//@RequestMapping("user")
@RequestMapping("dist")
public class DistanceController {
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);
    @Autowired
    private IDistanceService distanceService;

    // this works
    //>curl -i --request GET -u test1:test2 http://localhost:8080/dist/distance/427
    //http://websystique.com/spring-boot/spring-boot-rest-api-example/
    //return 404 if record is not found
    @PreAuthorize("hasAuthority('ROLE_USER')") //got a 401 event though my antMatcher was set to allow all for dist
    @GetMapping("distance/{id}")
    public ResponseEntity<?> getDistanceById(@PathVariable("id") Integer id) throws ResourceNotFoundException{
        log.info("getDistanceById");
        Distance distance = distanceService.getDistanceById(id);
        if (distance == null){
           // return new ResponseEntity<CustomErrorMsg>(new CustomErrorMsg("Distance ID " + id + " Not Found"),HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Distance ID " + id + " Not Found");
        }
        return new ResponseEntity<Distance>(distance, HttpStatus.OK);
    }
    //curl -i --request GET -u test1:test2 http:/localhost:8080/dist/distances
 //   @PreAuthorize("isAuthenticated()")
   // @GetMapping("distances")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value="/distances",method=GET,produces="application/json")
    public ResponseEntity<List<Distance>> getAllDistances() {
        log.info("getAllDistances");
     //   String uName = principal.getName();
        List<Distance> list = distanceService.getAllDistance();
        return new ResponseEntity<List<Distance>>(list, HttpStatus.OK);
    }
    //https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.web.client.RestTemplate
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("distance")
    public ResponseEntity<Void> addDistance(@RequestBody Distance distance){//, UriComponentsBuilder builder) {
        log.info("addDistance");
        boolean flag = distanceService.addDistance(distance);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(builder.path("/distance/{id}").buildAndExpand(distance.getLocation_id()).toUri());
        return new ResponseEntity<Void>( HttpStatus.CREATED);

    }
    @PutMapping("distance")
    public ResponseEntity<Distance> updateDistance(@RequestBody Distance distance) {
        distanceService.updateDistance(distance);
        return new ResponseEntity<Distance>(distance, HttpStatus.OK);
    }
    //verified ? by using a account with USER role only
    @PreAuthorize("hasAuthority('ADMIN')")
   // curl -i --request DELETE -u tim:Dial0gicRots! http:/localhost:8080/dist/delete/425
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteDistance(@PathVariable("id") Integer id) {
        log.info("deleteDistance");
        distanceService.deleteDistance(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
