package org.cs.demo.manager.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具类
 * @author Administrator
 *
 */
public class JDBCUtils {
	private static Properties p = new Properties();
	private static JDBCUtils ju = new JDBCUtils();
	static {
		try {
			p.load(ju.getClass().getResourceAsStream("info.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static Connection getConnection() {
		String dbtype = p.getProperty("dbtype");
		String ip = p.getProperty("ip");
		String dbname = p.getProperty("dbname");
		String port = p.getProperty("port");
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		Connection conn = null;
		if (dbtype.equalsIgnoreCase("mysql")) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("驱动文件未导入工程！");
			}
			StringBuffer s = new StringBuffer();
			s.append("jdbc:mysql://");
			s.append(ip);
			s.append(":");
			s.append(port);
			s.append("/");
			s.append(dbname);
			s.append("?user=");
			s.append(username);
			s.append("&password=");
			s.append(password);
			s.append("&useUnicode=true&characterEncoding=utf8");
			try {
				conn = DriverManager.getConnection(s.toString());
			} catch (SQLException e) {
				System.out.println("数据库连接项错误！");
			}
		}
		if (dbtype.equalsIgnoreCase("oracle")) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("驱动文件未导入工程！");
			}
			StringBuffer s = new StringBuffer();
			s.append("jdbc:oracle:thin:@");
			s.append(ip);
			s.append(":");
			s.append(port);
			s.append(":");
			s.append(dbname);
			try {
				conn = DriverManager.getConnection(s.toString(), username,
						password);
			} catch (SQLException e) {
				System.out.println("数据库连接项错误！");
			}
		}
		return conn;
	}

	public static void close(Connection con, PreparedStatement pstam,
			ResultSet rs) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstam != null) {
			try {
				pstam.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstam) {

		if (pstam != null) {
			try {
				pstam.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
