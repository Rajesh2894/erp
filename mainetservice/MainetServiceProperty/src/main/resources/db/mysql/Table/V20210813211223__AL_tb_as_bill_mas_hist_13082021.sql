--liquibase formatted sql
--changeset Kanchan:V20210813211223__AL_tb_as_bill_mas_hist_13082021.sql
alter table tb_as_bill_mas_hist add column  group_prop_no varchar(20) NOT NULL AFTER demand_rebate ;
--liquibase formatted sql
--changeset Kanchan:V20210813211223__AL_tb_as_bill_mas_hist_130820211.sql
alter table tb_as_bill_mas_hist add column parent_prop_no varchar(20) NOT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20210813211223__AL_tb_as_bill_mas_hist_130820212.sql
alter table tb_as_bill_mas_hist add column GROUP_MN_NO varchar(16) NOT NULL ;
