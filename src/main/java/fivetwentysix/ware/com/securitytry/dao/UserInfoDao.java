package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.dao.UserInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class UserInfoDao implements IUserInfoDao {
    @PersistenceContext
    private EntityManager entityManager;

//   @Autowired
   // private SessionFactory _sessionFactory;
  // @Bean
    //private Session getSession() {
//        return _sessionFactory.getCurrentSession();
//    }
    public void save(UserInfo userInfo) {
        //getSession().save(userInfo);
        entityManager.persist(userInfo);
    }

    public UserInfo getActiveUser(String userName) {
        Logger log = LoggerFactory.getLogger(MyApplication.class);
        String creds = "Dial0gicRots!";
        String encoded = new String(Base64.encodeBase64(creds.getBytes()));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String doo = bCryptPasswordEncoder.encode(creds.toString());
        log.info("ENCODED CRED = "+encoded + "ENCODED2 = " + doo);
        //ENCODED CRED = RGlhbG9naWNSb3RzENCODED2 = $2a$10$T2wBZNL4IZDa5mb5KRCTT.2bqGnZesDXA6XdbMYDZpC4XwpZwrVFS --> Dial0gicRots
        //ENCODED CRED = RGlhbG9naWNSb3RzIQ==ENCODED2 = $2a$10$UKwP6p/u7znYr1q9VoSl6.E4dvH9KDVn9lUNvKDeUDEf1YFiEnPaC -->Dial0gicRots!
        UserInfo activeUserInfo = new UserInfo();
        short enabled = 1;
        List<?> list = entityManager.createQuery("SELECT u FROM UserInfo u WHERE userName=? and enabled=?")
                .setParameter(1, userName).setParameter(2, enabled).getResultList();
        if(!list.isEmpty()) {
            activeUserInfo = (UserInfo)list.get(0);
            log.info("getActiveUser = list not empty");
            log.info("username = "+activeUserInfo.getUserName() + " password ="+activeUserInfo.getPassword() + "authority = "+activeUserInfo.getRole());
            log.info("username size = "+activeUserInfo.getUserName().length()+"password size = "+activeUserInfo.getPassword().length()+" authority = "+activeUserInfo.getRole().length());
        }
        log.info("leaving getActiveUser");
        return activeUserInfo;
    }

}
