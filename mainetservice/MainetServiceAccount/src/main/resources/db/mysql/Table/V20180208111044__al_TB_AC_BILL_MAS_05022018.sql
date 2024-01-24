--liquibase formatted sql
--changeset priya:V20180208111044__al_TB_AC_BILL_MAS_05022018.sql
ALTER TABLE tb_ac_bill_mas 
CHANGE COLUMN LANG_ID LANG_ID INT(11) NULL DEFAULT NULL COMMENT 'language identity' ;
--liquibase formatted sql
--changeset priya:V20180208111044__al_TB_AC_BILL_MAS_050220181.sql
ALTER TABLE tb_ac_bill_mas 
CHANGE COLUMN DEP_ID INT_REF_ID BIGINT(12) NULL DEFAULT NULL COMMENT '' ;
