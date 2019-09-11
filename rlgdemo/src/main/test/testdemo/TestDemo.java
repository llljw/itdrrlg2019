package testdemo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 16:52
 */
public class TestDemo {
    @Test
    public void test(){
        ApplicationContext a=new ClassPathXmlApplicationContext("spring.xml");
        DriverManagerDataSource dataSource = (DriverManagerDataSource) a.getBean("dataSource");
        String url = dataSource.getUrl();
        System.out.println(url);
    }
}
