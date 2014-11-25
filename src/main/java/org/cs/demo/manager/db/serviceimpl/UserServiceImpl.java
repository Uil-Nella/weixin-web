package org.cs.demo.manager.db.serviceimpl;

import java.sql.Connection;
import java.util.List;

import org.cs.demo.manager.db.model.dao.UserDao;
import org.cs.demo.manager.db.model.daoimpl.UserDaoImpl;
import org.cs.demo.manager.db.model.pojo.User;
import org.cs.demo.manager.db.service.UserService;
import org.cs.demo.manager.utils.JDBCUtils;


public class UserServiceImpl implements UserService{

	@Override
	public boolean loginCheck(User user) {
		Connection conn = JDBCUtils.getConnection();
		UserDao dao = new UserDaoImpl(conn);
		List<User> list = dao.loginCheck(user);
		boolean flag = false;
		if(list==null ||list.size() == 0){
			flag = false;
		}else{
			flag = true;
		}
		JDBCUtils.close(conn);
		return flag;
	}

}
