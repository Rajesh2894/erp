--liquibase formatted sql
--changeset nilima:V20170718190311__AL_TB_AC_CODINGSTRUCTURE_MAS_13072017.sql
update TB_AC_CODINGSTRUCTURE_MAS set LG_IP_MAC=now();
commit;
ALTER TABLE TB_AC_CODINGSTRUCTURE_MAS 
CHANGE COLUMN CODCOF_ID CODCOF_ID BIGINT(12) NOT NULL DEFAULT '0' COMMENT 'primary Key' ,
CHANGE COLUMN COM_APPFLAG COM_STATUS CHAR(1) NULL COMMENT 'Applicable (Y/N)'  ,
CHANGE COLUMN COM_CPD_ID COM_CPD_ID BIGINT(12) NOT NULL COMMENT 'prefix (CMD->Chart of Account)' ,
CHANGE COLUMN COD_NO_LEVEL COD_NO_LEVEL BIGINT(12) NOT NULL COMMENT 'No of Level for Hirarchey defination' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT 'orgnisation id';


