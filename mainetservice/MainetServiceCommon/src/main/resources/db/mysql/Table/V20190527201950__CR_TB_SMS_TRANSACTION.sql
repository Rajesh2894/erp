--liquibase formatted sql
--changeset sumit:V20190527201950__CR_TB_SMS_TRANSACTION.sql
CREATE TABLE TB_SMS_TRANSACTION (
  sms_id 			bigint(12) 		NOT NULL  		COMMENT 'Primary key',
  sessionid 		bigint(12) 		DEFAULT NULL  	COMMENT '',
  sender_org		varchar(100) 	DEFAULT NULL  	COMMENT '',
  mobile_no 		varchar(12) 	DEFAULT NULL  	COMMENT '',
  msg_sub 			varchar(1024) 	DEFAULT NULL  	COMMENT '',
  msg_text 			varchar(1024) 	DEFAULT NULL  	COMMENT '',
  sent_dt 			date 			DEFAULT NULL  	COMMENT '',
  sent_by 			varchar(100) 	DEFAULT NULL  	COMMENT '',
  status 			varchar(50) 	NOT NULL  		COMMENT 'Success/Fail',
  lg_ip_mac 		varchar(100) 	DEFAULT NULL  	COMMENT '',
  lg_ip_mac_upd 	varchar(100) 	DEFAULT NULL  	COMMENT '',
  orgid 			bigint(7) 		NOT NULL  		COMMENT 'ORD_ID',
  user_id 			bigint(7) 		NOT NULL  		COMMENT 'USER_ID',
  lang_id 			bigint(7) 		NOT NULL  		COMMENT 'LANGUAGE',
  lmoddate 			date 			NOT NULL 		COMMENT 'MODIFID DATE',
  remarks 			varchar(1000) 	DEFAULT NULL  	COMMENT '',
  ref_id1 			varchar(1000) 	DEFAULT NULL  	COMMENT '',
  ref_id2 			varchar(1000) 	DEFAULT NULL  	COMMENT '',
  ref_id3 			varchar(1000) 	DEFAULT NULL  	COMMENT 'S->SMS, E->Email',
  ref_id4 			varchar(1000) 	DEFAULT NULL  	COMMENT 'N-> NEW, Y->FETCHED',
  emailsubject 		varchar(1000) 	DEFAULT NULL  	COMMENT '',
  emailbody 		varchar(2000) 	DEFAULT NULL  	COMMENT '',
  emailid 			varchar(200) 	DEFAULT NULL  	COMMENT '',
  mob_numbers 		varchar(200) 	DEFAULT NULL  	COMMENT '',
  service_id 		bigint(12) 		DEFAULT NULL  	COMMENT '',
  application_id 	bigint(12) 		DEFAULT NULL	COMMENT '',
  PRIMARY KEY (sms_id)
);

