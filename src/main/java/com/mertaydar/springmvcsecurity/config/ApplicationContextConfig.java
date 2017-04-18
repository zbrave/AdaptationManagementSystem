package com.mertaydar.springmvcsecurity.config;
 
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
//import org.o7planning.springmvcsecurity.dao.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.MarkDAO;
import com.mertaydar.springmvcsecurity.dao.RulesDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.dao.impl.DeptDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.MarkDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.RulesDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.StudentDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.StudentLessonDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.SubstituteLessonDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.TakingLessonDAOImpl;
import com.mertaydar.springmvcsecurity.dao.impl.UniDAOImpl;
 
@Configuration
@ComponentScan("com.mertaydar.springmvcsecurity.*")
@EnableTransactionManagement
// Load to Environment.
@PropertySource("classpath:datasource-cfg.properties")
public class ApplicationContextConfig {
 
  // The Environment class serves as the property holder
  // and stores all the properties loaded by the @PropertySource
  @Autowired
  private Environment env;
 
 // @Autowired
//  private UserInfoDAO userInfoDAO;
 
  @Bean
  public ResourceBundleMessageSource messageSource() {
      ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
      // Load property in message/validator.properties
      rb.setBasenames(new String[] { "messages/validator" });
      return rb;
  }
 
  @Bean(name = "viewResolver")
  public InternalResourceViewResolver getViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/pages/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
  }
 
  @Bean(name = "dataSource")
  public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
      // See: datasouce-cfg.properties
      dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
      dataSource.setUrl(env.getProperty("ds.url"));
      dataSource.setUsername(env.getProperty("ds.username"));
      dataSource.setPassword(env.getProperty("ds.password"));
 
      System.out.println("## getDataSource: " + dataSource);
 
      return dataSource;
  }
  
  @Autowired
  @Bean(name = "sessionFactory")
  public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
      Properties properties = new Properties();
     
      // See: ds-hibernate-cfg.properties
      properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
      properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      properties.put("current_session_context_class", env.getProperty("current_session_context_class"));
       
  
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setPackagesToScan(new String[] { "com.mertaydar.springmvcsecurity.entity" });
      factoryBean.setDataSource(dataSource);
      factoryBean.setHibernateProperties(properties);
      factoryBean.afterPropertiesSet();
      //
      SessionFactory sf = factoryBean.getObject();
      return sf;
  }
  
  @Autowired
  @Bean(name = "transactionManager")
  public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
  
      return transactionManager;
  }
 
  // Transaction Manager
/*  @Autowired
  @Bean(name = "transactionManager")
  public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
      DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
 
      return transactionManager;
  }*/
  
  @Bean(name = "UniDAO")
  public UniDAO getUniDAO() {
      return new UniDAOImpl();
  }
  
  @Bean(name = "DeptDAO")
  public DeptDAO getDeptDAO() {
      return new DeptDAOImpl();
  }
  
  @Bean(name = "TakingLessonDAO")
  public TakingLessonDAO getTakingLessonDAO() {
      return new TakingLessonDAOImpl();
  }
  
  @Bean(name = "SubstituteLessonDAO")
  public SubstituteLessonDAO getSubstituteLessonDAO() {
      return new SubstituteLessonDAOImpl();
  }
  
  @Bean(name = "RulesDAO")
  public RulesDAO getRulesDAO() {
      return new RulesDAOImpl();
  }
  
  @Bean(name = "StudentDAO")
  public StudentDAO getStudentDAO() {
      return new StudentDAOImpl();
  }
  
  @Bean(name = "StudentLessonDAO")
  public StudentLessonDAO getStudentLessonDAO() {
      return new StudentLessonDAOImpl();
  }
  
  @Bean(name = "MarkDAO")
  public MarkDAO getMarkDAO() {
	  return new MarkDAOImpl();
  }
 
}