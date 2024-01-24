--liquibase formatted sql
--changeset PramodPatil:V20230921145950_AL_mm_mdn_items_entry_21092023.sql
ALTER TABLE mm_mdn_items_entry Add acceptqty double(10,2) null default null after quantity, Add rejectqty double(10,2) null default null after quantity;

--liquibase formatted sql
--changeset PramodPatil:V20230921145950_AL_mm_mdn_items_entry_210920231.sql
ALTER TABLE mm_mdn_items_entry DROP COLUMN decision;