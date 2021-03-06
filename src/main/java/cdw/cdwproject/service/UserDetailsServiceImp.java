package cdw.cdwproject.service;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userRespository.getUserByEmail(username);

     if(user==null){
         throw new UsernameNotFoundException("Could not find user");
     }
     return new MyUserDetails(user);
    }
}
