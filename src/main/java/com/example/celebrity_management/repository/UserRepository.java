package com.example.celebrity_management.repository;
import com.example.celebrity_management.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

  
  // @Query(value = "SELECT * FROM users inner join role on users.role_id= id and users.mail_id= mail_id",nativeQuery = true )
  Optional<Users> findByMailId(String mailId);

}