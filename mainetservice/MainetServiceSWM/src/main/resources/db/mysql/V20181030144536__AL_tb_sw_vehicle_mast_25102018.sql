--liquibase formatted sql
--changeset nilima:V20181030144536__AL_tb_sw_vehicle_mast_25102018.sql
ALTER TABLE TB_SW_VEHICLE_MAST 
DROP COLUMN VE_WEUNIT,
DROP COLUMN VE_CAUNIT;

--liquibase formatted sql
--changeset nilima:V20181030144536__AL_tb_sw_vehicle_mast_251020181.sql
ALTER TABLE TB_SW_VEHICLE_MAST
ADD COLUMN VE_NO VARCHAR(15) NULL COMMENT 'Vechicle Number' AFTER `VE_REG_NO`;

--liquibase formatted sql
--changeset nilima:V20181030144536__AL_tb_sw_vehicle_mast_251020182.sql
ALTER TABLE TB_SW_VEHICLE_MAST_HIST
ADD COLUMN VE_NO VARCHAR(15) NULL COMMENT 'Vechicle Number' AFTER `VE_REG_NO`;

--liquibase formatted sql
--changeset nilima:V20181030144536__AL_tb_sw_vehicle_mast_251020183.sql
update tb_sw_vehicle_mast set ve_no=ve_reg_no;
commit;

--liquibase formatted sql
--changeset nilima:V20181030144536__AL_tb_sw_vehicle_mast_251020184.sql
update tb_sw_vehicle_mast set ve_reg_no='';
commit;
