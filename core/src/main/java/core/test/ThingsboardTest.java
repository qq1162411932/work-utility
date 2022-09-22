package core.test;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.test.entity.LocationPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/20 11:39
 */
public class ThingsboardTest {

    String baseUrl = "http://39.101.175.163";
    String name = "tenant@thingsboard.org";
    String pas = "tenant456";

    private RestTemplate restTemplate = new RestTemplate();

    String authUrl = baseUrl + "/api/auth/login";


    @Test
    public void getAuth() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", pas);
        //uri变量 路径参数
        String userJson = JSON.toJSONString(map);

//       HttpHeaders headers = new HttpHeaders();
        HttpEntity userEntity = new HttpEntity(userJson);
//        ResponseEntity<ThingsBoardUserAuth> thingsBoardUserAuthResponseEntity = restTemplate.postForEntity(authUrl, userEntity, ThingsBoardUserAuth.class);
        ResponseEntity<String> thingsBoardUserAuthResponseEntity = restTemplate.postForEntity(authUrl, userEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(thingsBoardUserAuthResponseEntity.getBody());
        JsonNode token = root.path("token");
        Assertions.assertNotNull(token.asText());
    }

    /**
     * 测试getObject() 路径中带参数
     */
    @Test
    public void testGetObject() {
        String url = "http://jsonplaceholder.typicode.com/albums/id";
//        Album album = new Album(1);
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
//        restTemplate.setDefaultUriVariables(map);
        String res = restTemplate.getForObject(url, String.class);
    }


    @Test
    public void testAsset() {
        System.out.println(HttpStatus.OK);
//        Assertions.assertEquals("1", "2");
        Assertions.assertNotNull("1");
    }

    @Test
    public void testLocation(){
        String url = "https://jsonplaceholder.typicode.com/posts";

        Map<String, String> map = new HashMap<>();
        map.put("u","hug me");
        map.put("token", "token: Bear");
        // body key:value map或者对象
        HttpEntity<Map> httpEntity = new HttpEntity(map);
//        HttpHeaders headers = new HttpHeaders();


        //1 请求路径 2 HttpEntity 3 返回类型pojo
        LocationPost locationPost = restTemplate.postForObject(url, httpEntity, LocationPost.class);

        //postForLocation 返回加上参数后的URL
        URI locationPostURI = restTemplate.postForLocation(url, httpEntity);

        // responseEntity 继承了HttpEntity类 属性多了status
//        ResponseEntity<Object> response = restTemplate.postForEntity();
        Assertions.assertNotNull(locationPost);
    }

    /**
     * MultiValueMap 一个key对应多个value
     * set 添加一个值的， add 添加对应多个值
     */
    //
    @Test
    public void testMultiValueMap(){
        MultiValueMap<String, String>  linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("name", "test1");
        linkedMultiValueMap.add("name", "test2");
        System.out.println(linkedMultiValueMap);
    }

    /**
     * optionForAllow
     */
    @Test
    public void testOptionForAllow(){
        String url = "https://jsonplaceholder.typicode.com/posts";
        Map<String, String> map = new HashMap<>();
        map.put("u","hug me");
        map.put("token", "token: Bear");

        Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow("url", map);
        HttpMethod[] supportedMethods
                = {HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
        Assertions.assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
    }


    /**
     * 手机号验证
     */
    //验证是否为 手机号(手机)
    boolean yanZh_dianH_shouJ(String s_ds){
        if(s_ds.matches("^[1][3,4,5,7,8,9][0-9]{9}$")){
            return true;
        }else{
            return false;
        }
    }

    @Test
    public void testVerifyPhone(){
        yanZh_dianH_shouJ("1234");
    }

}
