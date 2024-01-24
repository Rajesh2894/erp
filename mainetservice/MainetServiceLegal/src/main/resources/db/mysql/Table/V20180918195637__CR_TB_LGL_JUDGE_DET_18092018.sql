--liquibase formatted sql
--changeset nilima:V20180918195637__CR_TB_LGL_JUDGE_DET_18092018.sql
CREATE TABLE TB_LGL_JUDGE_DET (
  judge_det_id bigint(12) NOT NULL COMMENT 'Primary Key',
  judge_id bigint(12) NOT NULL COMMENT 'foregin key TB_LGL_JUDGE_MAST',
  crt_id bigint(12) NOT NULL COMMENT 'Court Detail',
  from_period date NOT NULL COMMENT 'Appointment from ',
  to_period date DEFAULT NULL COMMENT 'Appointment To',
  judge_status varchar(50) NOT NULL COMMENT 'Appointment Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (judge_det_id),
  KEY FK_JUDGEID_idx (judge_id),
  KEY FK_COURTID_idx (crt_id),
  CONSTRAINT FK_COURTID FOREIGN KEY (crt_id) REFERENCES tb_lgl_court_mast (CRT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_JUDGEID FOREIGN KEY (judge_id) REFERENCES tb_lgl_judge_mast (JUDGE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='judge detail';
