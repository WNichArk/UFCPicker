package co.willnicholson;

import com.ftpix.sherdogparser.Sherdog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class App {



    public static void main(String[] args){
    SpringApplication.run(App.class);
    }

    @Bean
    public Sherdog sherdog(){
        return new Sherdog.Builder().build();
    }
}
