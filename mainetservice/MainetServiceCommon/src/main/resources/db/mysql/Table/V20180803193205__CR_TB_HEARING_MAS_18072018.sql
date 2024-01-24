--liquibase formatted sql
--changeset nilima:V20180803193205__CR_TB_HEARING_MAS_18072018.sql
CREATE TABLE TB_HEARING_MAS (
  HR_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  OBJ_ID bigint(12) NOT NULL COMMENT 'fk tb_objection_mast',
  HR_NO varchar(30) NOT NULL COMMENT 'HRpection Number',
  HR_DATE datetime NOT NULL COMMENT 'HRpection Raise Date',
  EMPID bigint(12) DEFAULT NULL COMMENT 'HRpector Name',
  HR_REMARK varchar(200) DEFAULT NULL COMMENT 'HRpection Remark',
  HR_AVAIL_PERSON char(1) DEFAULT NULL COMMENT 'Owner/Representative',
  HR_ATT_NAME varchar(45) DEFAULT NULL COMMENT 'HRepection Attendy Name',
  HR_ATT_MOBNO varchar(30) DEFAULT NULL COMMENT 'HRpection Attendy Mobile No',
  HR_ATT_EMAILID varchar(100) DEFAULT NULL COMMENT 'HRpection Attendy Email id',
  HR_STATUS bigint(12) NOT NULL COMMENT 'HRpection Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE varchar(45) DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (HR_ID),
  KEY FK_HROBJID_idx (OBJ_ID),
  CONSTRAINT FK_HROBJID FOREIGN KEY (OBJ_ID) REFERENCES tb_objection_mast (OBJ_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
