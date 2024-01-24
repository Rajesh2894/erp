--liquibase formatted sql
--changeset PramodPatil:V20231120161149__AL_MM_REQUISITION_DET_20112023.sql
ALTER TABLE MM_REQUISITION_DET modify quantity double(12,1);