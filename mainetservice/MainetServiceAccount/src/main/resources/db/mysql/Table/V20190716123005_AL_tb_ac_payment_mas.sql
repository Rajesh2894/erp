--liquibase formatted sql
--changeset nilima:V20190716123005_AL_tb_ac_payment_mas1.sql
alter table tb_ac_payment_mas change column `DISCREPANCY_FLAG` `FIELDID` BIGINT(12) DEFAULT NULL COMMENT 'Update from Reconciliation form  - if discrepancy is found update "Y" else default "N"';
--liquibase formatted sql
--changeset nilima:V20190716123005_AL_tb_ac_payment_mas2.sql
alter table tb_ac_payment_mas change column `DISCREPANCYDETAILS` `DPAYBILLREF_NO` varchar(1000) DEFAULT NULL COMMENT 'Update from Reconciliation form - if discrepancy is found then enter discrepancy details.';
  