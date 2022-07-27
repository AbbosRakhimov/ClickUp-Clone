package boss.online.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boss.online.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndEmailCode(String email, String emailCode);
}
