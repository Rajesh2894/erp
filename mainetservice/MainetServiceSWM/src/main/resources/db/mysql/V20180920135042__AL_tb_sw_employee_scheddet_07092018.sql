--liquibase formatted sql
--changeset nilima:V20180920135042__AL_tb_sw_employee_scheddet_07092018.sql
ALTER TABLE tb_sw_employee_scheddet
DROP FOREIGN KEY FK_SCHROID;

--liquibase formatted sql
--changeset nilima:V20180920135042__AL_tb_sw_employee_scheddet_070920181.sql
ALTER TABLE tb_sw_employee_scheddet
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL ,
ADD INDEX FK_BEAT_ID_idx(BEAT_ID ASC),
DROP INDEX FK_ROID_idx;

--liquibase formatted sql
--changeset nilima:V20180920135042__AL_tb_sw_employee_scheddet_070920182.sql
ALTER TABLE tb_sw_employee_scheddet
ADD CONSTRAINT FK_BEAT_ID
  FOREIGN KEY (BEAT_ID)
  REFERENCES tb_sw_beat_mast(BEAT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;