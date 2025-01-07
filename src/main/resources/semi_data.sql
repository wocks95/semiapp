DROP DATABASE IF EXISTS db_semi;
CREATE DATABASE IF NOT EXISTS db_semi;
USE db_semi;

DROP TABLE IF EXISTS tbl_attach;
DROP TABLE IF EXISTS tbl_notice;
DROP TABLE IF EXISTS tbl_comment;
DROP TABLE IF EXISTS tbl_blog;
DROP TABLE IF EXISTS tbl_bbs;
DROP TABLE IF EXISTS tbl_withdrawal;
DROP TABLE IF EXISTS tbl_login;
DROP TABLE IF EXISTS tbl_user;

CREATE TABLE IF NOT EXISTS tbl_user 
(
	user_id	    INT	         AUTO_INCREMENT  COMMENT '유저 아이디',
	user_pw	    VARCHAR(64)                  COMMENT '비밀번호',
	user_email  VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
	user_name   VARCHAR(100)                 COMMENT '이름',
	profile_img VARCHAR(100)                 COMMENT '프로필 이미지',
	session_id	varchar(100)                 COMMENT '자동로그인 정보',
	is_admin	BOOLEAN                      COMMENT '관리자 여부',
    change_dt   DATETIME                     COMMENT '비밀번호 변경일시',
    create_dt   DATETIME                     COMMENT '생성일시',
    CONSTRAINT pk_user PRIMARY KEY (user_id)
) ENGINE='InnoDB' COMMENT '유저';

CREATE TABLE IF NOT EXISTS tbl_login 
(
	user_id	   INT          NOT NULL COMMENT '유저 아이디',
	acc_dt	   DATETIME              COMMENT '접속 일시',
	acc_ip     VARCHAR(20)           COMMENT '접속 ip',
	user_agent VARCHAR(200)          COMMENT '접속 에이전트',
    CONSTRAINT fk_user_login FOREIGN KEY (user_id)
        REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE='InnoDB' COMMENT '유저 로그인기록';

CREATE TABLE IF NOT EXISTS tbl_withdrawal 
(
	user_email VARCHAR(100)	COMMENT '유저 이메일',
	user_name  VARCHAR(100) COMMENT '유저 이름',
	del_dt     DATETIME     COMMENT '삭제 일'
) ENGINE='InnoDB' COMMENT '탈퇴 유저';

CREATE TABLE tbl_notice 
(
	notice_id INT	       AUTO_INCREMENT COMMENT '공지사항 아이디',
	user_id	  INT	       NOT NULL       COMMENT '유저 아이디',
	title	  VARCHAR(100) NOT NULL       COMMENT '제목',
	contents  TEXT	                      COMMENT '내용',
	modify_dt DATETIME	                  COMMENT '수정일시',
	create_dt DATETIME                    COMMENT '작성일시',
    CONSTRAINT pk_notice PRIMARY KEY (notice_id),
    CONSTRAINT fk_user_notice FOREIGN KEY (user_id)
        REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE='InnoDB' COMMENT '공지사항';

CREATE TABLE IF NOT EXISTS tbl_attach
(
    attach_id         INT          AUTO_INCREMENT COMMENT '첨부파일 아이디',
    notice_id         INT                         COMMENT '공지사항 아이디',
    file_path         VARCHAR(300)                COMMENT '경로',
    original_filename VARCHAR(300)                COMMENT '본래이름',
    filesystem_name   VARCHAR(40)                 COMMENT '저장이름',
    download_count    INT                         COMMENT '다운로드 횟수',
    CONSTRAINT pk_attach PRIMARY KEY (attach_id),
    CONSTRAINT fk_notice_attach FOREIGN KEY (notice_id)
        REFERENCES tbl_notice (notice_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='첨부파일';

CREATE TABLE IF NOT EXISTS tbl_blog
(
    blog_id   INT          AUTO_INCREMENT COMMENT '블로그 아이디',
    user_id	  INT	       NOT NULL       COMMENT '유저 아이디',
    title     VARCHAR(100) NOT NULL       COMMENT '제목',
    contents  TEXT                        COMMENT '내용',
    hit       INT                         COMMENT '조회수',        
    modify_dt DATETIME                    COMMENT '수정일시',
    create_dt DATETIME                    COMMENT '작성일시',
    CONSTRAINT pk_blog PRIMARY KEY (blog_id),
    CONSTRAINT fk_user_blog FOREIGN KEY (user_id)
        REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT '블로그';

CREATE TABLE tbl_comment 
(
	comment_id INT	    AUTO_INCREMENT COMMENT '댓글 아이디',
	user_id    INT		               COMMENT '유저 아이디',
	blog_id	   INT	    NOT NULL       COMMENT '블로그 아이디',
	contents   TEXT	                   COMMENT '내용',	
	modify_dt  DATETIME	               COMMENT '수정일시',
	create_dt  DATETIME	               COMMENT '작성일시',
	state    INT                       COMMENT '원글 상태',
	depth    INT                       COMMENT '원글 depth',
	group_id INT                       COMMENT '그룹 아이디',
	group_order INT                    COMMENT '그룹 정렬',
	CONSTRAINT pk_comment PRIMARY KEY (comment_id),
	CONSTRAINT fk_user_comment FOREIGN KEY (user_id)
			REFERENCES tbl_user (user_id),
	CONSTRAINT fk_blog_comment FOREIGN KEY (blog_id)
			REFERENCES tbl_blog (blog_id)
) ENGINE=InnoDB COMMENT '블로그 댓글';

CREATE TABLE IF NOT EXISTS tbl_bbs
(
    bbs_id      INT      AUTO_INCREMENT COMMENT '게시판 아이디',
	user_id	    INT	                    COMMENT '유저 아이디',
    contents    TEXT     NOT NULL       COMMENT '내용',
    state       TINYINT                 COMMENT '삭제여부(0:정상, 1:삭제)',
    depth       TINYINT                 COMMENT '댓글깊이(0:원글, 1:댓글, 2:대댓글, ...)',
    group_id    INT                     COMMENT '그룹화(원글과 해당 원글에 달린 댓글은 같은 번호를 가짐)',
    group_order SMALLINT                COMMENT '같은 그룹 내에서 정렬하기 위한 값',    
	modify_dt	DATETIME	            COMMENT '수정일시',
    created_dt	DATETIME	            COMMENT '작성일시',
    CONSTRAINT pk_bbs PRIMARY KEY (bbs_id),
    CONSTRAINT fk_user_bbs FOREIGN KEY (user_id)
        REFERENCES tbl_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB COMMENT='계층형게시판';

DELIMITER $$

CREATE TRIGGER after_user_delete
AFTER DELETE ON tbl_user
FOR EACH ROW
BEGIN
    INSERT INTO tbl_withdrawal (user_name, user_email, del_dt)
    VALUES (OLD.user_name, OLD.user_email, NOW());
END$$

DELIMITER ;


INSERT INTO tbl_user
VALUES (null, SHA2('admin', 256), 'admin@naver.com', '관리자', null, null, 1, now(), now());
INSERT INTO tbl_user
VALUES (null, SHA2('chan', 256), 'chan@naver.com', '관리자', null, null, 1, now(), now());
INSERT INTO tbl_user
VALUES (null, SHA2('soo', 256), 'soo@naver.com', '관리자', null, null, 1, now(), now());

SELECT * FROM tbl_user;















