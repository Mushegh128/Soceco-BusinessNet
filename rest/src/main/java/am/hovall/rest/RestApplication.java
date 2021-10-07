package am.hovall.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"am.hovall.common.*","am.hovall.rest"})
@EnableJpaRepositories(basePackages = {"am.hovall.common.*","am.hovall.rest"})
@EntityScan({"am.hovall.common.model.entities*"})
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
