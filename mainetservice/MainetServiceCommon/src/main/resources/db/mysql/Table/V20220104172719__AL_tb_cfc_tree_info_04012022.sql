--liquibase formatted sql
--changeset Kanchan:V20220104172719__AL_tb_cfc_tree_info_04012022.sql
alter table tb_cfc_tree_info add column tree_cut_type bigint(12) DEFAULT NULL;
