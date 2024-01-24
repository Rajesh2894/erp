create table TB_RL_EST_CONTRACT_MAPPING
( cont_est_mapid      BIGINT(12) not null comment 'Contract Estate Map Id (primary key)',
  cont_id             BIGINT(12) not null comment 'contract id Foregin Key (TB_CONTRACT_MAST)',
  es_id               BIGINT(12) not null comment 'Estate Id Foregin Key (tb_RL_estate_mas)',
  prop_id             BIGINT(12) not null comment 'Propety Id Foregin Key(TB_PROPERTY_MAST)',
  cont_map_aut_status CHAR(1) comment 'Contract Mapping authorisation status',
  cont_map_aut_by     BIGINT(12) comment 'Contract Mapping authorisation by (empid)',
  cont_map_aut_date   DATETIME comment 'Contract Mapping authorisation date',
  orgid               BIGINT(12) not null comment 'orgnisation id',
  created_by          BIGINT(12) not null comment 'user identity',
  created_date        DATETIME not null comment 'Created Date',
  updated_by          BIGINT(12) comment 'user id who update the data',
  updated_date        DATETIME comment 'date on which data is going to update',
  lg_ip_mac           VARCHAR(100) not null comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd       VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address',
  cont_map_active     CHAR(1) default 'Y' not null comment 'Contract Mapping (Active->''Y''/Inactive,''N'') ' );

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