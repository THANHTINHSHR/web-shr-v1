package cdw.cdwproject.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
/*
Đây là lớp user mặc định của spring, ta map lớp user của mình với lớp này ( giống như làm với giao diện là trang login vậy)
 */
public class MyUserDetails implements UserDetails {
    private final User user;
    private String phone;

    public MyUserDetails(User user) {
        this.user = user;
        this.phone = user.getPhone();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getSetRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach((u) -> authorities.add(new SimpleGrantedAuthority(u.getRoleName())));
        authorities.forEach((r) -> System.out.println(r.getAuthority() + "[ROLE]"));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return isEnabled();
        return !user.isLock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
//    return true;
    }
    public int getUserID(){
        return  user.getId();
    }

    public User getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public  String getFullName(){
        return getUsername();
    }
}
