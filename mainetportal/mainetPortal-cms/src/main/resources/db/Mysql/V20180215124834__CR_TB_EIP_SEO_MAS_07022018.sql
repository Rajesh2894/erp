--liquibase formatted sql
--changeset priya:V20180215124834__CR_TB_EIP_SEO_MAS_07022018.sql
DROP TABLE IF EXISTS TB_EIP_SEO_MAS;
CREATE TABLE TB_EIP_SEO_MAS (
  SEO_ID BIGINT(12) NOT NULL COMMENT 'PRIMARY KEY',
  KEY_WORD VARCHAR(2000) DEFAULT NULL COMMENT 'METADATA',
  DESCRIPTION VARCHAR(2000) DEFAULT NULL COMMENT 'DESCRIPTION',
  ORGID BIGINT(12) NOT NULL COMMENT 'ORGANIZATION ID',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'USER ID WHO CREATED THE RECORD',
  CREATED_DATE DATETIME NOT NULL COMMENT 'RECORD CREATION DATE',
  UPDATED_BY BIGINT(12) DEFAULT NULL COMMENT 'USER ID WHO UPDATED THE RECORD',
  UPDATED_DATE DATETIME DEFAULT NULL COMMENT 'DATE ON WHICH UPDATED THE RECORD',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'MACHINE IP ADDRESS FROM WHERE USER HAS CREATED THE RECORD',
  LG_IP_MAC_UPD VARCHAR(100) DEFAULT NULL COMMENT 'MACHINE IP ADDRESS FROM WHERE USER HAS UPDATED THE RECORD',
  PRIMARY KEY (SEO_ID)
) ;