--liquibase formatted sql
--changeset Kanchan:V20230620204845__AL_mm_purchasereturn_20062023.sql
ALTER TABLE  mm_purchasereturn MODIFY returndate date; 