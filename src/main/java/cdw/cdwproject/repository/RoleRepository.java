package cdw.cdwproject.repository;

import cdw.cdwproject.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r  WHERE r.id = ?1")
    Role getRoleNameByID(int roleID);
    @Query("SELECT r FROM Role r WHERE r.roleName = ?1")
    Role getRoleByRoleName(String roleName);
}
