package cdw.cdwproject.repository;

import cdw.cdwproject.model.User.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {
    @Query("select u from  User u where u.email = :email")
    User getUserByEmail(@Param("email") String email);

    @Transactional
    @Query("UPDATE User u SET u.name = ?2, u.phone =?3, u.shippingAddress = ?4 WHERE u.id =?1")
    @Modifying
    void updateAccountDetails(int userId, String name, String phone, String shippingAddress);

}
