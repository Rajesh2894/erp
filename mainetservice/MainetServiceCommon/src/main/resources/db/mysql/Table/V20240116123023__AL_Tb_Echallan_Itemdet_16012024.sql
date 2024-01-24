--liquibase formatted sql
--changeset PramodPatil:V20240116123023__AL_Tb_Echallan_Itemdet_16012024.sql
ALTER TABLE Tb_Echallan_Itemdet MODIFY COLUMN ITEM_NO VARCHAR(30) null default null;
