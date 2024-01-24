--liquibase formatted sql
--changeset Anil:V20190801114252__CR_TB_LN_AQUISN_01082019.sql
drop table if exists TB_LN_AQUISN;
--liquibase formatted sql
--changeset Anil:V20190801114252__CR_TB_LN_AQUISN_010820191.sql
CREATE TABLE TB_LN_AQUISN(
  LNAQ_ID bigint(12) NOT NULL COMMENT 'Primary key',
  DEPT_APPL_ID bigint(16) NOT NULL COMMENT 'Service ID',
  LN_DESC varchar(100) NOT NULL COMMENT 'Land Description',
  LOC_ID bigint(10) NOT NULL COMMENT 'Location',
  ACQ_MODE bigint(10) NOT NULL COMMENT 'Leasehold/Freehold(Prefix AQM)',
  LN_SERVNO varchar(100) NOT NULL COMMENT 'Survey No',
  LN_AREA decimal(12,2) NOT NULL COMMENT 'Area(Sq.mtr)',
  PAY_TO varchar(100) NOT NULL COMMENT 'To whom paid',
  LN_OTH char(1) NOT NULL COMMENT 'Building/tress on land',
  ACQ_PURPOSE varchar(100) NOT NULL COMMENT 'Purpose',
  LN_TTL char(1) DEFAULT NULL COMMENT 'Tittle document',
  ACQ_DT date DEFAULT NULL COMMENT 'acquisition date',
  PYM_ORDER varchar(100) DEFAULT NULL COMMENT 'Payment Order No',
  ACQ_COST decimal(12,2) DEFAULT NULL COMMENT 'acquisition cost',
  LN_REMARK varchar(100) DEFAULT NULL COMMENT 'Remarks',
  ACQ_STATUS char(1) NOT NULL COMMENT 'T-transit (In process)/A-Aquired',
  ORGID bigint(12) NOT NULL COMMENT 'Organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine Login Name|IP Address|Physical Address',
  LANG_ID bigint(12) NOT NULL COMMENT 'Language Identity',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'Updated User Identity',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Updated Modification Date',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name|IP Address|Physical Address',
  PRIMARY KEY (LNAQ_ID)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;

