--liquibase formatted sql
--changeset Kanchan:V20230605191703__AL_Tb_Sfac_Sale_Info_Detail_05062023.sql
alter table Tb_Sfac_Sale_Info_Detail
add column TYPE_OF_SALE1 BigInt (20) null default null,
add column TYPE_OF_SALE2 BigInt (20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230605191703__AL_Tb_Sfac_Sale_Info_Detail_050620231.sql
alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast
add column IS_VEHICLE_OWN_CHECK varchar (10) null default null;