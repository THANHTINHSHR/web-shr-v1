package cdw.cdwproject.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;

@Component
public class SecuriryPasswordGenerator {

    public static  String encodePassword(String pass){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.encode(pass);
    }
    public boolean isPasswordMatches (String pass, String userPass){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.matches(pass, userPass);
    }
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "RpZLnu";
        String encodePassword = "$2a$10$6yuiMokRi3AQuXA66ywwq.IsJSHnzNIlHHemhFyZt8LEOiK4NnBt6";
        System.out.println("is match " + encoder.matches(rawPassword, encodePassword));
    }
}
