--liquibase formatted sql
--changeset Kanchan:V20201110180840__AL_tb_eip_public_notices_10112020.sql
ALTER TABLE tb_eip_public_notices MODIFY COLUMN ImagePath VARCHAR(2000) NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20201110180840__AL_tb_eip_public_notices_101120201.sql
ALTER TABLE tb_eip_public_notices_hist MODIFY COLUMN IMAGEPATH VARCHAR(2000) NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Kanchan:V20201110180840__AL_tb_eip_public_notices_101120202.sql
ALTER TABLE tb_eip_announcement MODIFY COLUMN IMAGE VARCHAR(2000) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20201110180840__AL_tb_eip_public_notices_101120203.sql
ALTER TABLE tb_eip_announcement_hist MODIFY COLUMN IMAGE VARCHAR(2000) NULL DEFAULT NULL ;

