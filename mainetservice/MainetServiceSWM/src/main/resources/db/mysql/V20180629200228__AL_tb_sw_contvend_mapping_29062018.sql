--liquibase formatted sql
--changeset nilima:V20180629200228__AL_tb_sw_contvend_mapping_29062018.sql
DROP TABLE if exists tb_sw_contvend_mapping_hist;

--liquibase formatted sql
--changeset nilima:V20180629200228__AL_tb_sw_contvend_mapping_290620181.sql
ALTER TABLE tb_sw_contvend_mapping
ADD COLUMN MAP_GARBAGE_UNIT BIGINT(12) NULL AFTER MAP_GARBAGE;

--liquibase formatted sql
--changeset nilima:V20180629200228__AL_tb_sw_contvend_mapping_290620182.sql
CREATE TABLE TB_SW_CONTVEND_MAPPING_HIST (
  MAP_ID_H bigint(12) NOT NULL,
  MAP_ID bigint(12) ,
  CONT_ID bigint(12)  COMMENT 'FK tb_contract_mast',
  MAP_TASK_ID varchar(50)  COMMENT 'Task Mapping',
  COD_WARD1 bigint(12) ,
  COD_WARD2 bigint(12) ,
  COD_WARD3 bigint(12) ,
  COD_WARD4 bigint(12) ,
  COD_WARD5 bigint(12) ,
  MAP_WASTETYPE bigint(12) ,
  MAP_GARBAGE bigint(12) ,
  MAP_GARBAGE_UNIT bigint(12) ,
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',  
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (MAP_ID_H)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
