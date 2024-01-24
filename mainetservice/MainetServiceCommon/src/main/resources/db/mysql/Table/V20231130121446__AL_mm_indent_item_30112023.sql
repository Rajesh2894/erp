--liquibase formatted sql
--changeset PramodPatil:V20231130121446__AL_mm_indent_item_30112023.sql
ALTER TABLE mm_indent_item modify quantity double(12,1);
