--liquibase formatted sql
--changeset Kanchan:V20210401093145__AL_tb_as_assesment_mast_01042021.sql
alter table tb_as_assesment_mast add column MN_SPL_NOT_DUE_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210401093145__AL_tb_as_assesment_mast_010420211.sql
alter table TB_AS_PRO_ASSESMENT_MAST add column PRO_SPL_NOT_DUE_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210401093145__AL_tb_as_assesment_mast_010420212.sql
alter table TB_AS_PRO_MAST_HIST add column PRO_SPL_NOT_DUE_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210401093145__AL_tb_as_assesment_mast_010420213.sql
alter table TB_AS_PRO_ASSESMENT_MAST modify column PRO_AREA_NAME varchar(100) NULL DEFAULT NULL;
