--liquibase formatted sql
--changeset Kanchan:V20221004182208__AL_TB_ORGANISATION_04102022.sql
Alter table TB_ORGANISATION
add column SDB_ID1 bigint(20) Null default null,
add column SDB_ID2 bigint(20) Null default null,
add column SDB_ID3 bigint(20) Null default null,
add column SDB_ID4 bigint(20) Null default null,
add column SDB_ID5 bigint(20) Null default null;