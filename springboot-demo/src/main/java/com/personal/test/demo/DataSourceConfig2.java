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
@MapperScan(basePackages="com.personal.test.demo.mod2.dao", sqlSessionFactoryRef="sqlSessionFactory2")
public class DataSourceConfig2 {
    
    @Bean
    public DataSource dataSource2() throws PropertyVetoException {
        ComboPooledDataSource rlt = new ComboPooledDataSource();
        rlt.setDriverClass("com.mysql.jdbc.Driver");
        rlt.setUser("root");
        rlt.setPassword("password");
        rlt.setJdbcUrl("jdbc:mysql://localhost:3306/test2");
        rlt.setInitialPoolSize(10);
        rlt.setMaxPoolSize(10);
        rlt.setMinPoolSize(10);
        rlt.setTestConnectionOnCheckin(true);
        rlt.setPreferredTestQuery("select 1");
        
        return rlt;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:sqlMap/database2/*.xml"));
        return bean.getObject();
    }
}
