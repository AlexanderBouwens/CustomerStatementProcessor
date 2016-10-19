package nl.rabobank.customerstatementprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The spring boot 'main' class that starts and ends the application.
 * 
 * @author Alexander Bouwens
 *
 */
@SpringBootApplication
public class TransactionHandlingApplication {
	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransactionHandlingApplication.class, args);
        SpringApplication.exit(context);
    }
}
