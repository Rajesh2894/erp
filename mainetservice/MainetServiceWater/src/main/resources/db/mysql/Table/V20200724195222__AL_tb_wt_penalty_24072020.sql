--liquibase formatted sql
--changeset Anil:V20200724195222__AL_tb_wt_penalty_24072020.sql
ALTER TABLE tb_wt_penalty ADD COLUMN bill_gen_amount DECIMAL(15,2) NULL;

