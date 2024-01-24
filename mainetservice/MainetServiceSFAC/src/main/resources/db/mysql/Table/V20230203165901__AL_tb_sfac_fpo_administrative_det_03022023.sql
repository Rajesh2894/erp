--liquibase formatted sql
--changeset Kanchan:V20230203165901__AL_tb_sfac_fpo_administrative_det_03022023.sql
Alter table tb_sfac_fpo_administrative_det change column NAME_OF_BOARD WHETHER_BOARD_MEMBER bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230203165901__AL_tb_sfac_fpo_administrative_det_030220231.sql
Alter table tb_sfac_fpo_administrative_det change column CIN_NO DIN varchar(50) null default null;