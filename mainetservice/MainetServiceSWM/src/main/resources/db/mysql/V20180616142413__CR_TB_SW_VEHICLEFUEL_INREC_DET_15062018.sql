--liquibase formatted sql
--changeset nilima:V20180616142413__CR_TB_SW_VEHICLEFUEL_INREC_DET_15062018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_INREC_DET (
  INRECD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  INREC_ID bigint(12) NOT NULL COMMENT 'FK  TB_VEHICLEFUEL_INREC',
  VEF_ID bigint(12) NOT NULL COMMENT 'FK  TB_SW_VEHICLEFUEL_MAST',
  INRECD_INVNO bigint(12) NOT NULL COMMENT 'Invoice No.',
  INRECD_INVDATE date NOT NULL COMMENT 'iNVoice Date',
  INRECD_INVAMT bigint(12) NOT NULL COMMENT 'Invoice Amount',
  INRECD_EXPEN bigint(12) DEFAULT NULL COMMENT 'Invoice Expenditure Head',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (INRECD_ID),
  KEY VEF_ID (VEF_ID),
  KEY FK_INREC_ID_idx (INREC_ID),
  CONSTRAINT FK_INREC_ID FOREIGN KEY (INREC_ID) REFERENCES tb_sw_vehiclefuel_inrec (INREC_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_INRE_VEFID FOREIGN KEY (VEF_ID) REFERENCES tb_sw_vehiclefuel_mast (VEF_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Invoice Reconcilation Detail';