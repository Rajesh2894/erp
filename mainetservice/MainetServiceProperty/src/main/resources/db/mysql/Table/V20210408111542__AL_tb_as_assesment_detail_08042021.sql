--liquibase formatted sql
--changeset Kanchan:V20210408111542__AL_tb_as_assesment_detail_08042021.sql
alter table tb_as_assesment_detail add column MN_FLAT_NO varchar(30) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210408111542__AL_tb_as_assesment_detail_080420211.sql
alter table tb_as_prop_det add column PD_FLAT_NO varchar(30) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210408111542__AL_tb_as_assesment_detail_080420212.sql
alter table TB_AS_PRO_ASSESMENT_DETAIL add column PRO_FLAT_NO varchar(30) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210408111542__AL_tb_as_assesment_detail_080420213.sql
alter table tb_as_pro_detail_hist add column PRO_FLAT_NO varchar(30) NULL default NULL;

