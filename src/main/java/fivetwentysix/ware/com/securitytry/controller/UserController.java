package fivetwentysix.ware.com.securitytry.controller;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.dao.IUserInfoDao;
import fivetwentysix.ware.com.securitytry.dao.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.sql.SQLDataException;
import java.sql.SQLException;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserInfoDao userInfoDao;
    private static final Logger log = LoggerFactory.getLogger(MyApplication.class);
    // WORKS - curl -i -d "@data4.txt" --request POST http://localhost:8080/user/register
    @Transactional
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/register",
            params = {"user", "password", "fullName", "country"})
    String registerUser(@RequestParam("user") String user,
                        @RequestParam("password") String password,
                        @RequestParam("fullName") String fullName,
                        @RequestParam("country") String country
    ) {
        log.info("IN UserController registerUSer");
        BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
        String encodedPw = bEncoder.encode(password);
        try {
            UserInfo userInfo = new UserInfo(user, encodedPw, fullName, country);
            userInfoDao.save(userInfo);
            //userInfo.setCountry(country);
            // _userInfoDao.save(userInfo);
            //TODO need to handle duplicate entry error and report username in use
        }catch (Exception  ex) {
            log.info("Exception saving user info " + ex.getLocalizedMessage());
            log.info("STACK = "+ ex.getStackTrace());
        }
        log.info("In registerUser");
        return encodedPw;
    }

    //>curl -i --request POST --DATA "user=test1&password=test2" http://localhost:8080/user/login -c cok.txt
    @Transactional
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login",
            params = {"user", "password"})
    String loginUser(@RequestParam("user") String user,
                     @RequestParam("password") String password
    ) {
        log.info("UserController - loginUser");
        //       BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
        //       String encodedPw = bEncoder.encode(password);
        try {
            log.info("BEFORE GETACTIVEUSER CALL");
            UserInfo ui = userInfoDao.getActiveUser(user);
            log.info(" PASSED Username = " + user +"Passed password " + password + " retrieved password = " + ui.getPassword() + " Role = "+ui.getRole());
            //    UserInfo userInfo = new UserInfo(user,password);
            //   _userInfoDao.save(userInfo);
            //userInfo.setCountry(country);
            //  _userInfoDao.save(userInfo);
        } catch (Exception ex) {
            log.info("Exception saving user info " + ex.getLocalizedMessage());
            return "fail";
        }
        log.info("In loginUser");
        return "foo";
        //     return encodedPw;
    }
}
