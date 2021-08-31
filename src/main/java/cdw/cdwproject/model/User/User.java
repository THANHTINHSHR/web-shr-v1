package cdw.cdwproject.model.User;


import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "USER_NAME")
    private String name;
    @Column(name = "PHONE")
    @NumberFormat
    private String phone;
    @NotEmpty
    @Email
    @Column(name = "EMAIL", unique = true)
//	@Email(message = "Email invalid")
    private String email;
    @Column(name = "SHIPPING_ADDRESS")
    private String shippingAddress;
    @NotEmpty
    @Column(name = "USER_PASS")

    private String password;
    @Column(name = "ENABLED")
    private boolean enabled;
    @Column(name = "AUTH_PROVIDER")
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authenticationProvider;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> setRoles = new HashSet<>();
    @Column(name = "OTP")
    private String otp;
    @Column(name = "OTP_CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpCreateTime;
    @Column(name = "LOCKED")
    private boolean lock;

    public User() {
        // TODO Auto-generated constructor stub
    }


    public User(int id, String name, String phone, String email, String paymentAddress, String shippingAddress, String password, boolean enabled) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.password = password;
        this.enabled = enabled;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getSetRoles() {
        return setRoles;
    }

    public void setSetRoles(Set<Role> setRoles) {
        this.setRoles = setRoles;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtpCreateTime() {
        return otpCreateTime;
    }

    public void setOtpCreateTime(Date otpCreateTime) {
        this.otpCreateTime = otpCreateTime;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isOTPExpired() {
        System.out.println("current time " + System.currentTimeMillis());
        System.out.println("otp expired time " + (OTP_VALID_DURATION + otpCreateTime.getTime()));
        System.out.println("is expired " + (System.currentTimeMillis() > OTP_VALID_DURATION + otpCreateTime.getTime()));
        return (System.currentTimeMillis() > OTP_VALID_DURATION + otpCreateTime.getTime());
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authenticationProvider=" + authenticationProvider +
                ", otp='" + otp + '\'' +
                ", otpLifeTime=" + otpCreateTime +
                ", lock=" + lock +
                '}';
    }
}
