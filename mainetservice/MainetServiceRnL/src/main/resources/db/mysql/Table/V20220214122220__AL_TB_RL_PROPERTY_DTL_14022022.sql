--liquibase formatted sql
--changeset Kanchan:V20220214122220__AL_TB_RL_PROPERTY_DTL_14022022.sql
alter table TB_RL_PROPERTY_DTL modify column PROP_AREA decimal(12,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220214122220__AL_TB_RL_PROPERTY_DTL_140220221.sql
alter table TB_RL_PROPTY_AMINITYFACILITY add column REMARKS varchar(500) NULL DEFAULT NULL;
