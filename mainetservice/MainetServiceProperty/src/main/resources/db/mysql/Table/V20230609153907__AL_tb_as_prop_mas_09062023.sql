--liquibase formatted sql
--changeset Kanchan:V20230609153907__AL_tb_as_prop_mas_09062023.sql
alter table tb_as_prop_mas modify column PM_PINCODE int(11) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230609153907__AL_tb_as_prop_mas_090620231.sql
alter table tb_as_prop_mas modify column PM_CORR_PINCODE int(11) null default null;