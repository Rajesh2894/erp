--liquibase formatted sql
--changeset PramodPatil:V20231219153706__AL_tb_sfac_farmer_mast_19122023.sql
alter table tb_sfac_farmer_mast add column FIG_GRP bigint(20) null;
