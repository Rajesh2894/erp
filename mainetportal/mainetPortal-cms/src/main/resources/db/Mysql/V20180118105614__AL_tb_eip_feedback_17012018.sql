--liquibase formatted sql
--changeset priya:V20180118105614__AL_tb_eip_feedback_17012018.sql
ALTER TABLE tb_eip_feedback
DROP COLUMN LANG_ID,
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NOT NULL COMMENT 'User Id' ,
CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT ' user id who updated the record' ,
ADD COLUMN FD_PARENT_ID BIGINT(12) NULL COMMENT 'Parent Id of previous Transaction' AFTER LG_IP_MAC_UPD,
ADD COLUMN FD_FLAG CHAR(1) NULL DEFAULT 'N' COMMENT '\'Y\'-> Completion \'N\' -> non completion' AFTER FD_PARENT_ID;


--liquibase formatted sql
--changeset priya:V20180118105614__AL_tb_eip_feedback_170120181.sql
ALTER TABLE tb_eip_feedback 
CHANGE COLUMN FD_DETAILS FD_QUESTIONS VARCHAR(4000) NULL COMMENT 'Feedback Details' ,
CHANGE COLUMN FD_PARENT_ID FD_ANSWERS VARCHAR(4000) NULL DEFAULT NULL COMMENT 'Parent Id of previous Transaction' ;