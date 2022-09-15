package core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 9:57
 */

@Data
//@RefreshScope cloud 注解
@ConfigurationProperties("core.mybatis")
public class CoreMybatisProperties {

    /**
     * 是否打印可执行 sql
     */
    private boolean showSql = true;

}
