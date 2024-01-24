--liquibase formatted sql
--changeset PramodPatil:V20231110155840__MO_MM_INDENT_10112023.sql
ALTER TABLE MM_INDENT MODIFY beneficiary varchar(250) null default null;