package com.personal.test.demo;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@MapperScan(basePackages = "com.personal.test.demo.mod2.dao",
    sqlSessionFactoryRef = "sqlSessionFactory2")
public class DataSourceConfig2 {

    private static final int DEFAULT_POOL_SIZE = 10;

    /**
     * 数据源2
     */
    @Bean
    public DataSource dataSource2() throws PropertyVetoException {
        ComboPooledDataSource rlt = new ComboPooledDataSource();
        rlt.setDriverClass("com.mysql.jdbc.Driver");
        rlt.setUser("root");
        rlt.setPassword("password");
        rlt.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        rlt.setInitialPoolSize(DEFAULT_POOL_SIZE);
        rlt.setMaxPoolSize(DEFAULT_POOL_SIZE);
        rlt.setMinPoolSize(DEFAULT_POOL_SIZE);
        rlt.setTestConnectionOnCheckin(true);
        rlt.setPreferredTestQuery("select 1");

        return rlt;
    }

    /**
     * sqlSession工厂2
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory2(
            @Qualifier("dataSource2") final DataSource dataSource)
                    throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:sqlMap/database2/*.xml"));
        return bean.getObject();
    }
}
