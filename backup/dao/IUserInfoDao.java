package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.dao.UserInfo;

//@RepositoryRestResource(collectionResourceRel = "user",path="user")
public interface IUserInfoDao {//extends PagingAndSortingRepository<UserInfo, String> {
        UserInfo getActiveUser(String userName);
     //   void save(UserInfo userInfo);
}
