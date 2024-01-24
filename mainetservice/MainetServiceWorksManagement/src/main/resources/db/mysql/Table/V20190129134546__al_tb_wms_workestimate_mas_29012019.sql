--liquibase formatted sql
--changeset nilima:V20190129134546__al_tb_wms_workestimate_mas_29012019.sql
ALTER TABLE tb_wms_workestimate_mas
ADD COLUMN WORK_PID BIGINT(12) NULL AFTER WORK_ID;

--liquibase formatted sql
--changeset nilima:V20190129134546__al_tb_wms_workestimate_mas_290120191.sql
ALTER TABLE tb_wms_workestimate_mas_hist
ADD COLUMN WORK_PID BIGINT(12) NULL AFTER WORK_ID;