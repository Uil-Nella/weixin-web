package org.cs.demo.manager.db.model.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cs.demo.manager.db.model.dao.UserDao;
import org.cs.demo.manager.db.model.pojo.User;
import org.cs.demo.manager.utils.JDBCUtils;



public class UserDaoImpl implements UserDao{
	private Connection conn = null;
	
	public UserDaoImpl(Connection conn){
		this.conn = conn;
	};
	@Override
	public List<User> loginCheck(User user) {
		List<User> list = new ArrayList<User>();
		String sql = "select * from user where username=? and password=?";
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			past = conn.prepareStatement(sql);
			past.setString(1, user.getUsername());
			past.setString(2, user.getPassword());
			rs = past.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(past);
		}
		return list;
	}

}
