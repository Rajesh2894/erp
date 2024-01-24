--liquibase formatted sql
--changeset Anil:V20190806155730__AL_tb_adh_agencymaster_hist_06082019.sql
ALTER TABLE tb_adh_agencymaster_hist
ADD COLUMN H_STATUS VARCHAR(2) NULL AFTER LG_IP_MAC_UPD;
--liquibase formatted sql
--changeset Anil:V20190806155730__AL_tb_adh_agencymaster_hist_060820191.sql
ALTER TABLE tb_adh_agencymaster_hist
ADD COLUMN AGN_ID_H BIGINT(12) NOT NULL AFTER AGN_ID,
DROP PRIMARY KEY,
ADD PRIMARY KEY (AGN_ID_H);


