package com.jungmin.model.board;

import com.jungmin.lib.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDaoImpl implements BoardDao {

  private final Mysql mysql = Mysql.getInstance();

  // 데이터 삽입을 위한 메서드
  @Override
  public int insert(BoardDto boardDto) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    int response = 0;

    try {
      connection = mysql.getConnection();
      mysql.query(connection, "USE practiceSQL");
      String sql = "INSERT INTO boards(board_id, name, description) values (?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, boardDto.getBoardId());
      preparedStatement.setString(2, boardDto.getName());
      preparedStatement.setString(3, boardDto.getDescription());

      response = preparedStatement.executeUpdate();
      preparedStatement.close();
    } finally {
      mysql.terminate(connection);
    }
    return response;
  }
}
