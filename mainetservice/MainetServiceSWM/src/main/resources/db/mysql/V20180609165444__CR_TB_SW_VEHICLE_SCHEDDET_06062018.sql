--liquibase formatted sql
--changeset nilima:V20180609165444__CR_TB_SW_VEHICLE_SCHEDDET_06062018.sql
CREATE TABLE TB_SW_VEHICLE_SCHEDDET (
  VESD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_VEHICLE_SCHEDULING',
  RO_ID bigint(12) NOT NULL COMMENT 'FK tb_sw_route_mast',
  VES_STARTIME datetime NOT NULL,
  VES_ENDTIME datetime NOT NULL,
  VES_WEEKDAY int(5) DEFAULT NULL COMMENT 'Week Day',
  VES_MONTH int(5) DEFAULT NULL COMMENT 'Week Month',
  VES_COLL_TYPE bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VESD_ID),
  KEY FK_VES_ID_idx (VES_ID),
  KEY FK_RO_ID_idx (RO_ID),
  CONSTRAINT FK_RO_ID FOREIGN KEY (RO_ID) REFERENCES tb_sw_route_mast (RO_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_VES_ID FOREIGN KEY (VES_ID) REFERENCES tb_sw_vehicle_scheduling (VES_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Schedule Detail';

