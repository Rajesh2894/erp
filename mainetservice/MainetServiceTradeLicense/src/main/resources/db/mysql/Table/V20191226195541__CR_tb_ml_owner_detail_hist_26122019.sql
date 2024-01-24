--liquibase formatted sql
--changeset Anil:V20191226195541__CR_tb_ml_owner_detail_hist_26122019.sql
drop table if exists tb_ml_owner_detail_hist;
--liquibase formatted sql
--changeset Anil:V20191226195541__CR_tb_ml_owner_detail_hist_261220191.sql
CREATE TABLE tb_ml_owner_detail_hist (
  TRO_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  TRO_ID bigint(12) NOT NULL COMMENT 'TRI ID',
  TRD_ID bigint(12) NOT NULL COMMENT 'Foregin Key',
  TRO_TITLE bigint(12) DEFAULT NULL,
  TRO_NAME varchar(100) NOT NULL COMMENT 'Owner Name',
  TRO_MNAME varchar(50) DEFAULT NULL,
  TRO_GEN char(1) DEFAULT NULL,
  TRO_ADDRESS varchar(200) NOT NULL COMMENT 'Owner Address',
  TRO_EMAILID varchar(256) NOT NULL COMMENT 'Owner Email id',
  TRO_MOBILENO varchar(10) NOT NULL COMMENT 'Owner Mobile No',
  TRO_ADHNO bigint(12) DEFAULT NULL COMMENT 'Owner Aadhar No.',
  TRO_PR varchar(45) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRO_ID_H),
  KEY FK_TRDO_idx (TRD_ID),
  CONSTRAINT FK_TRDO_H FOREIGN KEY (TRD_ID) REFERENCES tb_ml_trade_mast (TRD_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
