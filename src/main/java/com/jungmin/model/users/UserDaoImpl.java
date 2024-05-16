package com.jungmin.model.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jungmin.lib.Mysql;

public class UserDaoImpl implements UserDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(UserDto userDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;

    try {
      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");

        String sql = "INSERT INTO users(user_id, username, email, password, created_at) values (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, userDto.getUserId());
        preparedStatement.setString(2, userDto.getUsername());
        preparedStatement.setString(3, userDto.getEmail());
        preparedStatement.setString(4, userDto.getPassword());
        preparedStatement.setDate(5, userDto.getCreatedAt());
      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
