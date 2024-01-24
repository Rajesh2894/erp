--liquibase formatted sql
--changeset priya:V20180125105413__al_tb_portal_sms_mail_template
ALTER TABLE tb_portal_sms_mail_template 
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NOT NULL ,
CHANGE COLUMN LMODDATE CREATED_DATE DATETIME NULL DEFAULT NULL ;
