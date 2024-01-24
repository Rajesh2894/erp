--liquibase formatted sql
--changeset Anil:V20190617172658__AL_tb_bill_det_hist_13062019.sql
drop table if exists tb_bill_det_hist;
--liquibase formatted sql
--changeset Anil:V20190617172658__AL_tb_bill_det_hist_130620191.sql
CREATE TABLE tb_bill_det_hist (
  H_TBD_ID bigint(12) NOT NULL COMMENT 'Primary Key' ,
  TBD_ID bigint(12) NOT NULL,
  BM_ID bigint(12) NOT NULL,
  TAX_ID bigint(12) NOT NULL,
  TBD_AMOUNT decimal(10,0) DEFAULT NULL,
  SAC_HEAD_ID bigint(12) DEFAULT NULL,
  ACC_NO bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL,
  CREATEDBY int(11) NOT NULL,
  CREATEDDATETIME datetime NOT NULL,
  LASTUPDATEDBY int(11) DEFAULT NULL,
  LASTUPDATEDDATETIME datetime DEFAULT NULL,
  ISDELETED varchar(1) NOT NULL,
  SBD_REMARKS varchar(50) DEFAULT NULL,
  H_STATUS varchar(2) DEFAULT NULL COMMENT 'History status',
  PRIMARY KEY (H_TBD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8