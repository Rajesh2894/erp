--liquibase formatted sql
--changeset priya:V20180201183845__AL_tb_portal_sms_mail_temp_hist_01022018_portal.sql
ALTER TABLE tb_portal_sms_mail_temp_hist 
DROP COLUMN LANG_ID,
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT 'User Id' ,
CHANGE COLUMN LMODDATE CREATED_DATE DATETIME NULL DEFAULT NULL COMMENT 'Last Modification Date' ;
