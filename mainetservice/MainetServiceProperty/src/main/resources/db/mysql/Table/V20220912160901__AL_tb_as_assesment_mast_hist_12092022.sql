--liquibase formatted sql
--changeset Kanchan:V20220912160901__AL_tb_as_assesment_mast_hist_12092022.sql
alter table tb_as_assesment_mast_hist add column new_house_no varchar(50) null default null;