package boss.online.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import boss.online.entity.User;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {

	@Bean
	
	AuditorAware<User> auditorAware(){
		return new SpringSecrurityAuditAwareImpl ();
	}
}
