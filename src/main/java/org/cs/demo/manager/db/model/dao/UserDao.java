package org.cs.demo.manager.db.model.dao;

import java.util.List;

import org.cs.demo.manager.db.model.pojo.User;

/**
 * 用户Dao
 * @author Administrator
 *
 */
public interface UserDao {
	//验证用户名和密码是否正确
	public List<User> loginCheck(User user);
	
}
