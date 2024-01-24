--liquibase formatted sql
--changeset nilima:V20180730145004__AL_tb_sw_employee_scheddet_hist_30072018.sql
drop table if exists tb_sw_employee_scheddet_hist;

--liquibase formatted sql
--changeset nilima:V20180730145004__AL_tb_sw_employee_scheddet_hist_300720181.sql
CREATE TABLE tb_sw_employee_scheddet_hist (
  EMSD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  EMSD_ID bigint(12) COMMENT 'Primary Key',
  EMS_ID bigint(12) COMMENT 'FK TB_SW_EMPLOYEE_SCHEDULING',
  EMPID bigint(12) COMMENT 'FK EMPLOYEE',
  DE_ID bigint(12)  COMMENT 'FK TB_SW_DESPOSAL_MAST',
  EMSD_COLL_TYPE bigint(12)  COMMENT 'Collection Type',
  LOC_ID bigint(12)  COMMENT 'FK TB_LOCATION_MAST',
  VE_ID bigint(12)  COMMENT 'FK TB_SW_VEHICLE_MAST',
  RO_ID bigint(12) ,
  COD_WARD1 bigint(12)  COMMENT 'Ward',
  COD_WARD2 bigint(12)  COMMENT 'Zone',
  COD_WARD3 bigint(12)  COMMENT 'Block',
  COD_WARD4 bigint(12) ,
  COD_WARD5 bigint(12) ,
  EMSD_DAY varchar(14)  COMMENT '(OCC->''W'' then Monday->MON etc.)',
  EMSD_STARTTIME datetime  COMMENT 'From Time',
  EMSD_ENDTIME datetime  COMMENT 'To Time',
  H_STATUS varchar(1) COMMENT 'Status I->Insert,update',
  ORGID bigint(12) COMMENT 'organization id',
  CREATED_DATE datetime  COMMENT 'datetime ',
  CREATED_BY bigint(12) NOT NULL,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  ESD_SCHEDULEDATE date ,
  PRIMARY KEY (EMSD_ID_H)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

