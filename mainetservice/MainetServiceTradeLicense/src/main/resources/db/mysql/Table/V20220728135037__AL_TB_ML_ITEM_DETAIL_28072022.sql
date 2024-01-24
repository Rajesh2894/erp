--liquibase formatted sql
--changeset Kanchan:V20220728135037__AL_TB_ML_ITEM_DETAIL_28072022.sql
Alter table TB_ML_ITEM_DETAIL modify column TRD_UNIT Decimal(15,2) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220728135037__AL_TB_ML_ITEM_DETAIL_280720221.sql
Alter table TB_ML_ITEM_DETAIL_HIST modify column TRD_UNIT Decimal(15,2) null default null;