--liquibase formatted sql
--changeset Kanchan:V20220919161123__AL_tb_vm_vehicle_log_book_19092022.sql
alter table tb_vm_vehicle_log_book add column End_FuelInLitre double(15,2) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919161123__AL_tb_vm_vehicle_log_book_190920221.sql
alter table tb_vm_vehicle_log_book modify column day_Start_Meter_Read  double(15,1) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220919161123__AL_tb_vm_vehicle_log_book_190920222.sql
alter table tb_vm_vehicle_log_book modify column day_End_Meter_Read double(15,1) DEFAULT NULL;