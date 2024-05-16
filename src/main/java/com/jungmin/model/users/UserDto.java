package com.jungmin.model.users;

import java.sql.Date;

public class UserDto {
  private long userId;
  private String username;
  private String email;
  private String password;
  private Date createdAt;

  public UserDto(long userId, String username, String email, String password, Date createdAt) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.password = password;
    this.createdAt = createdAt;
  }

  public long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Date getCreatedAt() {
    return createdAt;
  }
}
