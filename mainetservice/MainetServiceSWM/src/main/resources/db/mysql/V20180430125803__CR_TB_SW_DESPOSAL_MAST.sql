--liquibase formatted sql
--changeset nilima:V20180430125803__CR_TB_SW_DESPOSAL_MAST.sql
CREATE TABLE TB_SW_DESPOSAL_MAST (
   DE_ID BIGINT(12) NOT NULL COMMENT 'Primay Key',
  DE_NAME VARCHAR(100) NOT NULL COMMENT 'Disposal Site Name',
  DE_NAME_REG VARCHAR(100) NOT NULL COMMENT 'Disposal Site Name REG',
  DE_AREA DECIMAL(20,2) NOT NULL COMMENT 'Disposal Area',
  DE_CATEGORY BIGINT(12) NOT NULL COMMENT 'Disposal Category',
  DE_ADDRESS VARCHAR(200) NOT NULL COMMENT 'Address',
  DE_CAPACITY DECIMAL(20,2) NOT NULL COMMENT 'Disposal Capacity',
  DE_CAPACITY_UNIT BIGINT(12) NOT NULL COMMENT 'Capacity Unit prefix',
  DE_GIS_ID VARCHAR(15) NULL,
  DE_ACTIVE CHAR(1) NOT NULL COMMENT 'Desposal Site (Active->\'Y\',Inactive->\'N\')',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY ( DE_ID))
COMMENT = 'Desposal Master';