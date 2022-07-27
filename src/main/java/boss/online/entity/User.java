package boss.online.entity;

import java.awt.Color;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import boss.online.entity.enums.SystemRoleName;
import boss.online.entity.enums.WorkspaceRoleName;
import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbsUUIDEntity implements UserDetails {

	@Column(nullable = false)
	private String fullName;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String color;
	
	private String emailCode;
	
	private Boolean mailCode=true;
	
	private String initialLetter;
	
	private Timestamp lastActive;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "users_role", joinColumns = {@JoinColumn(name="users_id")}, inverseJoinColumns = {@JoinColumn(name="role_id")})
//	private Set<Role> roles;
	
	@Enumerated(EnumType.STRING)
	private SystemRoleName systemRoleName;
	
//	@JoinColumn
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Attachment attachment;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<UserAndWorkspace> userAndWorkspaces;

	
	private boolean enabled;
	
	private boolean isAccountNonExpired=true;
	
	private boolean isAccountNonLocked=true;
	
	private boolean isCredentialsNonExpired=true;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authorities= new SimpleGrantedAuthority(this.systemRoleName.name());
		return List.of(authorities);
//		for(Role role:this.getRoles()) {
//			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
//		}
//		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	public User(String fullName, @Email String email, String password, String initialLetter,
			SystemRoleName systemRoleName) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.initialLetter = initialLetter;
		this.systemRoleName = systemRoleName;
	}

}
