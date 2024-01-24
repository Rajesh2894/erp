--liquibase formatted sql
--changeset Kanchan:V20210726115932__AL_TB_ML_ITEM_DETAIL_26072021.sql
alter table TB_ML_ITEM_DETAIL  add column  LICENSE_FEE decimal(15,2),add DEPOSIT_AMT  decimal(15,2) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726115932__AL_TB_ML_ITEM_DETAIL_260720211.sql
alter table TB_ML_ITEM_DETAIL_HIST  add column LICENSE_FEE decimal(15,2),add DEPOSIT_AMT  decimal(15,2) NULL;
