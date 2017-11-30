package fivetwentysix.ware.com.securitytry.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

    @Entity
    @Table(name="user")  //commenting this out results in org.hibernate.hql.internal.ast.QuerySyntaxException: UserInfo is not mapped error
    public class UserInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name="username")
        private String userName;
        @Column(name="password")
        private String password;
        @Column(name="full_name")
        private String fullName;
        @Column(name="role")
        private String role;
        @Column(name="country")
        private String country;
        @Column(name="enabled")
        private short enabled;

        public UserInfo(){}

        public UserInfo (String user, String password,String fullName, String country){
            this.userName = user;
            this.password = password;
            this.fullName = fullName;
            this.country = country;
            this.role = "ROLE_USER";
            //for now - this should be set if the save is successful
            this.enabled = 1;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {this.password = password;}//bCryptPasswordEncoder.encode(password); }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
        public String getFullName() {
            return fullName;
        }
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public short getEnabled() {
            return enabled;
        }
        public void setEnabled(short enabled) {
            this.enabled = enabled;
        }

    }
