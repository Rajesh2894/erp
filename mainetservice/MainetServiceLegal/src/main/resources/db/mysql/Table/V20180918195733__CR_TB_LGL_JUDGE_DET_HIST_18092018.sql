--liquibase formatted sql
--changeset nilima:V20180918195733__CR_TB_LGL_JUDGE_DET_HIST_18092018.sql
CREATE TABLE TB_LGL_JUDGE_DET_HIST (
  judge_det_id_H bigint(12) NOT NULL COMMENT 'Primary Key',
  judge_det_id bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  judge_id bigint(12) DEFAULT NULL COMMENT 'foregin key TB_LGL_JUDGE_MAST',
  crt_id bigint(12) DEFAULT NULL COMMENT 'Court Detail',
  from_period date DEFAULT NULL COMMENT 'Appointment from ',
  to_period date DEFAULT NULL COMMENT 'Appointment To',
  judge_status varchar(50) DEFAULT NULL COMMENT 'Appointment Status',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (judge_det_id_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='judge detail History';