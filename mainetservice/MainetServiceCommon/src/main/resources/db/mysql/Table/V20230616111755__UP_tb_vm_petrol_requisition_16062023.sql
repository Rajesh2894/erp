--liquibase formatted sql
--changeset Kanchan:V20230616111755__UP_tb_vm_petrol_requisition_16062023.sql
SET SQL_SAFE_UPDATES=0;
--liquibase formatted sql
--changeset Kanchan:V20230616111755__UP_tb_vm_petrol_requisition_160620231.sq
update tb_vm_petrol_requisition x set VE_ID=(select distinct VE_ID from TB_VEHICLE_MAST where VE_CHASIS_SRNO=x.VE_CHASIS_SRNO) where VE_ID IS NULL and VE_CHASIS_SRNO is not null;