--liquibase formatted sql
--changeset Kanchan:V20210409194254__AL_tb_as_assesment_owner_dtl_09042021.sql
alter table tb_as_assesment_owner_dtl modify column MN_asso_guardian_name varchar(500) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210409194254__AL_tb_as_assesment_owner_dtl_090420211.sql
alter table tb_as_transfer_owner_dtl modify column GUARDIAN_NAME varchar(500) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210409194254__AL_tb_as_assesment_owner_dtl_090420212.sql
alter table tb_as_pro_assesment_owner_dtl modify column pro_asso_guardian_name varchar(500) NULL DEFAULT NULL;
