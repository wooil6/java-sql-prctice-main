package com.jungmin.model.post;

import java.sql.SQLException;

public interface PostDao {
  int insert(PostDto postDto) throws SQLException;
}
