package com.jungmin.model.post;

import java.sql.Date;

public class PostDto {

  private long postId;
  private String title;
  private String content;
  private Long userId;
  private Long boardId;
  private Date createdAt;

  public PostDto(long postId, String title, String content, Long userId, Long boardId, Date createdAt) {
    this.postId = postId;
    this.title = title;
    this.content = content;
    this.userId = userId;
    this.boardId = boardId;
    this.createdAt = createdAt;
  }

  public long getPostId() {
    return postId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getBoardId() {
    return boardId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }
}
