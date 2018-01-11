package fivetwentysix.ware.com.securitytry

import fivetwentysix.ware.com.securitytry.controller.DistanceController
import fivetwentysix.ware.com.securitytry.entity.Distance
import org.junit.Before
import org.junit.Test
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import javax.validation.constraints.AssertTrue
import java.lang.invoke.MethodHandleImpl
import java.nio.charset.Charset
//import java.util.Base64
import org.apache.commons.codec.binary.Base64

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


class DistanceControllerTest  {
    private HttpHeaders headers;
    private HttpEntity<String> requestEntity;
    private String url;
    private RestTemplate restTemplate;

    @Before
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
     //   headers.add(HttpHeaders.AUTHORIZATION,encodeCreds("Test1","Test2"))
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/dist/";
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String foo = "abc";
    }

    void setCreds(String username, String password){
        String plainCreds = username + ":" + password;
        String base64Creds = new String( Base64.encodeBase64(plainCreds.getBytes()));
        headers.add("Authorization", "Basic " + base64Creds);
    }
    //http://www.baeldung.com/java-base64-encode-and-decode
    String encodeCreds(String username, String password){
        return "Basic " + username + ":" + password //new Base64().getEncoder().encodeToString((username + ":" + password).getBytes())
    }
    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

//    restTemplate.exchange
//    (uri, HttpMethod.POST, new HttpEntity<T>(createHeaders(username, password)), clazz);

    //ran after adding a default constructor
    @Test
    void test2GetDistanceId(){
        String goodUri = new String(url +"/distance/427");
        setCreds("test1", "test2")
        requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(goodUri, HttpMethod.GET,requestEntity,String.class);
        assertFalse("Error get distance ID 427",response.statusCode==200)
     //   assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    // TIP - set method in object class typed with int input causing an error here
    @Test
    void testGetAll(){
        String uri = new String(url +"/distances");
        setCreds("test1", "test2")
        requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Distance[]> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Distance[].class);
        Distance[] distances = response.getBody();
        assertTrue("Error - no distances returned",distances.length!=0);
        assertFalse("Error getting all distances",response.statusCode==200)
  //      log.info("Account Content:" + response.getBody());
    }

    /* [DEPRECATION] Encountered positional parameter near line 1, columnas dis 112 in
     HQL: [FROM fivetwentysix.ware.com.securitytry.entity.Distance dist WHERE dist.mileage = ? and dist.distancedate = ?].  Positional parameter are considered deprecated; use named parameters or JPA-style positional parameters instead.
      */
//NEEDS WORK - dist location id doesn't appear in list of written values
    @Test
    void testAddDistance(){
        String goodUri = new String(url +"/distance");
        setCreds("test1","test2")
        Distance dist = new Distance(1,11506916900000,8144.154296875,2916887621408,2948631582919)
        requestEntity = new HttpEntity<String>(headers)
        ResponseEntity<String> st = restTemplate.postForEntity(goodUri, dist, String.class);
      // ResponseEntity<String> response = restTemplate.exchange(goodUri,HttpMethod.POST,dist,requestEntity,String.class)
        String foo = "123"
    }




    void testUpdateDistance() {
    }

    void testDeleteDistance() {
    }
}
