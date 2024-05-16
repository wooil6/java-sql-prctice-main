package com.jungmin.model.comment;

import java.sql.Date;
import java.sql.Timestamp;

public class CommentDto {
  private long commentId;
  private String content;
  private long userId;
  private long postId;
  private Date createdAt;

  public CommentDto(long commentId, String content, long userId, long postId, Date createdAt) {
    this.commentId = commentId;
    this.content = content;
    this.userId = userId;
    this.postId = postId;
    this.createdAt = createdAt;
  }

  public long getCommentId() {
    return commentId;
  }

  public String getContent() {
    return content;
  }

  public long getUserId() {
    return userId;
  }

  public long getPostId() {
    return postId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }
}
