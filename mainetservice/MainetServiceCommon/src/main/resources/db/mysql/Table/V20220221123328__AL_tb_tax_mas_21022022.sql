--liquibase formatted sql
--changeset Kanchan:V20220221123328__AL_tb_tax_mas_21022022.sql
alter table tb_tax_mas add column TAX_PRINT_ON4 varchar (100) NULL DEFAULT NULL;
