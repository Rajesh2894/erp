--liquibase formatted sql
--changeset Anil:V20200729101908__AL_tb_wt_penalty_hist_29072020.sql
ALTER TABLE tb_wt_penalty_hist ADD COLUMN bill_gen_amount DECIMAL(15,2) NULL;

