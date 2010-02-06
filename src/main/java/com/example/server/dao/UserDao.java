/**
 * 
 */
package com.example.server.dao;

import com.example.client.model.User;

/**
 * DAO for {@link User} objects
 */
public interface UserDao {
	
	User findByUserName(String username);

}
