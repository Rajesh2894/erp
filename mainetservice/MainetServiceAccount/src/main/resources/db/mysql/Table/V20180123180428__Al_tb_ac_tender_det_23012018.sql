--liquibase formatted sql
--changeset priya:V20180123180428__Al_tb_ac_tender_det_23012018.sql
ALTER TABLE tb_ac_tender_det 
CHANGE COLUMN FI04_V1 SAC_HEAD_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'additional nvarchar2 fi04_v1 to be used in future' ;
