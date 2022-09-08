import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplate {

    private RestTemplate restTemplate = new RestTemplate();
    //在线restFul测试
    String onlineURL = "https://json.im/91bb49ade5df.json";

    /**
     *  get 不带参数
     */
    @Test
    public void test() {
        String urL = "http://jsonplaceholder.typicode.com/posts";
        String response = restTemplate.getForObject(urL, String.class);
        System.out.println(response);
    }

    @Test
    public void test2(){
        String res = restTemplate.getForObject(onlineURL, String.class);
        System.out.println(res);
    }
}
