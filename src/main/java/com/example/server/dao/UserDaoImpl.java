/**
 * 
 */
package com.example.server.dao;

import java.util.HashMap;
import java.util.Map;

import com.example.client.model.User;
import com.example.server.security.BCrypt;

/**
 * Mock implementation of {@link UserDao}.  You would re-write this to fetch from DB.
 */
public class UserDaoImpl implements UserDao {
	
	private final Map<String, User> map = new HashMap<String, User>();
	
	/**
	 * Constructor
	 */
	public UserDaoImpl() {
		// initialize mock repository
		map.put("davis", new User("davis", BCrypt.hashpw("davis", BCrypt.gensalt())));
		map.put("homer", new User("homer", BCrypt.hashpw("homer", BCrypt.gensalt())));
	}

	/* (non-Javadoc)
	 * @see com.example.server.dao.UserDao#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String username) {
		return map.get(username);
	}

}
