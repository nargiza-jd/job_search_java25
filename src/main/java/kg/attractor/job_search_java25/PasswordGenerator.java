package kg.attractor.job_search_java25;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String plainPassword = "password123";
        String hashedPassword = passwordEncoder.encode(plainPassword);

        System.out.println("Plain password: " + plainPassword);
        System.out.println("Hashed password (для вставки в Liquibase/DB): " + hashedPassword);
    }
}