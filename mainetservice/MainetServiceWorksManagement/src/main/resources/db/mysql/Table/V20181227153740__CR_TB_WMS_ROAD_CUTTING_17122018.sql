--liquibase formatted sql
--changeset nilima:V20181227153740__CR_TB_WMS_ROAD_CUTTING_17122018.sql
CREATE TABLE TB_WMS_ROAD_CUTTING (
  RC_ID bigint(12) NOT NULL,
  APM_APPLICATION_ID bigint(12) DEFAULT NULL,
  RC_COMNAME varchar(200) NOT NULL COMMENT 'Company Name ',
  RC_COMMOFFADD varchar(500) NOT NULL COMMENT 'Company Main Office Address.',
  RC_COMREPNAME varchar(100) DEFAULT NULL,
  RC_COMREPADD varchar(500) DEFAULT NULL,
  RC_COMFAXNUM varchar(100) DEFAULT NULL COMMENT 'Company Fax Number  ',
  RC_COMTELNO bigint(15) DEFAULT NULL COMMENT 'Company Telephone Number. ',
  RC_COMREPMOB bigint(15) DEFAULT NULL COMMENT 'Company Representative Mobile Number.',
  RC_COMREPEMAIL varchar(50) DEFAULT NULL COMMENT 'Company Representative Email-ID',
  RC_COMLOCOFFNM varchar(100) DEFAULT NULL COMMENT 'Company Local Office name  ',
  RC_COMLOCOFFADD varchar(500) DEFAULT NULL COMMENT 'Company Local Address  ',
  RC_COMLOCREPNAME varchar(100) DEFAULT NULL COMMENT 'Company Local Office Representative Name. ',
  RC_COMLOCREPADD varchar(500) DEFAULT NULL COMMENT 'Company Representative Address. ',
  RC_COMLOCREPMOB bigint(15) DEFAULT NULL COMMENT 'Company Local Office Representative Mobile No. ',
  RC_COMLOCFAXNUM varchar(100) DEFAULT NULL COMMENT 'Company Local Office Fax number.',
  RC_COMLOCTELNO bigint(12) DEFAULT NULL COMMENT 'Company Local Office Telephone ',
  RC_COMLOCREPEMAIL varchar(50) DEFAULT NULL COMMENT 'Company Local Office Representative Email-Id ',
  RC_CONTNM varchar(100) DEFAULT NULL COMMENT 'Contractor name ',
  RC_CONTADD varchar(500) DEFAULT NULL COMMENT 'Contractor Address',
  RC_CONTACTPNM varchar(100) DEFAULT NULL COMMENT 'Contact Person Name ',
  RC_CONTACTPMOB bigint(15) DEFAULT NULL COMMENT 'Contact Person Mobile No ',
  RC_CONTACTPEML varchar(50) DEFAULT NULL COMMENT 'Contact Person E-mail id ',
  RC_COSTPROJECT decimal(20,2) DEFAULT NULL,
  RC_ROADDAMAGE decimal(20,2) DEFAULT NULL,
  COD_WARD1 bigint(12) DEFAULT NULL,
  COD_WARD2 bigint(12) DEFAULT NULL,
  COD_WARD3 bigint(12) DEFAULT NULL,
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RC_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
