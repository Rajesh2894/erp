--liquibase formatted sql
--changeset PramodPatil:V20240122095400__AL_TB_BPMS_DEV_REG_22012024.sql
ALTER TABLE TB_BPMS_DEV_REG ADD Firm_Name varchar(100) NULL DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20240122095400__AL_TB_BPMS_DEV_REG_220120241.sql
ALTER TABLE TB_BPMS_DEV_REG ADD company_details_API_Flag char(1) NULL DEFAULT NULL;