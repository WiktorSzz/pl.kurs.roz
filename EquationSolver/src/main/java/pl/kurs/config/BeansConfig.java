package pl.kurs.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;

@Configuration
public class BeansConfig {

    @Bean
    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

        return mapper;
    }

    @Profile({"dev", "prod", "!prod & !dev"})
    @Bean
    public LocalContainerEntityManagerFactoryBean createEmf(JpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName("myPersistenceUnit");
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("pl.kurs.models");
        return emf;
    }

    @Profile({"prod", "!prod & !dev"})
    @Bean
    public BasicDataSource createDsMySql() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/equation_solver_app?useSSL=false&serverTimezone=CET");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setInitialSize(20);
        return ds;
    }

    @Profile({"prod", "!prod & !dev"})
    @Bean
    public HibernateJpaVendorAdapter createAdapterMySql() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Profile("dev")
    @Bean
    public HibernateJpaVendorAdapter createAdapterH2() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Profile("dev")
    @Bean
    public BasicDataSource getBasicDataSourceDev() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:h2:mem:test");
        ds.setDriverClassName("org.h2.Driver");
        ds.setUsername("sa");
        ds.setUsername("");
        ds.setInitialSize(20);
        return ds;
    }
}
