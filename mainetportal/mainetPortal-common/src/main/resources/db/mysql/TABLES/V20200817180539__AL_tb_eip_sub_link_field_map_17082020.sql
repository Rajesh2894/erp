--liquibase formatted sql
--changeset Anil:V20200817180539__AL_tb_eip_sub_link_field_map_17082020.sql
ALTER TABLE tb_eip_sub_link_field_map
ADD COLUMN ORDER_NO INT(5) NULL DEFAULT 1 AFTER DD_OPT_REG;
--liquibase formatted sql
--changeset Anil:V20200817180539__AL_tb_eip_sub_link_field_map_170820201.sql
ALTER TABLE tb_eip_sub_link_field_map_hist
ADD COLUMN ORDER_NO INT(5) NULL DEFAULT 1 AFTER DD_OPT_REG;
