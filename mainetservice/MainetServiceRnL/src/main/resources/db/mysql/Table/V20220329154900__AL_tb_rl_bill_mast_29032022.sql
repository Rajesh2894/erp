--liquibase formatted sql
--changeset Kanchan:V20220329154900__AL_tb_rl_bill_mast_29032022.sql
alter table tb_rl_bill_mast add column partial_paid_flag char(1) NULL DEFAULT 'N';
