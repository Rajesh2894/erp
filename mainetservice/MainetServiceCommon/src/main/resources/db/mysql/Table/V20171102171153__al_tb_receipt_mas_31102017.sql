--liquibase formatted sql
--changeset nilima:V20171102171153__al_tb_receipt_mas_31102017.sql
ALTER TABLE tb_receipt_mas
CHANGE COLUMN MANUALRECEIPTNO MANUALRECEIPTNO VARCHAR(50) NULL DEFAULT NULL COMMENT 'Manual Receipt no' ;
