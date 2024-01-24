--liquibase formatted sql
--changeset Anil:V20191205122401__CR_tb_adh_det_hist_05122019.sql
drop table if exists tb_adh_det_hist;
--liquibase formatted sql
--changeset Anil:V20191205122401__CR_tb_adh_det_hist_051220191.sql
CREATE TABLE tb_adh_det_hist(
  ADHDET_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  ADHDET_ID bigint(12) NOT NULL COMMENT 'Id',
  ADH_ID bigint(12) NOT NULL COMMENT 'id',
  ADH_TYPE_ID1 bigint(12) NOT NULL COMMENT 'Prefix Id Level 1',
  ADH_TYPE_ID2 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 2',
  ADH_TYPE_ID3 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 3',
  ADH_TYPE_ID4 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 4',
  ADH_TYPE_ID5 bigint(12) DEFAULT NULL COMMENT 'Prefix Id Level 5',
  ADH_DESC varchar(100) NOT NULL COMMENT 'Text',
  ADH_LENGTH decimal(12,2) DEFAULT NULL COMMENT 'Text',
  ADH_HEIGHT decimal(12,2) DEFAULT NULL COMMENT 'Text',
  ADH_AREA decimal(12,2) DEFAULT NULL COMMENT 'Text',
  ADH_UNIT bigint(10) DEFAULT NULL COMMENT 'Number',
  HRD_ID bigint(10) DEFAULT NULL COMMENT 'Hoarding ID',
  DISPL_TYPID bigint(12) NOT NULL COMMENT 'Prefix id',
  ADH_FEE decimal(12,2) DEFAULT NULL COMMENT 'Number',
  GIS_ID varchar(20) DEFAULT NULL COMMENT 'Number',
  ORGID bigint(12) NOT NULL COMMENT 'Organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Updated Modification Date',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
  H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (ADHDET_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
