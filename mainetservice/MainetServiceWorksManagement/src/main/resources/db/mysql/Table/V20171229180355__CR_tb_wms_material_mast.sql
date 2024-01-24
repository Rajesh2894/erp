--liquibase formatted sql
--changeset jinea:V20171229180355__CR_tb_wms_material_mast.sql
DROP TABLE IF EXISTS TB_WMS_MATERIAL_MAST;
--liquibase formatted sql
--changeset jinea:V20171229180355__CR_tb_wms_material_mast1.sql
CREATE TABLE tb_wms_material_mast (
  MA_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  SOR_ID BIGINT(12) NOT NULL COMMENT 'Foregin Key tb_wms_sor_mast',
  MA_ITEMNO VARCHAR(50) NOT NULL COMMENT 'Item Number',
  MA_DESCRIPTION VARCHAR(45) NOT NULL COMMENT 'Item Description',
  MA_ITEM_UNIT BIGINT(12) NOT NULL COMMENT 'Item Unit',
  MA_RATE DECIMAL(15,2) NOT NULL COMMENT 'Rate',
  MA_ACTIVE CHAR(1) NOT NULL COMMENT 'Material Active/Inactive',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL,
  UPDATED_DATE DATETIME NULL,
  LG_IP_MAC VARCHAR(100) NULL,
  lg_ip_mac_upd VARCHAR(100) NULL,
  PRIMARY KEY (MA_ID),
  INDEX FK_SOR_MATERIAL_idx (SOR_ID ASC),
  CONSTRAINT FK_SOR_MATERIAL
    FOREIGN KEY (SOR_ID)
    REFERENCES tb_wms_sor_mast (SOR_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Maintaine Details of Material';