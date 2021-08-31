package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.*;
import cdw.cdwproject.model.category.Category;
import cdw.cdwproject.model.product.Product;
import cdw.cdwproject.oauth.*;
import cdw.cdwproject.service.CategoryServiceImp;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.UserRoleServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private UserRoleServiceImp userRoleServiceImp;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping({"/", "home"})
    public String body(Model model) {
        List<Product> featuredProducts = productService.getFeaturedProducts();
        List<Product> newProducts = productService.getNewProduct();
        List<Category> categories = categoryServiceImp.getAllCategory();

        model.addAttribute("fProducts", featuredProducts);
        model.addAttribute("nProducts", newProducts);
        model.addAttribute("categories", categories);
        return "normal/body";
    }
//    @GetMapping("/")
//    public String index(Model model){
//
//
//    }

    @GetMapping("/register")
    public String showRegister(UserForm UserForm) {

//        User user = new User();
//        model.addAttribute("user", user);
        return "normal/register";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form mục tiêu
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(userValidator);
        }
        // ...
    }

    @PostMapping("/register")
    public String doRegister(Model model, @Valid UserForm userForm, BindingResult bResult, final RedirectAttributes redirectAttributes) {

        // Validate result
        if (bResult.hasErrors()) {

            System.out.println("error validate register");
//            System.out.println(bResult.get);
            model.addAttribute("UserForm", userForm);
            return "normal/register";
        }
//        String otp = RandomString.make(6);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(userForm.getEmail());
//        message.setSubject("Test Simple Email");
//        message.setText(otp);
//        emailSender.send(message);
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setPhone(userForm.getPhone());
        user.setAuthenticationProvider(AuthenticationProvider.LOCAL);
        System.out.println(user);


        userServiceImp.saveUser(user);

        List<String> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        userRoleServiceImp.save(user, roles);
        user = userServiceImp.getUserByEmail(user.getEmail());


        userServiceImp.saveRegister(user);


//        userServiceImp.sentOTP(userServiceImp.getUserByEmail(user.getEmail()));

        System.out.println("register success");
        model.addAttribute("email", userForm.getEmail());
//        return "redirect:/otp-register";
        return showOTP(model);
//        return showOTP(model);
    }

    //
    @RequestMapping(value = "/otp-register")
    public String showOTP(Model model) {
        if (model.getAttribute("email") == null) {
            return "redirect:/";
        } else {
            return "normal/otp-register";

        }
    }

    @PostMapping(value = "/otp-register")
    public String checkOTP(Model model, @RequestParam("otp") String otp, @RequestParam("email") String email) {
        boolean check = userServiceImp.checkOTPInvalid(email, otp);

        // if incorrect, unlock account
        if (check) {
            userServiceImp.unLockAccount(email);
            return "redirect:/login";
        } else {
            model.addAttribute("errorMess", "OTP incorrect or expired");
            return "normal/otp-register";
        }
    }

    @PostMapping(value = "/otp-resend")
    public String resendOTP(Model model, @RequestParam("email") String email) {
     User    user = userServiceImp.getUserByEmail(email);

        userServiceImp.saveRegister(user);
        System.out.println("RESEND OTP");
        return "normal/otp-register";
    }


    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (request.getAttribute("avs") == null || request.getAttribute("avs") == "") {
//            System.out.println("avs empty");
//        }

        return (authentication == null || authentication instanceof AnonymousAuthenticationToken) ? "normal/login" : "redirect:/home";

//        return  "normal/login";
    }

    @GetMapping("/login-fail")
    public String failLogin(Model model, @Param("error") String error) {
        System.out.println("avs empty log fail head");


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        if (error == null) {
            System.out.println("log faile error code null");
            return "redirect:/login";
        } else {
            System.out.println("log faile error code not null");


            System.out.println("in to error");
            String eMess;
            switch (error) {
                case "2":
                    eMess = "User disabled";
                    break;
                case "3":
                    eMess = "Account expired";
                    break;
                case "4":
                    eMess = "User account is locked";
                    break;
                default:
                    eMess = "Invalid email or password";
                    break;
            }
            System.out.println("avs empty log fail tail . eMess");
            model.addAttribute("eMess", eMess);
            return "normal/login-fail";
        }
    }

    // mod to make sure when enable cookie and reload. it will be change result ( to home page)
    @RequestMapping(value = "/cookie-require")
    public String cookieRequire(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "error/cookie-require";

        } else {
            return "redirect:/home";
        }

    }

    @RequestMapping(value = "/contact")
    public String showContact() {
        return "normal/contact";
    }

    @RequestMapping(value = "/log-problem")
    public String showlogProblem() {
        return "normal/log-problem";
    }


}
