package fivetwentysix.ware.com.securitytry.config;

//http://websystique.com/spring-boot/spring-boot-rest-api-example/
public class CustomErrorMsg {
    private String errorMsg;

    public CustomErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg(){
        return errorMsg;
    }
}
