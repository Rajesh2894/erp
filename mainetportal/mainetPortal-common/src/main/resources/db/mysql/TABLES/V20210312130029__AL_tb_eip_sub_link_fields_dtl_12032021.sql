--liquibase formatted sql
--changeset Kanchan:V20210312130029__AL_tb_eip_sub_link_fields_dtl_12032021.sql
alter table tb_eip_sub_link_fields_dtl add column is_highlighted char(1);
--liquibase formatted sql
--changeset Kanchan:V20210312130029__AL_tb_eip_sub_link_fields_dtl_120320211.sql
alter table tb_eip_sub_link_fields_dtl_hist add column is_highlighted char(1);
