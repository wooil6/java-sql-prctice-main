package com.jungmin.model.tag;

import java.sql.SQLException;

public interface TagDao {
  int insert(TagDto tagDto) throws SQLException;
}
