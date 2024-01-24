--liquibase formatted sql
--changeset Kanchan:V20210610122718__AL_tb_ml_item_detail_10062021.sql
alter table tb_ml_item_detail add column APM_APPLICATION_ID bigint(16) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610122718__AL_tb_ml_item_detail_100620211.sql
alter table tb_ml_owner_detail add column APM_APPLICATION_ID bigint(16) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610122718__AL_tb_ml_item_detail_100620212.sql
alter table tb_ml_item_detail_hist add column APM_APPLICATION_ID bigint(16) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610122718__AL_tb_ml_item_detail_100620213.sql
alter table tb_ml_owner_detail_hist add column APM_APPLICATION_ID bigint(16) NULL;
