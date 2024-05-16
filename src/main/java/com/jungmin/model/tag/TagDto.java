package com.jungmin.model.tag;

public class TagDto {
  private long tagId;
  private String name;

  public TagDto(long tagId, String name) {
    this.tagId = tagId;
    this.name = name;
  }

  public long getTagId() {
    return tagId;
  }

  public String getName() {
    return name;
  }
}
