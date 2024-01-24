--liquibase formatted sql
--changeset Kanchan:V20220222145833__AL_tb_collectionmaster_22022022.sql
alter table tb_collectionmaster add column cm_easytap_dev_id varchar(100) NULL DEFAULT NULL;
