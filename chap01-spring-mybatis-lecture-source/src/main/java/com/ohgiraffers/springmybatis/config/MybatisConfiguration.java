package com.ohgiraffers.springmybatis.config;

import com.ohgiraffers.springmybatis.section01.factorybean.MenuMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {

    @Value("&{spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("&{spring.datasource.jdbc-url}")
    private String jdbcUrl;

    @Value("&{spring.datasource.username}")
    private String username;

    @Value("&{spring.datasource.password}")
    private String password;

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        /* 설명. 데이터베이스에 연결을 시도할 때의 타임아웃(30초(밀리초)) */
        dataSource.setConnectionTimeout(30000);         // 30초 간 연결 유지하려고 try

        /* 설명. 데이터베이스 연결 유휴 상태(쿼리를 보내지 않고 데이터를 업데이트 하지 않는 시간) 유지 최대 시간(10분) */
        dataSource.setIdleTimeout(600000);

        /* 설명. 데이터베이스 연결 최대 유지 시간(30분) */
        dataSource.setMaxLifetime(18000000);

        /* 설명. 미리 만들 커넥션 객체 갯수 */
        dataSource.setMaximumPoolSize(50);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        org.apache.ibatis.session.Configuration configuration =
                        new org.apache.ibatis.session.Configuration();
        configuration.addMapper(MenuMapper.class);

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setConfiguration(configuration);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

}
