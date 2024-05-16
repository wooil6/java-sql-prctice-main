package com.jungmin.model.tag;

import com.jungmin.lib.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDaoImpl implements TagDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(TagDto tagDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;

    try {
      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");
      String sql = "INSERT INTO tags(tag_id, name) values (?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, tagDto.getTagId());
      preparedStatement.setString(2, tagDto.getName());
      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
