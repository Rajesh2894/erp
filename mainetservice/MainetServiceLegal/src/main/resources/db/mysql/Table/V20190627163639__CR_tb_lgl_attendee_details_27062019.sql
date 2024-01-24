--liquibase formatted sql
--changeset Anil:V20190627163639__CR_tb_lgl_attendee_details_27062019.sql
drop table if exists tb_lgl_attendee_details;
--liquibase formatted sql
--changeset Anil:V20190627163639__CR_tb_lgl_attendee_details_270620191.sql
CREATE TABLE tb_lgl_attendee_details (
  ATTENDEE_ID bigint(12) NOT NULL,
  ATTENDEE_NAME varchar(50) NOT NULL,
  ATTENDEE_ADDRESS varchar(300) NOT NULL,
  CJD_ID bigint(12) NOT NULL,
  CREATED_BY bigint(20) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  ORGID bigint(12) NOT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machines Login Name | IP Address | Physical Address',
  UPDATED_BY bigint(20) DEFAULT NULL COMMENT 'Last Updated By',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Last Updated Date',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'last Updated Client Machines Login Name | IP Address | Physical Address',
  PRIMARY KEY (ATTENDEE_ID),
  KEY FK_LGL_CJD_ID (CJD_ID),
  CONSTRAINT FK_LGL_CJD_ID FOREIGN KEY (CJD_ID) REFERENCES tb_lgl_casejudgement_detail (cjd_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

