--liquibase formatted sql
--changeset Anil:V20200213143519__AL_tb_ml_owner_detail_hist_13022020.sql
ALTER TABLE tb_ml_owner_detail_hist DROP FOREIGN KEY FK_TRDO_H;
--liquibase formatted sql
--changeset Anil:V20200213143519__AL_tb_ml_owner_detail_hist_130220201.sql
ALTER TABLE tb_ml_owner_detail_hist DROP INDEX FK_TRDO_idx;
