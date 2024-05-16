package com.jungmin.model.board;

public class BoardDto {
  private long boardId;
  private String name;
  private String description;

  public BoardDto(long boardId, String name, String description) {
    this.boardId = boardId;
    this.name = name;
    this.description = description;
  }

  public long getBoardId() {
    return boardId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
