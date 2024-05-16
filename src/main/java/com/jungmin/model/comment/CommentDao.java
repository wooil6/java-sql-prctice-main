package com.jungmin.model.comment;

import java.sql.SQLException;

public interface CommentDao {
  int insert(CommentDto commentDto) throws SQLException;
}
