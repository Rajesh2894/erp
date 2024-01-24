--liquibase formatted sql
--changeset Kanchan:V20221223173504__AL_Tb_Echallan_Itemdet_23122022.sql
alter table Tb_Echallan_Itemdet modify column ITEM_NO varchar(10) null default null;
--liquibase formatted sql
--changeset Kanchan:V20221223173504__AL_Tb_Echallan_Itemdet_231220221.sql
alter table Tb_Echallan_Itemdet modify column STORE_ID varchar(30) null default null;