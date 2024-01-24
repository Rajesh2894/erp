--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET1.sql
alter table tb_wt_bill_det change column WT_V1 rule_id varchar(200) null comment 'BRMS RULE ID';
--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET2.sql
alter table tb_wt_bill_det change column WT_N1 base_rate decimal(15,2) null comment 'BRMS BASE RATE';
--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET3.sql
alter table tb_wt_bill_det_hist change column WT_V1 rule_id varchar(200) null comment 'BRMS RULE ID';
--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET4.sql
alter table tb_wt_bill_det_hist change column WT_N1 base_rate decimal(15,2) null comment 'BRMS BASE RATE';
--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET5.sql
ALTER TABLE TB_CSMR_INFO CHANGE COLUMN PM_PRMSTID PM_PROP_NO VARCHAR(20) NULL COMMENT 'ID OF TB_PROP_MAS TABLE';
--liquibase formatted sql
--changeset nilima:V20180920200108_AL_TB_WT_BILL_DET6.sql
ALTER TABLE TB_CSMR_INFO_HIST CHANGE COLUMN PM_PRMSTID PM_PROP_NO VARCHAR(20) NULL COMMENT 'ID OF TB_PROP_MAS TABLE';



