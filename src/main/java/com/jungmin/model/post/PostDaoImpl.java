package com.jungmin.model.post;

import com.jungmin.lib.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostDaoImpl implements PostDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(PostDto postDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;

    try {
      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");
      String sql = "INSERT INTO posts(post_id, title, content, user_id, board_id, created_at) values (?, ?, ?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, postDto.getPostId());
      preparedStatement.setString(2, postDto.getTitle());
      preparedStatement.setString(3, postDto.getContent());

      preparedStatement.setDate(6, postDto.getCreatedAt());
      if(postDto.getUserId() == null) {
        preparedStatement.setNull(4, Types.BIGINT);
      } else {
        preparedStatement.setLong(4, postDto.getUserId());
      }

      if(postDto.getBoardId() == null) {
        preparedStatement.setNull(5, Types.BIGINT);
      } else {
        preparedStatement.setLong(5, postDto.getBoardId());
      }

      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
