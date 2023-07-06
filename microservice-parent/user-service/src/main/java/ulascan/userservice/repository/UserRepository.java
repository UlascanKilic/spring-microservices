package ulascan.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ulascan.userservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByVerificationCode(String code);
    Optional<User> findById(Integer id);
    Optional<User> findByPublicId(Long publicId);

}
