--liquibase formatted sql
--changeset Kanchan:V20220728172921__AL_TB_SW_MRF_EMPLOYEE_DET_28072022.sql
Alter table TB_SW_MRF_EMPLOYEE_DET modify column DSGID bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220728172921__AL_TB_SW_MRF_EMPLOYEE_DET_280720221.sql
Alter table TB_SW_MRF_EMPLOYEE_DET modify column MRFE_AVALCNT bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220728172921__AL_TB_SW_MRF_EMPLOYEE_DET_280720222.sql
Alter table TB_SW_MRF_EMPLOYEE_DET modify column MRFE_REQCNT bigint(12) null default null;