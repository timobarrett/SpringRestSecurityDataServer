package fivetwentysix.ware.com.securitytry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@SpringBootApplication
public class MyApplication {
    private static final Logger log = LoggerFactory.getLogger(MyApplication.class);
    public static void main(String[] args) {
        log.info("in MAIN");
        SpringApplication.run(MyApplication.class, args);
    }
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
}
