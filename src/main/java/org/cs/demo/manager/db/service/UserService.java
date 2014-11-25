package org.cs.demo.manager.db.service;

import org.cs.demo.manager.db.model.pojo.User;

public interface UserService {
	//验证用户名和密码是否正确
		public boolean loginCheck(User user);
}
