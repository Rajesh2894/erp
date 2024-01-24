--liquibase formatted sql
--changeset Kanchan:V20210317110315__AL_tb_as_assesment_mast_17032021.sql
alter table tb_as_assesment_mast add column
MN_REFPROPNO varchar(100) DEFAULT NULL,
  add MN_ASSNTDT datetime DEFAULT NULL,
  add MN_LSTASSNTDT datetime DEFAULT NULL,
  add MN_REVISE_ASSNTDT datetime DEFAULT NULL,
  add CPD_BILLMETH bigint(12) DEFAULT NULL,
  add MN_BILL_CNG_REASON  varchar(100) DEFAULT NULL,
  add CPD_SURVEYTYP bigint(12) DEFAULT NULL,
  add MN_ADDN_SURVEYNO varchar(100) DEFAULT NULL,
  add MN_AALINO varchar(100) DEFAULT NULL,
  add MN_CHAALTANO varchar(100) DEFAULT NULL,
  add MN_AREA_NAME varchar(100) DEFAULT NULL,
  add MN_CLUSTER_NO bigint(12) DEFAULT NULL,
  add MN_CLU_BUILDING_NO bigint(12) DEFAULT NULL,
  add MN_URB_LOC_GRP varchar(100) DEFAULT NULL,
  add MN_ADDN_CITYSURVEYNO varchar(100) DEFAULT NULL;
