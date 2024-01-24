--liquibase formatted sql
--changeset priya:V20180123180338__AL_TB_WMS_GEN_RATE_MAST.sql
drop table if exists TB_WMS_MATERIAL_MAST;
--liquibase formatted sql
--changeset priya:V20180123180338__AL_TB_WMS_GEN_RATE_MAST1.sql
CREATE TABLE TB_WMS_GEN_RATE_MAST (
  MA_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  SOR_ID bigint(12) NOT NULL COMMENT 'Foregin Key tb_wms_sor_mast',
  MA_TYPEID bigint(12) DEFAULT NULL COMMENT 'Master Type(Material/Loading&unloading/Labour/stacking/Machinary)',
  MA_ITEMNO varchar(50) NOT NULL COMMENT 'Item Number',
  MA_DESCRIPTION varchar(45) NOT NULL COMMENT 'Item Description',
  MA_ITEM_UNIT bigint(12) NOT NULL COMMENT 'Item Unit',
  MA_RATE decimal(15,2) NOT NULL COMMENT 'Rate',
  MA_ACTIVE char(1) NOT NULL COMMENT 'Material Active/Inactive',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  lg_ip_mac_upd varchar(100) DEFAULT NULL,
  PRIMARY KEY (MA_ID),
  KEY FK_SOR_GEN_RATE_idx (SOR_ID),
  CONSTRAINT FK_SOR_GEN_RATE FOREIGN KEY (SOR_ID) REFERENCES tb_wms_sor_mast (SOR_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Maintaine Details of Material';