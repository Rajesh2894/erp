--liquibase formatted sql
--changeset nilima:V20170419114615__AL_TB_WT_DEMAND_NOTICE.sql
alter table TB_WT_DEMAND_NOTICE drop column lang_id;
alter table TB_WT_DEMAND_NOTICE modify orgid NUMBER(12);
alter table TB_WT_DEMAND_NOTICE rename column user_id to CREATED_BY;
alter table TB_WT_DEMAND_NOTICE rename column lmoddate to CREATED_DATE;
alter table TB_WT_DEMAND_NOTICE rename column is_deleted to NB_ACTIVE;
alter table TB_WT_DEMAND_NOTICE add tax_amount NUMBER(15,2);
comment on column TB_WT_DEMAND_NOTICE.tax_amount
  is 'Tax Amount';
