package ordersTrackingSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserDetailsManagerSecurity {
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		System.out.println(passwordEncoder.getClass());
		org.springframework.security.core.userdetails.UserDetails user = User.withUsername("shivani")
				.password(passwordEncoder.encode("shivani")).roles("USER").build();
		org.springframework.security.core.userdetails.UserDetails admin = User.withUsername("admin")
				.password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
}
