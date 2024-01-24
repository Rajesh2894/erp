--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_01112021.sql
alter table TB_SWD_SCHEME_MAST add column FAMILY_DET_REQ varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120211.sql
alter table TB_SWD_SCHEME_MAST add column RESOLUTION_NO varchar(60) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120212.sql
alter table TB_SWD_SCHEME_MAST add column RESOLUTION_DATE date NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120213.sql
alter table tb_swd_scheme_mast_hist add column FAMILY_DET_REQ varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120214.sql
alter table tb_swd_scheme_mast_hist add column RESOLUTION_NO varchar(60) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120215.sql
alter table tb_swd_scheme_mast_hist add column RESOLUTION_DATE date NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120216.sql
alter table TB_SWD_SCHEME_DET modify column SDSCH_SHARING_PER decimal(12,2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20211101180406__AL_TB_SWD_SCHEME_MAST_011120217.sql
alter table TB_SWD_SCHEME_DET_HIST modify column SDSCH_SHARING_PER decimal(12,2) null default null;
