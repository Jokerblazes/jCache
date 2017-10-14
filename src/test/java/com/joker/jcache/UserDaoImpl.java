package com.joker.jcache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.joker.support.connection.DataSourceFactory;
import com.joker.support.connection.TransactionUtil;

public class UserDaoImpl implements UserDao {
	@Override
	public int insert(User user) {
		Connection conn = TransactionUtil.getConnection();
		String sql = "INSERT INTO `user` (`password`) VALUES ('123456')";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int update(User user) {
		Connection conn = DataSourceFactory.getInstance().getConnection();
		String sql = "UPDATE `user` SET `password`='98765' WHERE userId='"+user.getUserid()+"'";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int delete(int userId) {
		Connection conn = DataSourceFactory.getInstance().getConnection();
		String sql = "DELETE FROM `user` WHERE userId='"+userId+"'";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public List<User> selectUserByPassword(String password) {
		List<User> users = new ArrayList<User>();
		Connection conn = DataSourceFactory.getInstance().getConnection();
		String sql = "SELECT * FROM `user` WHERE `password`='"+password +"'";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet set = pst.executeQuery();
			while (set.next()) {
				User user = new User();
				user.setUserid(set.getInt("userId"));
				user.setPassword(set.getString("password"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	@Override
	public List<User> selectAllUser() {
		Connection conn = DataSourceFactory.getInstance().getConnection();
		List<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM `user`";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet set = pst.executeQuery();
			while (set.next()) {
				User user = new User();
				user.setUserid(set.getInt("userId"));
				user.setPassword(set.getString("password"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	@Override
	public List<User> selectUserByUser(User user) {
		List<User> users = new ArrayList<User>();
		Connection conn = DataSourceFactory.getInstance().getConnection();
		String sql = "SELECT * FROM `user` WHERE `password`='"+user.getPassword() +"'";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet set = pst.executeQuery();
			while (set.next()) {
				User user1 = new User();
				user1.setUserid(set.getInt("userId"));
				user1.setPassword(set.getString("password"));
				users.add(user1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

}
