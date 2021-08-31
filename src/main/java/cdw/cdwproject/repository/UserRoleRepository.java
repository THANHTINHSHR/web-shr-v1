package cdw.cdwproject.repository;

import cdw.cdwproject.model.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("SELECT r.roleName FROM Role r , UserRole  ur WHERE r.id = ur.id and ur.user.id = ?1 ")
    List<String> getUserRolesByUserId(int userID);

}
