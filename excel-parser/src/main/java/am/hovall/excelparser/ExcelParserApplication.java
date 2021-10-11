package am.hovall.excelparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"am.hovall.common.*","am.hovall.excelparser.*"})
@EnableJpaRepositories(basePackages = {"am.hovall.common.repositories*"})
@EntityScan({"am.hovall.common.model.*"})
public class ExcelParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelParserApplication.class, args);
    }

}
