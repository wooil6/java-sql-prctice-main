CREATE TABLE Users (
     user_id bigint auto_increment primary key not null,
     username varchar(255) not null unique,
     email varchar(255) not null unique,
     password varchar(255) not null,
     created_at datetime not null default CURRENT_TIMESTAMP
);

CREATE TABLE Boards (
     board_id bigint auto_increment primary key not null,
     name varchar(255) not null,
     description text
 );

 CREATE TABLE Posts (
     post_id bigint auto_increment primary key not null,
     title varchar(255) not null,
     content text not null,
     user_id bigint,
     board_id bigint,
     foreign key (user_id) references Users (user_id),
     foreign key (board_id) references Boards (board_id),
     created_at datetime not null default CURRENT_TIMESTAMP
 );

 CREATE TABLE Comments (
     comment_id bigint auto_increment primary key not null,
     content text not null,
     user_id bigint,
     post_id bigint,
     foreign key (user_id) references Users (user_id),
     foreign key (post_id) references Posts (post_id),
     created_at datetime not null default CURRENT_TIMESTAMP
 );

 CREATE TABLE Tags (
     tag_id bigint auto_increment primary key not null,
     name varchar(255) not null unique
 );

 CREATE TABLE Post_Tags (
     post_id bigint not null,
     tag_id bigint not null,
     foreign key (post_id) references Posts (post_id),
     foreign key (tag_id) references Tags (tag_id)
 );