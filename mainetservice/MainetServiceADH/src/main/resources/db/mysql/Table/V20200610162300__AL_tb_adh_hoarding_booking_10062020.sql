--liquibase formatted sql
--changeset Anil:V20200610162300__AL_tb_adh_hoarding_booking_10062020.sql
ALTER TABLE tb_adh_hoarding_booking DROP FOREIGN KEY FK_ADH_ID_1;
--liquibase formatted sql
--changeset Anil:V20200610162300__AL_tb_adh_hoarding_booking_100620201.sql
ALTER TABLE tb_adh_hoarding_booking ADD INDEX FK_ADH_ID_1_idx (ADH_ID ASC),
DROP INDEX ADH_ID_idx ;
--liquibase formatted sql
--changeset Anil:V20200610162300__AL_tb_adh_hoarding_booking_100620202.sql
ALTER TABLE tb_adh_hoarding_booking ADD CONSTRAINT FK_ADH_ID_1 FOREIGN KEY (ADH_ID) REFERENCES tb_adh_mas (ADH_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;


