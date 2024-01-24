--liquibase formatted sql
--changeset PramodPatil:V20231221103150__AL_mm_transactions_21122023.sql
ALTER TABLE mm_transactions modify mfgdate date null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231221103150__AL_mm_transactions_211220231.sql
ALTER TABLE mm_transactions modify binlocation bigint(12) null default null;

