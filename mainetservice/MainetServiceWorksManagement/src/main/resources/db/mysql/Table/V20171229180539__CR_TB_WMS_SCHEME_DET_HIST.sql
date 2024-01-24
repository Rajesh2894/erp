--liquibase formatted sql
--changeset jinea:V20171229180539__CR_TB_WMS_SCHEME_DET_HIST.sql
CREATE TABLE TB_WMS_SCHEME_DET_HIST(
  SCHD_ID_H BIGINT(12),
  SCHD_ID BIGINT(12)  COMMENT 'Primary Key',
  SCH_ID BIGINT(12)  COMMENT 'Foregin  Key(TB_WMS_SCHEME_MAST)',
  SCHD_SPONSORED_BY VARCHAR(250)  COMMENT 'Scheme Sponsoror BY',
  SCHD_SHARING_PER DECIMAL(3,2)  COMMENT 'Scheme Sharing Percentage',
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME  COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'machine ip address from where user has updated the record',
  H_STATUS CHAR(1));
