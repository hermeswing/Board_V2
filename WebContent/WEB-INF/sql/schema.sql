-- phpMyAdmin SQL Dump
-- version 3.3.10deb1
-- http://www.phpmyadmin.net
--
-- 서버 버전: 5.1.54
-- PHP 버전: 5.3.5-1ubuntu7.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: 'antop'
--

-- --------------------------------------------------------

--
-- 테이블 구조 'ab_board'
--

CREATE TABLE ab_board (
  board_id int(11) NOT NULL AUTO_INCREMENT COMMENT '고유번호',
  thread double NOT NULL COMMENT '정렬필드',
  dept int(11) NOT NULL COMMENT '응답글 깊이',
  `subject` varchar(255) NOT NULL COMMENT '제목',
  `security` bit(1) DEFAULT b'0' COMMENT '비밀글 여부(y/n)',
  content longtext COMMENT '내용',
  author varchar(255) NOT NULL COMMENT '작성자',
  pwd char(41) NOT NULL COMMENT '비밀번호',
  write_date datetime NOT NULL COMMENT '작성일',
  modify_date datetime DEFAULT NULL COMMENT '수정일',
  hit int(11) DEFAULT '0' COMMENT '조회수',
  PRIMARY KEY (board_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 'ab_board_file'
--

CREATE TABLE ab_board_file (
  seq int(11) NOT NULL AUTO_INCREMENT,
  path varchar(50) NOT NULL,
  file_name varchar(255) NOT NULL,
  real_name varchar(255) NOT NULL,
  context_type varchar(50) NOT NULL,
  ext varchar(10) DEFAULT NULL,
  file_size double NOT NULL,
  add_date datetime NOT NULL,
  board_id int(11) DEFAULT NULL,
  session_id varchar(100) DEFAULT NULL,
  del_flag bit(1) NOT NULL,
  PRIMARY KEY (seq),
  KEY board_id (board_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ab_board_file`
--
ALTER TABLE `ab_board_file`
  ADD CONSTRAINT ab_board_file_ibfk_1 FOREIGN KEY (board_id) REFERENCES ab_board (board_id) ON DELETE SET NULL ON UPDATE CASCADE;
