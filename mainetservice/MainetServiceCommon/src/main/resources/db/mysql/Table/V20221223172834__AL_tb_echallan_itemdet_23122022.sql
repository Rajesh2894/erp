--liquibase formatted sql
--changeset Kanchan:V20221223172834__AL_tb_echallan_itemdet_23122022.sql
ALTER TABLE tb_echallan_itemdet DROP PRIMARY KEY,ADD PRIMARY KEY (`ITEM_ID`);

