package boss.online.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import boss.online.entity.UserAndWorkspace;

public interface UserAndWorkspaceRepository extends JpaRepository<UserAndWorkspace, UUID>{

	boolean existsByUserIdAndWorkspaceId(UUID uuid, Long id);
	
	Optional<UserAndWorkspace> findByUserIdAndWorkspaceId(UUID uuid, Long id);
	
	@Transactional
	@Modifying
	void deleteByUserIdAndWorkspaceId(UUID uuid, Long id);

	List<UserAndWorkspace> findAllByWorkspaceId(Long id);

	List<UserAndWorkspace> findAllByUserId(UUID id);
}
