--liquibase formatted sql
--changeset nilima:V20170725115616__AL_TB_AC_SECONDARYHEAD_MASTER_210720172.sql
update TB_AC_SECONDARYHEAD_MASTER set STATUS_CPD_ID=114;
COMMIT;

--liquibase formatted sql
--changeset nilima:V20170725115616__AL_TB_AC_SECONDARYHEAD_MASTER_210720173.sql
update TB_AC_SECONDARYHEAD_MASTER set LG_IP_MAC='MH-0099/192.168.100.215/00-22-4D-9F-89-42';
COMMIT;


--liquibase formatted sql
--changeset nilima:V20170725115616__AL_TB_AC_SECONDARYHEAD_MASTER_21072017.sql
ALTER TABLE TB_AC_SECONDARYHEAD_MASTER 
DROP COLUMN LANG_ID,
DROP COLUMN SAC_SUB_LEDDGER_TYPE_CPD_ID,
CHANGE COLUMN CODCOFDET_ID CODCOFDET_ID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN SAC_HEAD_CODE SAC_HEAD_CODE VARCHAR(10) NOT NULL COMMENT '' ,
CHANGE COLUMN SAC_HEAD_DESC SAC_HEAD_DESC VARCHAR(1000) NOT NULL COMMENT '' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN USER_ID CREATED_BY BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN LMODDATE CREATED_DATE DATETIME NOT NULL COMMENT '' ,
CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL COMMENT '' ,
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT '' ,
CHANGE COLUMN FI04_N1 FN_FUNCTION_ID BIGINT(15) NULL COMMENT '' ,
CHANGE COLUMN STATUS_CPD_ID STATUS_CPD_ID BIGINT(12) NOT NULL COMMENT '' ;

--liquibase formatted sql
--changeset nilima:V20170725115616__AL_TB_AC_SECONDARYHEAD_MASTER_210720171.sql
ALTER TABLE TB_AC_SECONDARYHEAD_MASTER 
ADD CONSTRAINT FK_SECOND_CODCOFDET_ID
  FOREIGN KEY (CODCOFDET_ID)
  REFERENCES TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT FK_PAC_HEAD_ID
  FOREIGN KEY (PAC_HEAD_ID)
  REFERENCES TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
