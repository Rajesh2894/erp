--liquibase formatted sql
--changeset PramodPatil:V20230901122751__AL_mm_mdn_items_entry_31082023.sql
ALTER TABLE mm_mdn_items_entry MODIFY mfgdate date null default null, MODIFY receivedbinlocation bigint(12) null default null;