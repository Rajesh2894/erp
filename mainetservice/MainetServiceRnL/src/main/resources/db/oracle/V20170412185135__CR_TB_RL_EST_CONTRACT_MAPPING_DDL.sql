--liquibase formatted sql
--changeset nilima:V20170412185135__CR_TB_RL_EST_CONTRACT_MAPPING_DDL.sql
create table TB_RL_EST_CONTRACT_MAPPING
(
  cont_est_mapid      NUMBER(12) not null,
  cont_id             NUMBER(12) not null,
  es_id               NUMBER(12) not null,
  prop_id             NUMBER(12) not null,
  cont_map_aut_status CHAR(1),
  cont_map_aut_by     NUMBER(12),
  cont_map_aut_date   DATE,
  orgid               NUMBER(12) not null,
  created_by          NUMBER(12) not null,
  lang_id             NUMBER(7),
  created_date        DATE not null,
  updated_by          NUMBER(12),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100) not null,
  lg_ip_mac_upd       VARCHAR2(100),
  cont_map_active     CHAR(1) default 'Y' not null
);
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_est_mapid
  is 'Contract Estate Map Id (primary key)';
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_id
  is 'contract id Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_RL_EST_CONTRACT_MAPPING.es_id
  is 'Estate Id Foregin Key (tb_RL_estate_mas)';
comment on column TB_RL_EST_CONTRACT_MAPPING.prop_id
  is 'Propety Id Foregin Key(TB_PROPERTY_MAST)';
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_map_aut_status
  is 'Contract Mapping authorisation status';
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_map_aut_by
  is 'Contract Mapping authorisation by (empid)';
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_map_aut_date
  is 'Contract Mapping authorisation date';
comment on column TB_RL_EST_CONTRACT_MAPPING.orgid
  is 'orgnisation id';
comment on column TB_RL_EST_CONTRACT_MAPPING.created_by
  is 'user identity';
comment on column TB_RL_EST_CONTRACT_MAPPING.lang_id
  is 'language identity';
comment on column TB_RL_EST_CONTRACT_MAPPING.created_date
  is 'Created Date';
comment on column TB_RL_EST_CONTRACT_MAPPING.updated_by
  is 'user id who update the data';
comment on column TB_RL_EST_CONTRACT_MAPPING.updated_date
  is 'date on which data is going to update';
comment on column TB_RL_EST_CONTRACT_MAPPING.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_RL_EST_CONTRACT_MAPPING.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_RL_EST_CONTRACT_MAPPING.cont_map_active
  is 'Contract Mapping (Active->''Y''/Inactive,''N'') ';
alter table TB_RL_EST_CONTRACT_MAPPING
  add constraint PK_CONT_EST_MAP primary key (CONT_EST_MAPID);
alter table TB_RL_EST_CONTRACT_MAPPING
  add constraint FK_MAP_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
alter table TB_RL_EST_CONTRACT_MAPPING
  add constraint FK_MAP_ES_ID foreign key (ES_ID)
  references TB_RL_ESTATE_MAS (ES_ID);
alter table TB_RL_EST_CONTRACT_MAPPING
  add constraint FK_MAP_PROP_ID foreign key (PROP_ID)
  references TB_RL_PROPERTY_MAS (PROP_ID);
