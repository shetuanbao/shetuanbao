/*
MySQL Data Transfer
Source Host: localhost
Source Database: test
Target Host: localhost
Target Database: test
Date: 2016/9/16 9:40:53
*/

SET FOREIGN_KEY_CHECKS=1;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
CREATE TABLE Activities (
  activity_id int NOT NULL default '0',
  activity_title char(10) default NULL,
  activity_time char(255) NOT NULL default 'CURRENT_TIMESTAMP',
  activity_place char(50) default NULL,
  activity_introduce char(255) default NULL,
  activity_picture char(100) default NULL,
  community_id int default NULL,
  leixing int default NULL,
  PRIMARY KEY  (activity_id));

-- ----------------------------
-- Table structure for activity_picture
-- ----------------------------
CREATE TABLE activity_picture (
  activity_id int NOT NULL default '0',
  picture_id char(20) NOT NULL default '',
  PRIMARY KEY  (activity_id,picture_id)
);

-- ----------------------------
-- Table structure for activity_pinglun
-- ----------------------------

CREATE TABLE activity_pinglun (
  sid_activity char(5) NOT NULL default '',
  sid_user char(10) NOT NULL default '',
  sdetail char(200) default NULL,
  stime char(25) NOT NULL default '',
  sname char(20) default NULL,
  spicture char(20) default NULL,
  PRIMARY KEY  (sid_activity,sid_user,stime)
); 

-- ----------------------------
-- Table structure for activity_user
-- ------------------------------
CREATE TABLE activity_user (
  activity_id int NOT NULL default '0',
  user_id int NOT NULL default '0',
  PRIMARY KEY  (activity_id,user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------

CREATE TABLE administrator (
  community_id int NOT NULL default '0',
  user_id int NOT NULL default '0',
  password char(40) default NULL,
  PRIMARY KEY  (community_id)
);

-- ----------------------------
-- Table structure for community
-- ----------------------------

CREATE TABLE community (
  community_id int NOT NULL default '0',
  community_name char(10) default NULL,
  community_introduce char(200) default NULL,
  community_kouhao char(50) default NULL,
  community_tubiao char(15) default NULL,
  community_picture char(10) default NULL,
  community_stat char(2) default NULL,
  PRIMARY KEY  (community_id)
);

-- ----------------------------
-- Table structure for community_cishan
-- ----------------------------

CREATE TABLE community_cishan (
  community_id char(5) NOT NULL,
  community_name char(15) default NULL,
  PRIMARY KEY  (community_id)
);

-- ----------------------------
-- Table structure for community_compaign
-- ----------------------------

CREATE TABLE community_compaign (
  compaign_id int NOT NULL default '0',
  compaign_picture_id int default NULL,
  compaign_introduce char(200) default NULL,
  compaign_time timestamp NOT NULL default CURRENT_TIMESTAMP,
  community_id int default NULL,
  PRIMARY KEY  (compaign_id),
  KEY community_compaign_ibfk_1 (community_id),
  CONSTRAINT `community_compaign_ibfk_1` FOREIGN KEY (community_id) REFERENCES community (community_id)
);

-- ----------------------------
-- Table structure for community_tiyu
-- ----------------------------

CREATE TABLE `community_tiyu` (
  `community_id` char(5) NOT NULL,
  `community_name` char(15) default NULL,
  PRIMARY KEY  (`community_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 

-- ----------------------------
-- Table structure for community_users
-- ----------------------------

CREATE TABLE community_users (
 user_id int NOT NULL default '0',
  community_id int NOT NULL default '0',
  stat char(2) default NULL,
  PRIMARY KEY  (user_id,community_id),
  CONSTRAINT `community_users_ibfk_2` FOREIGN KEY (community_id) REFERENCES community(community_id)
);

-- ----------------------------
-- Table structure for community_wenyi
-- ----------------------------

CREATE TABLE community_wenyi(
  community_id char(5) NOT NULL,
  community_name char(15) default NULL,
  PRIMARY KEY  (community_id)
);

-- ----------------------------
-- Table structure for community_xiangce
-- ----------------------------

CREATE TABLE community_xiangce(
  community_id char(5) default NULL,
  community_name char(10) default NULL
);

-- ----------------------------
-- Table structure for community_xueshu
-- ----------------------------

CREATE TABLE community_xueshu (
  community_id char(5) NOT NULL,
  community_name char(15) default NULL,
  PRIMARY KEY  (community_id)
); 

-- ----------------------------
-- Table structure for users
-- ----------------------------

CREATE TABLE users (
  user_id int NOT NULL default '0',
  username char(20) default NULL,
  userpassword char(20) default NULL,
  useremail char(50) default NULL,
  userphone char(12) default NULL,
  sex char(4) default NULL,
  userpen char(100) default NULL,
  userphoto char(20) default NULL,
  static char(2) default NULL,
  major char(20) default NULL,
  xueyuan char(20) default NULL,
  PRIMARY KEY  (user_id)
); 

-- ----------------------------
-- Table structure for friends
-- ----------------------------

CREATE TABLE friends (
  user_id int NOT NULL default '0',
  friend_id int NOT NULL default '0',
  PRIMARY KEY  (user_id,friend_id),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- ----------------------------
-- Table structure for president
-- ----------------------------

CREATE TABLE president (
  id char(10) NOT NULL default '',
  password char(15) default NULL,
  PRIMARY KEY  (id)
);

-- ----------------------------
-- Table structure for yijian
-- ----------------------------

CREATE TABLE yijian (
  user_id char(10) default NULL,
  lianxi char(20) default NULL,
  detail char(100) default NULL,
  time  char(20) default NULL,
  username char(10) default NULL,
  userpicture char(10) default NULL
);
