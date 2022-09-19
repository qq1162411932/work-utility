package core.utils.http;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/19 16:26
 */

public class RestTemplateUtils {

    /**
     * spring-web
     */

    private  RestTemplate restTemplate = new RestTemplate();


    /**
     * 带token
     * get请求参数  路径参数/表单参数/请求参数
     * post请求   表单参数
     *
     * 不带token
     *
     *
     */
    @Test
    public void testRest(){
//        restTemplate.postForEntity();
    }
}
