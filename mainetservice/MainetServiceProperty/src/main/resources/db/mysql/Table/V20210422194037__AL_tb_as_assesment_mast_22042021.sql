--liquibase formatted sql
--changeset Kanchan:V20210422194037__AL_tb_as_assesment_mast_22042021.sql
alter table tb_as_assesment_mast add column MN_FLAT_NO varchar(30) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210422194037__AL_tb_as_assesment_mast_220420211.sql
alter table TB_AS_PRO_MAST_HIST add column PRO_FLAT_NO varchar(30) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210422194037__AL_tb_as_assesment_mast_220420212.sql
alter table TB_AS_PRO_ASSESMENT_MAST add column PRO_FLAT_NO varchar(30) NULL default NULL;
