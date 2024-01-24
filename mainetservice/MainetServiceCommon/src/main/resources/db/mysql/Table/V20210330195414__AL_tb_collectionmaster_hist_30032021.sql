--liquibase formatted sql
--changeset Kanchan:V20210330195414__AL_tb_collectionmaster_hist_30032021.sql
altre table tb_collectionmaster_hist add column H_STATUS varchar(2) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210330195414__AL_tb_collectionmaster_hist_300320211.sql
altre table tb_countermaster_hist add column H_STATUS varchar(2) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210330195414__AL_tb_collectionmaster_hist_300320212.sql
altre table tb_counterschedule_hist add column H_STATUS varchar(2) NULL;
