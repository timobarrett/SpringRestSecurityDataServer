package fivetwentysix.ware.com.securitytry.config;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.dao.IUserInfoDao;
import fivetwentysix.ware.com.securitytry.dao.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);
    @Autowired
    private IUserInfoDao userInfoDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo activeUserInfo = userInfoDAO.getActiveUser(userName);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
        log.info("loadUserByUsername role = " + activeUserInfo.getRole());
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = (UserDetails) new User(activeUserInfo.getUserName(),
                //activeUserInfo.getPassword(), Arrays.asList(authority));
                activeUserInfo.getPassword(),authList   );
        log.info("Leaving loadUserByUsername---");
        log.info("aaaa ="+userDetails.getPassword()+ userDetails.getUsername()+userDetails.getAuthorities());
        return userDetails;
    }
}
