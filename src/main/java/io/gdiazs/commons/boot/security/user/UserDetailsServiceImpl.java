package io.gdiazs.commons.boot.security.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gdiazs.commons.boot.security.domain.User;
import io.gdiazs.commons.boot.security.domain.UsersAuthority;
import io.gdiazs.commons.boot.security.authority.AuthorityDTO;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

@Qualifier("userRepository")
  private UserRepository userRepository;

 
  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
    return (UserDetails) mapToUserDto(userRepository.findUserByUserName(username));
  }
  
  
  private UserDTO mapToUserDto(User user){
	  UserDTO userDto = new UserDTO();
	  userDto.setAccountNonExpired(user.getUserAccountNonExpired().intValue() == 1 ? true: false);
	  userDto.setAccountNonLocked(user.getUserAccountNonLocked().intValue() == 1 ? true: false);
	  List<AuthorityDTO> authorities = new ArrayList<>();
	  List<UsersAuthority> userAuthorities =  user.getUsersAuthorities();
	  
	  for (UsersAuthority usersAuthority : userAuthorities) {
		 AuthorityDTO authority = new AuthorityDTO();
		 authority.setAuthority(usersAuthority.getAuthority().getAuthorityName());
		 authorities.add(authority);
	  }
	  
	  userDto.setAuthorities(authorities);
	  
	  userDto.setCredentialsNonExpired(user.getUserCredentialsNonExpired().intValue() == 1 ? true: false);
	  userDto.setEnabled(user.getUserEnabled().intValue() == 1 ? true: false);
	  userDto.setPassword(user.getUserPassword());
	  userDto.setUserName(user.getUserName());

	  Calendar start = Calendar.getInstance();
	  start.setTimeInMillis( user.getUserLastPasswordResetDate().getTime() );

	  userDto.setLastPasswordResetDate(start.getTime());
	  
	  
	
	  return userDto;
  }


}
