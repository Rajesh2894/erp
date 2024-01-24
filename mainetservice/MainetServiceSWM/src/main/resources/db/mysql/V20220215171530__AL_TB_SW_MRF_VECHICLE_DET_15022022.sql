--liquibase formatted sql
--changeset Kanchan:V20220215171530__AL_TB_SW_MRF_VECHICLE_DET_15022022.sql
alter table TB_SW_MRF_VECHICLE_DET modify column VE_VETYPE bigint (12) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215171530__AL_TB_SW_MRF_VECHICLE_DET_150220221.sql
alter table TB_SW_MRF_VECHICLE_DET modify column MRFV_AVALCNT bigint (12) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215171530__AL_TB_SW_MRF_VECHICLE_DET_150220222.sql
alter table TB_SW_MRF_VECHICLE_DET modify column MRFV_REQCNT bigint (12) NULL DEFAULT NULL;
