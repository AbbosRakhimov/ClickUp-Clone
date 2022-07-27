package boss.online.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import boss.online.entity.User;


public class SpringSecrurityAuditAwareImpl implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		Authentication authenticateAction = SecurityContextHolder.getContext().getAuthentication();
		if (authenticateAction != null && authenticateAction.isAuthenticated()
				&& !authenticateAction.getPrincipal().equals("anonymousUser")) {
			User user = (User) authenticateAction.getPrincipal();
			return Optional.of(user);
		}

		return Optional.empty();
	}

}
