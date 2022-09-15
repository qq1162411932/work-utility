package core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 13:41
 */


@Configuration
@EnableConfigurationProperties(CoreMybatisProperties.class)
//@ConditionalOnBean(DataSource.class)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MvcConfig implements WebMvcConfigurer {

    /**
     * SQL 日志格式化
     * @return DruidSqlLogFilter
     */
    @Bean
    public DruidSqlLogFilter sqlLogFilter(CoreMybatisProperties properties) {
        return new DruidSqlLogFilter(properties);
    }

    /**
     * MybatisPlus 自动填充配置
     *
     * @author L.cm
     */
}
