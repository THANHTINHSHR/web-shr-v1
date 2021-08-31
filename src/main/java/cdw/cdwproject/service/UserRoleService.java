package cdw.cdwproject.service;

import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.User.UserRole;

import java.util.List;

public interface UserRoleService {
    void save(UserRole userRole);
    void save (User user, List<String> roleName);
}
