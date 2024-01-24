--liquibase formatted sql
--changeset nilima:V20170914115047__AL_TB_AC_BILL_EXP_DETAIL_05092017.sql
ALTER TABLE TB_AC_BILL_EXP_DETAIL 
DROP COLUMN LANG_ID,
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT '' ,
CHANGE COLUMN FI04_N1 SAC_HEAD_ID BIGINT(12) NOT NULL COMMENT 'Account Headcode Id' ;
