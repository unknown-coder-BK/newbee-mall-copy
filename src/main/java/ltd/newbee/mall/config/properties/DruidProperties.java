package ltd.newbee.mall.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * druid 配置属性类
 */
@Configuration
public class DruidProperties {

    /**配置bean*/
    public DruidDataSource dataSource(DruidDataSource datasource) {
        return datasource;
    }
}
