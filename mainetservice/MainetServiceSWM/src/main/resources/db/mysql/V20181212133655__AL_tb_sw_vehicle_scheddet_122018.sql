--liquibase formatted sql
--changeset nilima:V20181212133655__AL_tb_sw_vehicle_scheddet_122018.sql
ALTER TABLE tb_sw_vehicle_scheddet 
DROP FOREIGN KEY FK_RO_ID;

--liquibase formatted sql
--changeset nilima:V20181212133655__AL_tb_sw_vehicle_scheddet_1220181.sql
ALTER TABLE tb_sw_vehicle_scheddet 
ADD INDEX FK_RO_ID_idx (BEAT_ID ASC),
DROP INDEX FK_RO_ID_idx ;

--liquibase formatted sql
--changeset nilima:V20181212133655__AL_tb_sw_vehicle_scheddet_1220182.sql
ALTER TABLE tb_sw_vehicle_scheddet 
ADD CONSTRAINT FK_RO_ID
  FOREIGN KEY (BEAT_ID)
  REFERENCES tb_sw_beat_mast (BEAT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
