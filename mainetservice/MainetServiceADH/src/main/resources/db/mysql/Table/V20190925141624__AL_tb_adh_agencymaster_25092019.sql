--liquibase formatted sql
--changeset Anil:V20190925141624__AL_tb_adh_agencymaster_25092019.sql
ALTER TABLE tb_adh_agencymaster 
ADD COLUMN cancellation_Date DATE NULL AFTER LG_IP_MAC_UPD,
ADD COLUMN cancellation_reason VARCHAR(200) NULL AFTER cancellation_Date;
--liquibase formatted sql
--changeset Anil:V20190925141624__AL_tb_adh_agencymaster_250920191.sql
ALTER TABLE tb_adh_agencymaster_hist
ADD COLUMN cancellation_Date DATE NULL AFTER LG_IP_MAC_UPD,
ADD COLUMN cancellation_reason VARCHAR(200) NULL AFTER cancellation_Date;
