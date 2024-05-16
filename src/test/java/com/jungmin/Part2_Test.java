package com.jungmin;

import com.jungmin.lib.FactoryService;
import com.jungmin.lib.Mysql;
import com.jungmin.script.Part2;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class Part2_Test {
  private static final Mysql mysql = Mysql.getInstance();
  private static final FactoryService factoryService = FactoryService.getInstance();
  private static Connection connection = null;

  @BeforeEach
  public void init() throws SQLException {
    connection = mysql.getConnection();
    factoryService.init(connection);
    factoryService.migration(connection);
    factoryService.part2_setup();
  }

  @AfterAll
  public static void terminate() throws SQLException {
    mysql.terminate(connection);
  }

  @Test
  @DisplayName("Q 2-01. users 테이블에 존재하는 모든 컬럼을 포함한 모든 데이터를 확인하기 위한 SQL을 작성해주세요.")
  public void Query_Test_2_1() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection, Part2.PART2_1);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "users", "*");

    assertThat(response.size()).isEqualTo(10);
    assertThat(response).usingRecursiveComparison().isEqualTo(factoryResponse);
  }
  @Test
  @DisplayName("Q 2-02. users 테이블에 존재하는 모든 데이터에서 username 컬럼만을 확인하기 위한 SQL을 작성해주세요.")
  public void Query_Test_2_2() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection, Part2.PART2_2);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "users", "username");

    assertThat(response.size()).isEqualTo(10);
    assertThat(response).usingRecursiveComparison().isEqualTo(factoryResponse);
  }

  @Test
  @DisplayName("Q 2-03. users 테이블에 데이터를 추가하기 위한 SQL을 작성해주세요.")
  public void Query_Test_2_3() throws SQLException {
    mysql.query(connection, Part2.PART2_3);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "users", "*");

    assertThat(factoryResponse.size()).isEqualTo(11);
  }

  @Test
  @DisplayName("Q 2-04. users 테이블에서 특정 조건을 가진 데이터를 찾기위한 SQL을 작성해주세요.(username = 'luckykim'")
  public void Query_Test_2_4() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection, Part2.PART2_4);

    System.out.println(response);
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).get("username")).isEqualTo("luckykim");
  }

  @Test
  @DisplayName("Q 2-05. users 테이블에서 특정 조건을 가진 데이터를 찾기위한 SQL을 작성해주세요. \n username이 'luckykim이 아니여야 합니다.")
  public void Query_Test_2_5() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection, Part2.PART2_5);

    assertThat(response.size()).isEqualTo(9);
    for (HashMap<String, Object> data : response) {
      assertThat(data.get("username")).isNotEqualTo("luckykim");
    }
  }

  @Test
  @DisplayName("Q 2-06. posts 테이블에 존재하는 모든 데이터에서 title 컬럼만을 찾기 위한 SQL을 작성해주세요.")
  public void Query_Test_2_6() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection, Part2.PART2_6);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "posts", "title");

    assertThat(factoryResponse.size()).isEqualTo(10);
    assertThat(response).usingRecursiveComparison().isEqualTo(factoryResponse);
  }

  @Test
  @DisplayName("Q 2-07. posts의 title과 그 게시글을 작성한 user의 username을 찾기 위한 SQL을 작성해주세요. - 저자가 없더라도, 게시글의 title을 모두 찾아야합니다.")
  public void Query_Test_2_7() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_7);

    assertThat(response.size()).isEqualTo(10);
    int count = 0;
    for (HashMap<String, Object> data : response) {
      if (data.get("username") == null) continue;
      if (data.get("username").equals("luckykim")) count++;
    }
    assertThat(count).isEqualTo(1);
  }

  @Test
  @DisplayName("Q 2-08. posts의 title과 그 게시글을 작성한 user의 username을 찾기 위한 SQL을 작성해주세요. - 저자가 있는 게시글의 title만 찾아야합니다.")
  public void Query_Test_2_8() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_8);

    assertThat(response.size()).isEqualTo(9);
  }

  @Test
  @DisplayName("Q 2-09. posts 테이블의 데이터를 수정하기 위한 SQL을 작성해주세요. - title이 'New Tech Trends'인 posts 데이터에서 content를 'Updated content'로 수정해야합니다.")
  public void Query_Test_2_9() throws SQLException {
    mysql.query(connection, Part2.PART2_9);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "posts", "*");

    for (HashMap<String, Object> data : factoryResponse) {
      if (data.get("title").equals("New Tech Trends")) {
        assertThat(data.get("content")).isEqualTo("Updated content");
      }
    }
  }

  @Test
  @DisplayName("Q 2-10. posts 테이블의 데이터를 추가하기 위한 SQL을 작성해주세요. - luckykim이 작성한 게시글을 추가해주세요. 제목과 본문은 자유입니다. (참고: luckykim의 아이디는 1입니다.)")
  public void Query_Test_2_10() throws SQLException {
    mysql.query(connection, Part2.PART2_10);
    ArrayList<HashMap<String,Object>> factoryResponse = factoryService.find(connection, "posts", "*");

    assertThat(factoryResponse.size()).isEqualTo(11);
    int count = 0;
    for (HashMap<String, Object> data : factoryResponse) {
      if (data.get("user_id") == null) continue;
      long userId = (long)data.get("user_id");
      if (userId == 1) count++;
    }
    assertThat(count).isEqualTo(2);
  }

  @Test
  @DisplayName("Q 2-11. 어느 board에도 속하지 않는 post의 모든 데이터를 찾기위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_3() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_11);

    assertThat(response.size()).isEqualTo(3);
  }

  @Test
  @DisplayName("Q 2-12. users 테이블의 user_id가 1인 유저가 작성한 post의 title을 찾기위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_5() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_12);

    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(1);
      assertThat(map.get("title")).isEqualTo("Welcome to the forum!");
    }
  }

  @Test
  @DisplayName("Q 2-13. users 테이블의 user_id가 1인 유저가 작성한 post의 board name을 찾기위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_6() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_13);
    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(1);
      assertThat(map.get("name")).isEqualTo("General Discussion");
    }
  }

  @Test
  @DisplayName("Q 2-14. board의 name이 'General Discussion'인 post의 title, content, created_at을 찾기위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_7() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_14);

    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(3);
      assertThat(map.get("title")).isEqualTo("Welcome to the forum!");
      assertThat(map.get("content")).isEqualTo("We're glad to have you here.");
    }
  }

  @Test
  @DisplayName("Q 2-15. luckykim이 작성한 comment의 개수 (컬럼명: CommentCount)를 출력하기 위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_9() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_15);

    System.out.println(response);
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).get("CommentCount")).isEqualTo(3L);
  }

  @Test
  @DisplayName("Q 2-16. 각 user(컬럼명: username)가 작성한 comment의 개수 (컬럼명: CommentCount)를 출력하기 위한 SQL을 작성해주세요. \n 0개의 comment를 작성한 유저도 모두 출력해야 합니다.")
  public void Query_Test_3_2_10() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_16);

    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(2);
    }
  }

  @Test
  @DisplayName("Q 2-17. 각 board의 name과 해당 board에 속한 post의 개수 (컬럼명: PostCount)를 출력하기 위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_11() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_17);

    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(2);
    }
  }

  @Test
  @DisplayName("Q 2-18. 각 board의 name과 해당 board에 속한 posts 테이블의 content의 평균 길이 (컬럼명: AvgLength)를 출력하기 위한 SQL을 작성해주세요.")
  public void Query_Test_3_2_13() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_18);

    assertThat(response.size()).isEqualTo(10);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(2);
    }
  }

  @Test
  @DisplayName("Q 2-19. 각 board의 name과 해당 board에 속한 posts 테이블의 content의 평균 길이 (컬럼명: AvgLength)를 출력하기 위한 SQL을 작성해주세요.\n 단, content가 null인 경우를 제외해야 합니다.")
  public void Query_Test_3_2_14() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_19);

    assertThat(response.size()).isEqualTo(7);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(2);
    }

    assertThat(response.get(6).get("name")).isEqualTo("Travel");
    String avg = response.get(6).get("AvgLength").toString();
    assertThat(avg).isEqualTo("28.0000");
  }

  @Test
  @DisplayName("Q 2-20. 각 tag의 name과 해당 tag가 달린 post의 개수 (컬럼명: PostCount)를 출력하기 위한 SQL을 작성해주세요. \n 단, post에 할당되지 않은 tag의 이름도 모두 출력해야 합니다.")
  public void Query_Test_3_2_16() throws SQLException {
    ArrayList<HashMap<String,Object>> response = mysql.selectQuery(connection,
            Part2.PART2_20);

    assertThat(response.size()).isGreaterThan(0);
    for(HashMap<String, Object> map: response) {
      assertThat(map.size()).isEqualTo(2);
    }

    assertThat(response.get(0).get("name")).isEqualTo("Books");
    assertThat(response.get(2).get("name")).isEqualTo("Fitness");
    assertThat(response.get(3).get("PostCount")).isEqualTo(0L);
    assertThat(response.get(7).get("name")).isEqualTo("Music");
    assertThat(response.get(7).get("PostCount")).isEqualTo(2L);

  }
}
