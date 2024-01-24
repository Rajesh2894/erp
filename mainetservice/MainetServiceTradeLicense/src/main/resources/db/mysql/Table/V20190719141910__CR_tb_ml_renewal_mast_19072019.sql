--liquibase formatted sql
--changeset Anil:V20190719141910__CR_tb_ml_renewal_mast_19072019.sql
drop TABLE if exists tb_ml_renewal_mast;
--liquibase formatted sql
--changeset Anil:V20190719141910__CR_tb_ml_renewal_mast_190720191.sql
CREATE TABLE tb_ml_renewal_mast(
  TRE_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  APM_APPLICATION_ID bigint(16) DEFAULT NULL,
  TRD_ID bigint(12) DEFAULT NULL COMMENT 'System Generated Lic number',
  TRE_LICFROM_DATE date DEFAULT NULL COMMENT 'License From',
  TRE_LICTO_DATE date DEFAULT NULL COMMENT 'License To',
  TRE_STATUS char(1) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
