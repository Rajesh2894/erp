--liquibase formatted sql
--changeset nilima:V20180920144206__AL_tb_sw_vehicle_scheddet_07092018.sql
ALTER TABLE tb_sw_vehicle_scheddet
DROP FOREIGN KEY FK_RO_ID;

--liquibase formatted sql
--changeset nilima:V20180920144206__AL_tb_sw_vehicle_scheddet_070920181.sql
ALTER TABLE tb_sw_vehicle_scheddet
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NOT NULL COMMENT 'FK tb_sw_Beat_mast' ,
ADD COLUMN EMPID BIGINT(12) NULL AFTER BEAT_ID;
--liquibase formatted sql
--changeset nilima:V20180920144206__AL_tb_sw_vehicle_scheddet_070920182.sql
ALTER TABLE tb_sw_vehicle_scheddet
ADD CONSTRAINT FK_RO_ID
  FOREIGN KEY (BEAT_ID)
  REFERENCES tb_sw_route_mast(RO_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
--liquibase formatted sql
--changeset nilima:V20180920144206__AL_tb_sw_vehicle_scheddet_070920183.sql
ALTER TABLE tb_sw_vehicle_scheddet_hist
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK tb_sw_Beat_mast' ,
ADD COLUMN EMPID BIGINT(12) NULL COMMENT 'Employee Id' AFTER LG_IP_MAC_UPD;
