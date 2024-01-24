--liquibase formatted sql
--changeset priya:V20180417133855__CR_TB_WMS_MEASUREMENT_DETAIL_HIST.sql
drop table if exists TB_WMS_MEASUREMENT_DETAIL_HIST;
--liquibase formatted sql
--changeset priya:V20180417133855__CR_TB_WMS_MEASUREMENT_DETAIL_HIST1.sql
CREATE TABLE TB_WMS_MEASUREMENT_DETAIL_HIST (
  ME_ID_H bigint(12) NOT NULL COMMENT 'Primary Jey',
  ME_ID bigint(12)  COMMENT 'Primary Jey',
  WORKE_ID bigint(12)  COMMENT 'Foregin Jey(TB_WMS_WORKESTIMATE_MAS)\n',
  ME_PARTICULARE varchar(500)  COMMENT 'Measurement Particulare',
  ME_NOS bigint(5)  COMMENT 'Nos',
  ME_VALUE_TYPE char(1)  COMMENT 'Value Type (Calculated->C,Direct->D,Formula->F)',
  ME_LENGTH decimal(7,2) DEFAULT NULL COMMENT 'Length',
  ME_BREADTH decimal(7,2) DEFAULT NULL COMMENT 'Breadth',
  ME_HEIGHT decimal(7,2) DEFAULT NULL COMMENT 'Height',
  ME_VALUE decimal(12,2) DEFAULT NULL COMMENT 'Value',
  ME_FORMULA varchar(100) DEFAULT NULL COMMENT 'Formula',
  ME_TOTAL decimal(12,2)  COMMENT 'Total Quantity',
  ME_TYPE char(1)  COMMENT 'Deviation Type (Addition->A,Deduction->D)',
  ME_ACTIVE char(1) ,
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ME_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work estimation measurement details';