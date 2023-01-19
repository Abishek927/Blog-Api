package com.api.blog.Model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

@Entity
@Table(name="user_table")
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_about")
	private String about;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = "user")
	@JsonManagedReference(value = "post_table")
	private List<Post> post;
	
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        @JoinTable(
            name = "role_user",
            joinColumns = {
                @JoinColumn(name = "user",referencedColumnName = "user_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "role",referencedColumnName = "role_id")
            }
        )
	
	private List<Role> role;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> collect = this.role.stream().map((eachRole)->new SimpleGrantedAuthority(eachRole.getRoleName())).collect(Collectors.toList());
		return collect;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userEmail;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
