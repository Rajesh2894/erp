--liquibase formatted sql
--changeset Kanchan:V20230622201447__AL_mm_invoice_22062023.sql
ALTER TABLE mm_invoice ADD invoiceNo varchar(20) AFTER invoiceid;