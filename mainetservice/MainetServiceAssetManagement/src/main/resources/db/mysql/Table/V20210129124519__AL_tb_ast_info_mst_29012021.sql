--liquibase formatted sql
--changeset Kanchan:V20210129124519__AL_tb_ast_info_mst_29012021.sql
alter table tb_ast_info_mst add  group_ref_id varchar(100) default null comment 'To Identify Unique Group';
--liquibase formatted sql
--changeset Kanchan:V20210129124519__AL_tb_ast_info_mst_290120211.sql
alter table tb_ast_info_mst_hist add group_ref_id varchar(100) default null comment 'To Identify Unique Group';
--liquibase formatted sql
--changeset Kanchan:V20210129124519__AL_tb_ast_info_mst_290120212.sql
alter table tb_ast_info_mst_rev add group_ref_id varchar(100) default null comment 'To Identify Unique Group';
