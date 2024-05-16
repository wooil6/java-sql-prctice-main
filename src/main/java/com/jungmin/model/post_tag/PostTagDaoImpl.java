package com.jungmin.model.post_tag;

import com.jungmin.lib.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostTagDaoImpl implements Post_TagDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(Post_TagDto postTagDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;

    try {
      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");
      String sql = "INSERT INTO post_tags(post_id, tag_id) values (?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, postTagDto.getPostId());
      preparedStatement.setLong(2, postTagDto.getTagId());

      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
