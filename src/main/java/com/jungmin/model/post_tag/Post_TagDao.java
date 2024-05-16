package com.jungmin.model.post_tag;

import java.sql.SQLException;

public interface Post_TagDao {
  int insert(Post_TagDto postTagDto) throws SQLException;
}
