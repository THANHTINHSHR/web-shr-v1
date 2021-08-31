package cdw.cdwproject.service;

import cdw.cdwproject.model.User.*;
import cdw.cdwproject.repository.RoleRepository;
import cdw.cdwproject.repository.UserRespository;
import cdw.cdwproject.repository.UserRoleRepository;
import cdw.cdwproject.security.SecuriryPasswordGenerator;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRespository.getUserByEmail(email);
    }


    @Override
    public void updateUserAccount(User user) {
        userRespository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRespository.save(user);
    }

    @Override
    public void createOauth2UserAfterLoginSuccess( String email, String name, AuthenticationProvider provider, boolean isAdmin) {


        // create new account.
        User user = new User();
        //  create random password and send pass to user email
        // send email - do later
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        String encrytedPassword = SecuriryPasswordGenerator.encodePassword(randomPassword);

        //set user properties- basic properties the rest will update in web page
        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encrytedPassword);
        user.setEnabled(true);
        user.setAuthenticationProvider(provider);
        System.out.println("name insert " + name);
        System.out.println("user before insert" + user.toString());
        saveUser(user);
        // default create user with role = CUSTOMER.

        Role role = roleRepository.getRoleByRoleName((isAdmin) ? Role.ROLE_ADMIN : Role.ROLE_USER);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        // send email
        try {
            sendOAuth2PassEmail(user, randomPassword);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void saveRegister(User user) {
        String otp = RandomString.make(6);
        String encodedOTP = passwordEncoder.encode(otp);

        user.setOtp(encodedOTP);
        // YYYY-MM-DD hh:mm:ss
        user.setOtpCreateTime(Calendar.getInstance().getTime());
        user.setEnabled(true);
        user.setLock(true);

        saveUser(user);

        try {
            sendOTPEmail(user, otp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkOTPInvalid(String email, String otp) {
        System.out.println("Check otp valid");
        User user = getUserByEmail(email);
        boolean isMatch = passwordEncoder.matches(otp, user.getOtp());
        System.out.println("2 cp : " + otp + "\t" + user.getOtp());
        System.out.println("is match " + isMatch);
        return (isMatch && !user.isOTPExpired());
    }

    @Override
    public void unLockAccount(String email) {
        User user = getUserByEmail(email);
        user.setLock(false);
        saveUser(user);
    }
    public void sendOAuth2PassEmail(User user, String pass)
            throws UnsupportedEncodingException, MessagingException {


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("tromail00@gmail.com", "E STORE Support");
        helper.setTo(user.getEmail());

        String subject = "WELCOME TO E STORE";

        String content = "<p>Hello " + user.getName() + "</p>"
                + "<p>Thanks for join with us, your account </p> "
                + "<p>Email: <b>" + user.getEmail() + "</b></p>"
                + "<br>"
                + "<p>Password: <b>" + pass + "</b></p>"
                + "<br>"
                + "<p>Note: your can use this account or use Google account instead.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);

    }
    public void sendOTPEmail(User user, String otp)
            throws UnsupportedEncodingException, MessagingException {


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("tromail00@gmail.com", "E STORE Support");
        helper.setTo(user.getEmail());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p>Hello " + user.getName() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + otp + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);

    }

    @Override
    public List<User> getAllUser() {
        return userRespository.findAll();
    }

//    @Override
//    public User registerNewUserAccount(UserForm appUserForm, List<String> roleNames) {
//String name = appUserForm.getName();
//        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
//        String encrytedPassword = SecuriryPasswordGenerator.encodePassword(randomPassword);
//
//        //set user properties- basic properties the rest will update in web page
//        User user = new User();
//        user.setName(name);
//        user.setPassword(encrytedPassword);
//        user.setEnabled(true);
//        saveUser(user);
//
//        Role role = roleRepository.getRoleByRoleName(Role.ROLE_USER);
//        UserRole userRole = new UserRole();
//        userRole.setUser(user);
//        userRole.setRole(role);
//        userRoleRepository.save(userRole);
//        return  user;
//
//
//    }


}
