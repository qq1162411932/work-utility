package core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import  core.config.CoreMybatisProperties;

/**
 * 主类命名格式 (叶子包名+Application)
 * @author 86139
 */
@MapperScan("core.mapper")
@SpringBootApplication
@EnableConfigurationProperties(CoreMybatisProperties.class)
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
