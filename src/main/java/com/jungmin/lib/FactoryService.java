package com.jungmin.lib;

import com.jungmin.model.post.PostDao;
import com.jungmin.model.post.PostDaoImpl;
import com.jungmin.model.post.PostDto;
import com.jungmin.model.comment.CommentDao;
import com.jungmin.model.comment.CommentDaoImpl;
import com.jungmin.model.comment.CommentDto;
import com.jungmin.model.post_tag.Post_TagDao;
import com.jungmin.model.post_tag.PostTagDaoImpl;
import com.jungmin.model.post_tag.Post_TagDto;
import com.jungmin.model.board.BoardDao;
import com.jungmin.model.board.BoardDaoImpl;
import com.jungmin.model.board.BoardDto;
import com.jungmin.model.tag.TagDao;
import com.jungmin.model.tag.TagDaoImpl;
import com.jungmin.model.tag.TagDto;
import com.jungmin.model.users.UserDao;
import com.jungmin.model.users.UserDaoImpl;
import com.jungmin.model.users.UserDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FactoryService {

  private final Mysql mysql = Mysql.getInstance();
  private final UserDao userDao = new UserDaoImpl();
  private final CommentDao commentDao = new CommentDaoImpl();
  private final BoardDao boardDao = new BoardDaoImpl();
  private final Post_TagDao postTagDao = new PostTagDaoImpl();
  private final PostDao postDao = new PostDaoImpl();
  private final TagDao tagDao = new TagDaoImpl();
  private static FactoryService instance;

  private FactoryService(){}

  // Singleton
  public static FactoryService getInstance() {
    if (instance == null)
      instance = new FactoryService();
    return instance;
  }

  // initialize
  public void init(Connection connection) throws SQLException {
    mysql.query(connection, "DROP DATABASE IF EXISTS practiceSQL");
    mysql.query(connection, "CREATE DATABASE practiceSQL");
    mysql.query(connection, "USE practiceSQL");
  }

  // migration
  // 작성된 schema.sql 파일을 통해 테이블 생성
  public void migration(Connection connection) throws SQLException {

    File file = new File("src/main/java/com/jungmin/migrations/schema.sql");
    String absolutePath = file.getAbsolutePath(); //절대 경로 찾기

    StringBuilder contentBuilder = new StringBuilder();

    try (Stream<String> stream = Files.lines(Paths.get(absolutePath), StandardCharsets.UTF_8)) {
      stream.forEach(contentBuilder::append);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String[] querys = contentBuilder.toString().split(";");

    Statement statement = connection.createStatement();

    for(String str : querys) {
      statement.execute(str);
    }

    statement.close();
  }

  // 검증 메서드
  public ArrayList<HashMap<String,Object>> find(Connection connection, String table, String column) throws SQLException {
    return mysql.selectQuery(connection, String.format("SELECT %s.%s FROM %s", table, column, table));
  }

  // part-2 테스트를 위한 컬럼값 셋팅
  public void part2_setup() throws SQLException {
    List<UserDto> users = new ArrayList<UserDto>() {{
      add(new UserDto(1L, "luckykim", "luckykim@codestates.com", "password1", new Date(System.currentTimeMillis())));
      add(new UserDto(2L, "lattekim", "lattekim@codestates.com", "password2", new Date(System.currentTimeMillis())));
      add(new UserDto(3L, "nillava", "nillava@codestates.com", "password3", new Date(System.currentTimeMillis())));
      add(new UserDto(4L, "jungminlee", "jungminlee@codestates.com", "password4", new Date(System.currentTimeMillis())));
      add(new UserDto(5L, "johnsmith", "johnsmith@example.com", "password5", new Date(System.currentTimeMillis())));
      add(new UserDto(6L, "janedoe", "janedoe@example.com", "password6", new Date(System.currentTimeMillis())));
      add(new UserDto(7L, "alicewonder", "alicewonder@example.com", "password7", new Date(System.currentTimeMillis())));
      add(new UserDto(8L, "bobbuilder", "bobbuilder@example.com", "password8", new Date(System.currentTimeMillis())));
      add(new UserDto(9L, "charliebrown", "charliebrown@example.com", "password9", new Date(System.currentTimeMillis())));
      add(new UserDto(10L, "davidclark", "davidclark@example.com", "password10", new Date(System.currentTimeMillis())));
    }};

    for(UserDto userDto : users) {
      userDao.insert(userDto);
    }

    List<BoardDto> boards = new ArrayList<BoardDto>() {{
      add(new BoardDto(1L, "General Discussion", "A place for general chat"));
      add(new BoardDto(2L, "Tech Talk", "Discuss the latest in tech"));
      add(new BoardDto(3L, "Announcements", "Official announcements and updates"));
      add(new BoardDto(4L, "Support", "Get help and support here"));
      add(new BoardDto(5L, "Off Topic", "Talk about anything and everything"));
      add(new BoardDto(6L, "Gaming", "All things gaming"));
      add(new BoardDto(7L, "Movies", "Discuss the latest movies and TV shows"));
      add(new BoardDto(8L, "Music", "Talk about your favorite tunes"));
      add(new BoardDto(9L, "Books", "Share your favorite reads"));
      add(new BoardDto(10L, "Travel", "Share travel tips and experiences"));
    }};

    for(BoardDto boardDto : boards) {
      boardDao.insert(boardDto);
    }

    List<PostDto> posts = new ArrayList<PostDto>() {{
      add(new PostDto(1L, "Welcome to the forum!", "We're glad to have you here.", 1L, 1L, new Date(System.currentTimeMillis())));
      add(new PostDto(2L, "New Tech Trends", "What's new in tech this year?", 2L, 2L, new Date(System.currentTimeMillis())));
      add(new PostDto(3L, "Site Update", "We've made some updates to the site.", 3L, null, new Date(System.currentTimeMillis())));
      add(new PostDto(4L, "Need help with my PC", "My PC won't start. Help!", 4L, 4L, new Date(System.currentTimeMillis())));
      add(new PostDto(5L, "Random Chat", "Let's chat about anything!", 4L, 5L, new Date(System.currentTimeMillis())));
      add(new PostDto(6L, "Favorite Games", "What are your favorite games?", 6L, null, new Date(System.currentTimeMillis())));
      add(new PostDto(7L, "New Movie Releases", "What movies are you excited about?", 7L, 7L, new Date(System.currentTimeMillis())));
      add(new PostDto(8L, "Music Recommendations", "Share your favorite songs.", 8L, null, new Date(System.currentTimeMillis())));
      add(new PostDto(9L, "Book Club", "What books are you reading?", null, 9L, new Date(System.currentTimeMillis())));
      add(new PostDto(10L, "Travel Tips", "Share your best travel tips.", 7L, 10L, new Date(System.currentTimeMillis())));
    }};

    for(PostDto postDto : posts) {
      postDao.insert(postDto);
    }

    List<CommentDto> comments = new ArrayList<CommentDto>() {{
      add(new CommentDto(1L, "Great post!", 2L, 1L, new Date(System.currentTimeMillis())));
      add(new CommentDto(2L, "I totally agree.", 3L, 2L, new Date(System.currentTimeMillis())));
      add(new CommentDto(3L, "Thanks for the update.", 4L, 3L, new Date(System.currentTimeMillis())));
      add(new CommentDto(4L, "Have you tried restarting?", 5L, 4L, new Date(System.currentTimeMillis())));
      add(new CommentDto(5L, "This is fun!", 6L, 5L, new Date(System.currentTimeMillis())));
      add(new CommentDto(6L, "I love that game too.", 7L, 6L, new Date(System.currentTimeMillis())));
      add(new CommentDto(7L, "Can't wait to see it.", 8L, 7L, new Date(System.currentTimeMillis())));
      add(new CommentDto(8L, "Great recommendation.", 1L, 8L, new Date(System.currentTimeMillis())));
      add(new CommentDto(9L, "I'm reading that too.", 1L, 9L, new Date(System.currentTimeMillis())));
      add(new CommentDto(10L, "Thanks for the tips.", 1L, 10L, new Date(System.currentTimeMillis())));
    }};

    for(CommentDto commentDto : comments) {
      commentDao.insert(commentDto);
    }

    List<TagDto> tags = new ArrayList<TagDto>() {{
      add(new TagDto(1L, "Technology"));
      add(new TagDto(2L, "Gaming"));
      add(new TagDto(3L, "Movies"));
      add(new TagDto(4L, "Music"));
      add(new TagDto(5L, "Books"));
      add(new TagDto(6L, "Travel"));
      add(new TagDto(7L, "Food"));
      add(new TagDto(8L, "Fitness"));
      add(new TagDto(9L, "Education"));
      add(new TagDto(10L, "Lifestyle"));
    }};

    for(TagDto tagDto : tags) {
      tagDao.insert(tagDto);
    }

    List<Post_TagDto> postTags = new ArrayList<Post_TagDto>() {{
      add(new Post_TagDto(1L, 1L));
      add(new Post_TagDto(2L, 1L));
      add(new Post_TagDto(3L, 2L));
      add(new Post_TagDto(4L, 2L));
      add(new Post_TagDto(5L, 3L));
      add(new Post_TagDto(6L, 3L));
      add(new Post_TagDto(7L, 4L));
      add(new Post_TagDto(8L, 4L));
      add(new Post_TagDto(9L, 5L));
      add(new Post_TagDto(10L, 5L));
    }};

    for(Post_TagDto postTagDto: postTags) {
      postTagDao.insert(postTagDto);
    }
  }
}
