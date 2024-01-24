--liquibase formatted sql
--changeset Kanchan:V20230417210235__AL_MM_EXPIRED_17042023.sql
ALTER TABLE MM_EXPIRED add column  scrapno varchar(20),
add scrapdate datetime,
add initiator bigint(12),
add vendorid bigint(12),
add workorderid bigint(12),
add disposeddate datetime,
add paymentflag char(1),
add receiptamt double(12,2),
add mode varchar(10),
add bankid bigint(12),
add instrumentno bigint(12),
add instrumentdate datetime,
add instrumentamt double(12,2);