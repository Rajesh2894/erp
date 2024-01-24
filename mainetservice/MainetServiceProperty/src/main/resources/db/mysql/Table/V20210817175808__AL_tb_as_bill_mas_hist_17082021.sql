--liquibase formatted sql
--changeset Kanchan:V20210817175808__AL_tb_as_bill_mas_hist_17082021.sql
alter table tb_as_bill_mas_hist modify column group_prop_no varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210817175808__AL_tb_as_bill_mas_hist_170820211.sql
alter table tb_as_bill_mas_hist modify column parent_prop_no varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210817175808__AL_tb_as_bill_mas_hist_170820212.sql
alter table tb_as_bill_mas_hist modify column GROUP_MN_NO varchar(16) NUll;
