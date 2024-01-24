--liquibase formatted sql
--changeset PramodPatil:V20230911101222__AL_mm_purchaseorder_11092023.sql
ALTER TABLE mm_purchaseorder MODIFY workorderid bigint(12) null default null;