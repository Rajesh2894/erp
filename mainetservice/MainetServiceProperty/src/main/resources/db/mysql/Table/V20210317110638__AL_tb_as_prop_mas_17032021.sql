--liquibase formatted sql
--changeset Kanchan:V20210317110638__AL_tb_as_prop_mas_17032021.sql
alter table  tb_as_prop_mas add column
PM_REFPROPNO varchar(12)  DEFAULT NULL,
add PM_ASSNTDT datetime DEFAULT NULL,
add PM_LSTASSNTDT datetime DEFAULT NULL,
add CPD_SURVEYTYP bigint(20) DEFAULT NULL,
add LM_ADDN_SURVEYNO varchar(100)  DEFAULT NULL,
add LM_ADDN_CITYSURVEYNO varchar(100)  DEFAULT NULL, 
add PM_AALINO varchar(15)  DEFAULT NULL,
add PM_CHAALTANO varchar(12)  DEFAULT NULL,
add PM_AREA_NAME varchar(100)  DEFAULT NULL,
add PM_CLUSTER_NO bigint(20) DEFAULT NULL,
add PM_CLU_BUILDING_NO bigint(20) DEFAULT NULL,
add PM_REVISE_ASSNTDT datetime DEFAULT NULL,
add PM_BILL_CNG_REASON varchar(100) DEFAULT NULL,
add PM_URB_LOC_GRP varchar(50) DEFAULT NULL;
