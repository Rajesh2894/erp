--liquibase formatted sql
--changeset PramodPatil:V20231219135306__AL_mm_transactions_19122023.sql
ALTER TABLE mm_transactions modify openingbal double(14,2) null default null,
 modify uom bigint(12) null default null , modify closingbal double(14,2) null default null ,
 modify unitprice double(14,2) null default null , modify transctionamt double(14,2) null default null ,
 modify mfgdate double(14,2) null default null ;