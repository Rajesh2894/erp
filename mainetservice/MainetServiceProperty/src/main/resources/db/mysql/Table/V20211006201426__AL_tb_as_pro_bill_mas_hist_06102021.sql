--liquibase formatted sql
--changeset Kanchan:V20211006201426__AL_tb_as_pro_bill_mas_hist_06102021.sql
alter table tb_as_pro_bill_mas_hist add column Assd_alv decimal(15,2) NULL after Assd_std_rate ;
