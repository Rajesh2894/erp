--liquibase formatted sql
--changeset Kanchan:V20221226171052__AL_Tb_Echallan_Itemdet_26122022.sql
alter table Tb_Echallan_Itemdet modify column ITEM_QUANT bigint(10) null default null;