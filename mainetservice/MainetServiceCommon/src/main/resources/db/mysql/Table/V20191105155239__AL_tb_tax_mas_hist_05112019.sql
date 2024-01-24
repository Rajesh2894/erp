--liquibase formatted sql
--changeset Anil:V20191105155239__AL_tb_tax_mas_hist_05112019.sql
ALTER TABLE tb_tax_mas_hist CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NULL;

