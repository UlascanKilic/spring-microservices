package ulascan.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulascan.userservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String code);

}
