--liquibase formatted sql
--changeset Anil:V20200817180514__AL_tb_eip_sub_link_field_map_17082020.sql
ALTER TABLE tb_eip_sub_link_field_map
ADD COLUMN DD_OPT_EN VARCHAR(4000) NULL DEFAULT NULL AFTER SUB_SECTION_TYPE;
--liquibase formatted sql
--changeset Anil:V20200817180514__AL_tb_eip_sub_link_field_map_170820201.sql
ALTER TABLE tb_eip_sub_link_field_map
ADD COLUMN DD_OPT_REG VARCHAR(4000) NULL DEFAULT NULL AFTER DD_OPT_EN;
--liquibase formatted sql
--changeset Anil:V20200817180514__AL_tb_eip_sub_link_field_map_170820202.sql
ALTER TABLE tb_eip_sub_link_field_map_hist
ADD COLUMN DD_OPT_EN VARCHAR(4000) NULL DEFAULT NULL AFTER H_STATUS;
--liquibase formatted sql
--changeset Anil:V20200817180514__AL_tb_eip_sub_link_field_map_170820203.sql
ALTER TABLE tb_eip_sub_link_field_map_hist
ADD COLUMN DD_OPT_REG VARCHAR(4000) NULL DEFAULT NULL AFTER DD_OPT_EN;
