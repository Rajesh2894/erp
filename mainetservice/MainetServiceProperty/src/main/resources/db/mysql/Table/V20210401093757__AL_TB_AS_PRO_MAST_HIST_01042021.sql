--liquibase formatted sql
--changeset Kanchan:V20210401093757__AL_TB_AS_PRO_MAST_HIST_01042021.sql
alter table TB_AS_PRO_MAST_HIST  
add column PRO_REFPROPNO varchar(100) DEFAULT NULL,
add column   PRO_ASSNTDT datetime DEFAULT NULL,
  add column PRO_LSTASSNTDT datetime DEFAULT NULL,
  add column PRO_REVISE_ASSNTDT datetime DEFAULT NULL,
  add column CPD_BILLMETH bigint(12) DEFAULT NULL,
  add column PRO_BILL_CNG_REASON varchar(100) DEFAULT NULL,
  add column PRO_SURVEYTYP bigint(12) DEFAULT NULL,
  add column PRO_ADDN_SURVEYNO varchar(100) DEFAULT NULL,
  add column PRO_AALINO varchar(100) DEFAULT NULL,
  add column PRO_CHAALTANO varchar(100) DEFAULT NULL,
  add column PRO_AREA_NAME varchar(100) DEFAULT NULL,
  add column PRO_CLUSTER_NO bigint(12) DEFAULT NULL,
  add column PRO_CLU_BUILDING_NO bigint(12) DEFAULT NULL,
  add column PRO_URB_LOC_GRP varchar(100) DEFAULT NULL,
  add column PRO_ADDN_CITYSURVEYNO varchar(100) DEFAULT NULL,
  add column LOGICAL_PROP_NO varchar(100);
