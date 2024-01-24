--liquibase formatted sql
--changeset Kanchan:V20201210202053__AL_tb_eip_sub_link_field_map_10122020.sql
ALTER TABLE tb_eip_sub_link_field_map
MODIFY COLUMN ORDER_NO DECIMAL(10,2) NULL DEFAULT '0' ;
--liquibase formatted sql
--changeset Kanchan:V20201210202053__AL_tb_eip_sub_link_field_map_101220201.sql
ALTER TABLE tb_eip_sub_link_field_map_hist
MODIFY COLUMN ORDER_NO DECIMAL(10,2) NULL DEFAULT '0' ;
