package cdw.cdwproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;

@Component
public class SecuriryPasswordGenerator {


    public String encodePassword(String pass){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.encode(pass);
    }
    public boolean isPasswordMatches (String pass, String userPass){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return bcpe.matches(pass, userPass);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        SecuriryPasswordGenerator s = new SecuriryPasswordGenerator();
        encoder.encode("ThanhTinh98");
        System.out.println("pass : ab5f0d");
        System.out.println("encde : " + encoder.encode("ab5f0d").toString());
        System.out.println("is ,mathch  :" + encoder.matches("ab5f0d", encoder.encode("ab5f0d").toString() ));

//      String c =   encoder.encode("741Tinh77");
//        System.out.println(" new code " +  c);
//        String rawPassword = "RpZLnu";
//        String encodePassword = "$2a$10$6yuiMokRi3AQuXA66ywwq.IsJSHnzNIlHHemhFyZt8LEOiK4NnBt6";
//        System.out.println("is match " + encoder.matches(rawPassword, encodePassword));
    }
}
