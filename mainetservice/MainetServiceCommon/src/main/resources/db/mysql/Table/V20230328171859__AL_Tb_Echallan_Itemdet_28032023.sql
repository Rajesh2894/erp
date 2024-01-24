--liquibase formatted sql
--changeset Kanchan:V20230328171859__AL_Tb_Echallan_Itemdet_28032023.sql
alter table Tb_Echallan_Itemdet add column ITEM_NAME varchar(50) not null;
--liquibase formatted sql
--changeset Kanchan:V20230328171859__AL_Tb_Echallan_Itemdet_280320231.sql
alter table Tb_Echallan_Itemdet modify column ITEM_DESC varchar(500) null default null;