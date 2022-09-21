package core.test;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.test.entity.LocationPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

        //1 请求路径 2 HttpEntity 3 返回类型pojo
        LocationPost locationPost = restTemplate.postForObject(url, httpEntity, LocationPost.class);

        //postForLocation 返回加上参数后的URL
        URI locationPostURI = restTemplate.postForLocation(url, httpEntity);
        Assertions.assertNotNull(locationPost);
    }
}
