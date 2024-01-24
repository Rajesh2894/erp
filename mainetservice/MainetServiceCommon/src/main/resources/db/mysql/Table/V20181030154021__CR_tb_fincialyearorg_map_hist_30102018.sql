--liquibase formatted sql
--changeset nilima:V20181030154021__CR_tb_fincialyearorg_map_hist_30102018.sql
drop table if exists tb_fincialyearorg_map_hist;

--liquibase formatted sql
--changeset nilima:V20181030154021__CR_tb_fincialyearorg_map_hist_301020181.sql
CREATE TABLE tb_fincialyearorg_map_hist (
  H_MAP_ID bigint(12) NOT NULL  COMMENT 'Primary Key (map id)',
  MAP_ID bigint(12)   COMMENT 'Primary Key (map id)',
  FA_YEARID bigint(12)  COMMENT 'Financial year Id',
  REMARK varchar(600)  COMMENT 'remark\n',
  fa_frommonth int(3)  COMMENT 'Fiscale from  Month',
  fa_tomonth int(3)  COMMENT 'Fiscale To  Month',
  fa_fromyear int(4)  COMMENT 'Fiscale From Year',
  fa_toyear int(4)  COMMENT 'Fiscale To Year',
  fa_monstatus int(12)  COMMENT 'Fiscale "SOFT" close',
  fa_yearstatus int(12)  COMMENT 'Fiscale "Hard" close',
  MAP_STATUS char(1)  COMMENT 'Status Of the record',
  H_STATUS varchar(2) COMMENT 'status Of the record',
  COM_V1 varchar(200)  COMMENT 'additional varchar com_v1 to be used in future\n',
  COM_V2 varchar(200)  COMMENT 'additional varchar com_v2 to be used in future',
  COM_V3 varchar(200)  COMMENT 'additional varchar com_v3 to be used in future',
  COM_V4 varchar(200)  COMMENT 'additional varchar com_v4 to be used in future',
  COM_V5 varchar(200)  COMMENT 'additional varchar com_v5 to be used in future',
  COM_N1 decimal(15,0)  COMMENT 'additional number com_n1 to be used in future\n',
  COM_N2 decimal(15,0)  COMMENT 'additional number com_n2 to be used in future\n',
  COM_N3 decimal(15,0)  COMMENT 'additional number com_n3 to be used in future\n',
  COM_N4 decimal(15,0)  COMMENT 'additional number com_n4 to be used in future\n',
  COM_N5 decimal(15,0)  COMMENT 'additional number com_n5 to be used in future\n',
  COM_D1 datetime  COMMENT 'additional Datetime com_d1 to be used in future\n',
  COM_D2 datetime  COMMENT 'additional Datetime com_d2 to be used in future\n',
  COM_D3 datetime  COMMENT 'additional Datetime com_d3 to be used in future\n',
  COM_LO1 char(1)  COMMENT 'additional logical field com_lo1 to be used in future\n',
  COM_LO2 char(1)  COMMENT 'additional logical field com_lo2 to be used in future\n',
  COM_LO3 char(1)  COMMENT 'additional logical field com_lo3 to be used in future\n',
  ORGID int(11)  COMMENT 'Organisation Id',
  CREATED_BY int(11)  COMMENT ' user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date\n',
  UPDATED_BY int(11)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record\n',
  PRIMARY KEY (H_MAP_ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
