--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_26072021.sql
alter table tb_as_assesment_mast add column ADDRESS_REG VARCHAR(1000) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_260720211.sql
alter table tb_as_assesment_mast add column AREA_NAME_REG VARCHAR(100) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_260720212.sql
alter table TB_AS_PRO_ASSESMENT_MAST add column ADDRESS_REG VARCHAR(1000) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_260720213.sql
alter table TB_AS_PRO_ASSESMENT_MAST add column AREA_NAME_REG VARCHAR(100) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_260720214.sql
alter table TB_AS_ASSESMENT_MAST_HIST add column ADDRESS_REG VARCHAR(1000) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726101810__AL_tb_as_assesment_mast_260720215.sql
alter table TB_AS_ASSESMENT_MAST_HIST add column AREA_NAME_REG VARCHAR(100) NULL DEFAULT NULL;
