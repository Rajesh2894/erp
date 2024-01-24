--liquibase formatted sql
--changeset nilima:V20171130192622__AL_TB_TAX_AC_MAPPING_HIST.sql
DROP TABLE IF EXISTS TB_TAX_MAS_HIST;
--liquibase formatted sql
--changeset nilima:V20171130193541__CR_TB_TAX_MAS_HIST.sql
CREATE TABLE TB_TAX_MAS_HIST (
  H_TAXID bigint(12),
  TAX_ID bigint(12) ,
  TAX_DESC varchar(100) ,
  TAX_METHOD varchar(60) ,
  TAX_VALUE_TYPE varchar(60) ,
  PARENT_CODE bigint(12) ,
  TAX_GROUP varchar(100) ,
  TAX_PRINT_ON1 varchar(100) ,
  TAX_CODE varchar(100) ,
  TAX_DISPLAY_SEQ double ,
  DP_DEPTID double ,
  COLL_MTD double ,
  COLL_SEQ double ,
  ORGID bigint(11) ,
  CREATED_BY bigint(11) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  TAX_CATEGORY1 bigint(12) ,
  H_STATUS varchar(2) ,
  TAX_CATEGORY2 bigint(12) ,
  TAX_CATEGORY3 bigint(12) ,
  TAX_CATEGORY4 bigint(12) ,
  TAX_CATEGORY5 varchar(45) ,
  TAX_DESC_ID bigint(12) ,
  TAX_APPLICABLE bigint(12) ,
  SM_SERVICE_ID bigint(12) ,
  PRIMARY KEY (H_TAXID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
