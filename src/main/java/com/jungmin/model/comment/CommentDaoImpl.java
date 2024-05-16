package com.jungmin.model.comment;

import com.jungmin.lib.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CommentDaoImpl implements CommentDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(CommentDto commentDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;
    try {

      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");
      String sql = "INSERT INTO comments(comment_id, content, user_id, post_id, created_at) values (?, ?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, commentDto.getCommentId());
      preparedStatement.setString(2, commentDto.getContent());
      preparedStatement.setLong(3, commentDto.getUserId());
      preparedStatement.setLong(4, commentDto.getPostId());
      preparedStatement.setDate(5, commentDto.getCreatedAt());

      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
