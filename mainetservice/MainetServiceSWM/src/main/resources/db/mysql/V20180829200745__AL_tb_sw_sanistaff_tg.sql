--liquibase formatted sql
--changeset nilima:V20180829200745__AL_tb_sw_sanistaff_tg.sql
alter table tb_sw_sanistaff_tg change column SAN_TYPE SAN_TYPE bigint(12) null comment' Target Type ';
alter table tb_sw_sanistaff_tgdet change column EMPID VEHICLE_ID bigint(12) NOT NULL COMMENT 'VEHICLE ID';
alter table tb_sw_sanistaff_tgdet_hist change column EMPID VEHICLE_ID bigint(12) NOT NULL COMMENT 'VEHICLE ID';
rename table tb_sw_sanistaff_tg to tb_sw_vehicle_tg;
rename table tb_sw_sanistaff_tg_hist to tb_sw_vehicle_tg_hist;
rename table tb_sw_sanistaff_tgdet to tb_sw_vehicle_tgdet;
rename table tb_sw_sanistaff_tgdet_hist to tb_sw_vehicle_tgdet_hist;
