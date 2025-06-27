package dev.lunna.panel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan
@EnableJpaRepositories(basePackages = "dev.lunna.panel")
public class PanelApplication {
  public static void main(String[] args) {
    SpringApplication.run(PanelApplication.class, args);
  }
}
