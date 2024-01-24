--liquibase formatted sql
--changeset Kanchan:V20230317202901__AL_tb_fm_complain_register_17032023.sql
ALTER TABLE tb_fm_complain_register Modify column assign_vehicle varchar(200) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230317202901__AL_tb_fm_complain_register_170320231.sql
ALTER TABLE tb_fm_complain_closure Modify column assign_vehicle varchar(200) null default null;