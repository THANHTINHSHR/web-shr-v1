package cdw.cdwproject.service;

import cdw.cdwproject.model.User.Role;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.User.UserRole;
import cdw.cdwproject.repository.RoleRepository;
import cdw.cdwproject.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImp implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void save(User user, List<String> roleName) {
        for (String s : roleName
        ) {
            Role role = roleRepository.getRoleByRoleName(s);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            save(userRole);
        }

    }
}
