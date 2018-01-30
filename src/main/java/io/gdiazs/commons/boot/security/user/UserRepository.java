package io.gdiazs.commons.boot.security.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.gdiazs.commons.boot.security.domain.User;


@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long>{

	public User findUserByUserName(String userName);
}
