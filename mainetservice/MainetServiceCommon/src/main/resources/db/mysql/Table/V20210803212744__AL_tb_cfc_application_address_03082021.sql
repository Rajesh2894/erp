--liquibase formatted sql
--changeset Kanchan:V20210803212744__AL_tb_cfc_application_address_03082021.sql
ALTER TABLE tb_cfc_application_address MODIFY COLUMN APA_AREANM VARCHAR(1500);     
--liquibase formatted sql
--changeset Kanchan:V20210803212744__AL_tb_cfc_application_address_030820211.sql
ALTER TABLE tb_cfc_application_add_hist MODIFY COLUMN APA_AREANM VARCHAR(1500);
