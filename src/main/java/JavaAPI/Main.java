package JavaAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"JavaAPI"})
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
}
