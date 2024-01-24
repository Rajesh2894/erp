--liquibase formatted sql
--changeset Kanchan:V20210813211737__AL_tb_as_pro_bill_mas_13082021.sql
alter table tb_as_pro_bill_mas  add column  group_prop_no varchar(20) NOT NULL AFTER demand_rebate ;
--liquibase formatted sql
--changeset Kanchan:V20210813211737__AL_tb_as_pro_bill_mas_130820211.sql
alter table tb_as_pro_bill_mas  add column parent_prop_no varchar(20) NOT NULL AFTER  group_prop_no ;
--liquibase formatted sql
--changeset Kanchan:V20210813211737__AL_tb_as_pro_bill_mas_130820212.sql
alter table tb_as_pro_bill_mas  add column GROUP_MN_NO varchar(16) NOT NULL AFTER parent_prop_no ;
