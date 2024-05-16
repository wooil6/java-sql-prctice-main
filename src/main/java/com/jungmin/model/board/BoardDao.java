package com.jungmin.model.board;

import java.sql.SQLException;

public interface BoardDao {
  int insert(BoardDto boardDto) throws SQLException;
}
