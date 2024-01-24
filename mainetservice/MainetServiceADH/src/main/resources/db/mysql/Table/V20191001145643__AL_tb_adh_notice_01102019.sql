--liquibase formatted sql
--changeset Anil:V20191001145643__AL_tb_adh_notice_01102019.sql
ALTER TABLE tb_adh_notice 
CHANGE COLUMN notice_type notice_type BIGINT(12) NOT NULL COMMENT 'notice type',
CHANGE COLUMN lic_id AGN_ID BIGINT(12) NOT NULL COMMENT 'lic id' ,
ADD COLUMN UPDATED_BY BIGINT(12) NULL AFTER lg_ip_mac,
ADD COLUMN UPDATED_DATE DATETIME NULL AFTER UPDATED_BY,
ADD COLUMN LG_IP_MAC_UPD VARCHAR(100) NULL AFTER UPDATED_DATE;
--liquibase formatted sql
--changeset Anil:V20191001145643__AL_tb_adh_notice_011020191.sql
ALTER TABLE tb_adh_notice ADD CONSTRAINT fk_AGN_ID FOREIGN KEY(AGN_ID) REFERENCES tb_adh_agencymaster(AGN_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
