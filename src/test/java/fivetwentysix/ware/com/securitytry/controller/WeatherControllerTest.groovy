package fivetwentysix.ware.com.securitytry.controller

import fivetwentysix.ware.com.securitytry.entity.Weather
import org.apache.commons.codec.binary.Base64
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

class WeatherControllerTest {
    private HttpHeaders headers;
    private HttpEntity<String> requestEntity;
    private String url;
    private RestTemplate restTemplate;
    private String sessionID;

    @Before
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //   headers.add(HttpHeaders.AUTHORIZATION,encodeCreds("Test1","Test2"))
        restTemplate = new RestTemplate();
        url = "http://localhost:8080/weather/";
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        //restTemplate.getMessageConverters().add(new FormatHttpMessageConverter());

        String foo = "abc";
    }

    void setCreds(String username, String password){
        String plainCreds = username + ":" + password;
        String base64Creds = new String( Base64.encodeBase64(plainCreds.getBytes()));
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.TEXT_PLAIN)
//        headers.add("Content-type","test/plain;charset=utf-8");
//        headers.setAcceptCharset()
//        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    //NEEDS WORK - getting 200 OK but no content - cause missing default constructor
    @Test
    void testGetAll(){
        String uri = new String(url +"/getAll");
        setCreds("test1", "test2")
        requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Weather[]> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Weather[].class);
        Weather[] weather = response.getBody();
        assertTrue("Error - no weather returned",weather.length!=null);
        assertFalse("Error getting all weather",response.statusCode==200)
        //      log.info("Account Content:" + response.getBody());
    }
    @Test
    void testGetWeatherId(){
        String goodUri = new String(url +"/get/760");
        setCreds("test1", "test2")
        requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(goodUri, HttpMethod.GET,requestEntity,String.class);
        assertFalse("Error get weather ID 760",response.statusCode==200)
        //   assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    //this worked when run as unit test 11/21/17
@Test
    public void testRegisterUser() {
    //String casUrlPrefix = "http://login-test.his.com/tcgroup-sso-web";
    String casUrlPrefix = "http://localhost:8080"
    String username = "test01";
    String password = "test02";

    // GET TGT
    RestTemplate rest = new RestTemplate();
    rest.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));
    MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    params.set("user", username);
    params.set("password", password);
    params.set("fullName", "StinkFinger")
    params.set("country","en")
    URI tgtUrl = rest.postForLocation(casUrlPrefix + "/user/register", params, Collections.emptyMap());
    System.out.println("[" + tgtUrl + "]");
}
    //login part worked but had to remove permissions check in controller
@Test
    public void testLoginUser() {
    String casUrlPrefix = "http://localhost:8080"
    String username = "test1";
    String password = "test2";

    // GET TGT
    RestTemplate rest = new RestTemplate();
    rest.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));

    MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, String>();
    params.set("user", username);
    params.set("password", password);

    String tgtUrl = rest.postForLocation(casUrlPrefix + "/user/login", params, String);
    System.out.println("WHODO")
}


    //>curl -i --request  POST --data "user=test1&password=test2" http://localhost:8080/user/login - ne sessionID
    @Test
    public void testNewLoginApproach(){
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, String>();
        params.set("user", "test1");
        params.set("password", "test2");
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/login/",params,String.class);
        //List<String>response = restTemplate.postForEntity()
        sessionID = response.getHeaders().get("Set-Cookie")
        System.out.println("JSESSIONID" + response.getHeaders().get("Set-Cookie"))
//this gets the jsessionid response.getHeaders().get("Set-Cookie").get(0)
    }

    // Sends the username and password with the post request to the server
    @Test
    public void testWriteWeatherWithUsernameAndPassword(){
        String goodUri = new String(url +"save");
        setCreds("test1", "test2")

     //   headers.put(HttpHeaders.COOKIE,sessionID)
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        MultiValueMap<String,?> uriVar = new LinkedMultiValueMap<String, String>()

        uriVar.set("location","1")
        uriVar.set("date","11506916900001")
        uriVar.set("short_desc", "fart")
        uriVar.set("temp", "72.5")
        uriVar.set("humidity", "97")
        uriVar.set("pressure","101")
        uriVar.set("wind","1.11")
        uriVar.set("degrees","NW")
        uriVar.set("storm","1")
        HttpEntity<Void> requestEntity = new HttpEntity<>(uriVar,headers)

        ResponseEntity<String> response =restTemplate.postForEntity(goodUri,requestEntity,String.class)
        assertFalse("Error Writing Weather Record",response.statusCode==200)
    }
    // This tests logging in and then using the resulting sessionID to write the weather
    @Test
    public void testWriteWeatherLoginFirst(){
        String goodUri = new String(url +"save");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, String>();
        params.set("user", "test1");
        params.set("password", "test2");
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/login/",params,String.class);
        //List<String>response = restTemplate.postForEntity()
        String sessionID = response.getHeaders().get("Set-Cookie")
        String[] doo = sessionID.split(";")
        String jSessionID = doo[0].replace("[","")
     //   String jSessionID = sessionID.replace("[","").replace("]","")
        // now write the record
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        headers.add(HttpHeaders.SET_COOKIE,jSessionID)
        MultiValueMap<String,?> uriVar = new LinkedMultiValueMap<String, String>()

        uriVar.set("location","1")
        uriVar.set("date","11506916900001")
        uriVar.set("short_desc", "fart")
        uriVar.set("temp", "72.5")
        uriVar.set("humidity", "97")
        uriVar.set("pressure","101")
        uriVar.set("wind","1.11")
        uriVar.set("degrees","NW")
        uriVar.set("storm","1")
        HttpEntity<Void> requestEntity = new HttpEntity<>(uriVar,headers)

        ResponseEntity<String> response2 =restTemplate.postForEntity(goodUri,requestEntity,String.class)
        assertFalse("Error Writing Weather Record",response.statusCode==200)
    }
}
