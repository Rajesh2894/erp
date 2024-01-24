--liquibase formatted sql
--changeset nilima:V20171130193416__CR_TB_TAX_DET_HIST_24112017.sql
DROP TABLE IF EXISTS TB_TAX_DET_HIST;

--liquibase formatted sql
--changeset nilima:V20171130193416__CR_TB_TAX_DET_HIST_241120171.sql
CREATE TABLE TB_TAX_DET_HIST (
  H_DETID bigint(12) ,
  TD_TAXDET bigint(12) ,
  TM_TAXID bigint(12) ,
  TD_DEPEND_FACT bigint(12) ,
  STATUS varchar(1) ,
  ORGID int(11) ,
  CREATED_BY bigint(11) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  H_STATUS varchar(2) ,
  PRIMARY KEY (H_DETID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


