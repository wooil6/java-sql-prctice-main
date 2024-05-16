package com.jungmin.model.post_tag;

public class Post_TagDto {
  private long postId;
  private long tagId;

  public Post_TagDto(long postId, long tagId) {
    this.postId = postId;
    this.tagId = tagId;
  }

  public long getPostId() {
    return postId;
  }

  public long getTagId() {
    return tagId;
  }
}
