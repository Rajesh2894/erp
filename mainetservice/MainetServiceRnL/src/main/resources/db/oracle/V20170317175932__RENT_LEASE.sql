--changeset nilima:V20170329182413__TB_AC_BILL_MAS_DDL.sql
create table TB_RL_ESTATE_MAS
(
  es_id                NUMBER(12) not null,
  loc_id               NUMBER(12) not null,
  es_code              NVARCHAR2(14) not null,
  es_oldestate_no      NVARCHAR2(30),
  es_name_eng          NVARCHAR2(1000) not null,
  es_address           NVARCHAR2(1000) not null,
  es_type              CHAR(1) not null,
  cod_id1_ety          NUMBER(12) not null,
  cod_id2_ety          NUMBER(12),
  es_reg_number        NVARCHAR2(30),
  es_regn_date         DATE,
  asset_id             NVARCHAR2(30),
  es_construction_date DATE,
  es_completion_date   DATE,
  es_no_of_floors      NUMBER(3),
  es_no_of_basement    NUMBER(3),
  es_remarks           NVARCHAR2(1000),
  es_gis_id            VARCHAR2(20),
  es_area              NUMBER(18,2),
  es_active            CHAR(1) default 'N' not null,
  orgid                NUMBER(12) not null,
  langid               NUMBER(8) not null,
  lmoddate             DATE not null,
  user_id              NUMBER(12) not null,
  updated_by           NUMBER(12),
  updated_date         DATE,
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  cpd_id_res           NUMBER(12),
  es_name_reg          NVARCHAR2(1000),
  es_nature_land       NUMBER(12) not null,
  es_survey_no         NVARCHAR2(25) not null
);
comment on column TB_RL_ESTATE_MAS.es_id
  is 'estate id primary key ( no organisation reset)';
comment on column TB_RL_ESTATE_MAS.loc_id
  is 'Location Id FK key ( no organisation reset)';
comment on column TB_RL_ESTATE_MAS.es_code
  is 'estate no. for estate ( Auto generated)(''EST''+''ORGID''+0000+Sequence number)';
comment on column TB_RL_ESTATE_MAS.es_oldestate_no
  is 'old estate/land number if exist';
comment on column TB_RL_ESTATE_MAS.es_name_eng
  is 'estate Name English';
comment on column TB_RL_ESTATE_MAS.es_address
  is 'estate address';
comment on column TB_RL_ESTATE_MAS.es_type
  is 'Estate Category(Single/Group)';
comment on column TB_RL_ESTATE_MAS.cod_id1_ety
  is 'estate type  (Hirarchey Prefix->ETY)';
comment on column TB_RL_ESTATE_MAS.cod_id2_ety
  is 'estate sub type (Hirarchey Prefix->ETY)';
comment on column TB_RL_ESTATE_MAS.es_reg_number
  is 'estate registration no';
comment on column TB_RL_ESTATE_MAS.es_regn_date
  is 'estate registration date';
comment on column TB_RL_ESTATE_MAS.asset_id
  is 'Asset Number';
comment on column TB_RL_ESTATE_MAS.es_construction_date
  is 'Estate Constrution Date';
comment on column TB_RL_ESTATE_MAS.es_completion_date
  is 'Estate Completion Date';
comment on column TB_RL_ESTATE_MAS.es_no_of_floors
  is 'no of floors';
comment on column TB_RL_ESTATE_MAS.es_no_of_basement
  is 'no of Basement';
comment on column TB_RL_ESTATE_MAS.es_remarks
  is 'remark';
comment on column TB_RL_ESTATE_MAS.es_gis_id
  is 'gis id';
comment on column TB_RL_ESTATE_MAS.es_area
  is 'estate area';
comment on column TB_RL_ESTATE_MAS.es_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_RL_ESTATE_MAS.orgid
  is 'orgnisation id';
comment on column TB_RL_ESTATE_MAS.langid
  is 'Langid';
comment on column TB_RL_ESTATE_MAS.lmoddate
  is 'creation date';
comment on column TB_RL_ESTATE_MAS.user_id
  is 'user id';
comment on column TB_RL_ESTATE_MAS.updated_by
  is 'updated by';
comment on column TB_RL_ESTATE_MAS.updated_date
  is 'updated date & time';
comment on column TB_RL_ESTATE_MAS.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_RL_ESTATE_MAS.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_RL_ESTATE_MAS.cpd_id_res
  is 'a-available, i-issued ';
comment on column TB_RL_ESTATE_MAS.es_name_reg
  is 'estate Name Regional';
comment on column TB_RL_ESTATE_MAS.es_nature_land
  is 'Nature of Land (Agricultural land,Non agricultural land,Forest land) Prefix->NOL';
comment on column TB_RL_ESTATE_MAS.es_survey_no
  is 'City Survey No.';
alter table TB_RL_ESTATE_MAS
  add constraint PK_ES_ID primary key (ES_ID);
alter table TB_RL_ESTATE_MAS
  add constraint FK_LOC_ID_ES foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

prompt
prompt Creating table TB_RL_PROPERTY_MAS
prompt =================================
prompt
create table TB_RL_PROPERTY_MAS
(
  prop_id           NUMBER(12) not null,
  es_id             NUMBER(12) not null,
  pm_prmstid        NUMBER(12),
  pm_propno         NVARCHAR2(30) not null,
  pm_oldpropno      NVARCHAR2(30),
  prop_name         NVARCHAR2(1000),
  prop_unit_number  NUMBER(12),
  prop_occupancy    NUMBER(12) not null,
  prop_usage        NUMBER(12) not null,
  prop_floor        NUMBER(12),
  prop_roadtype     NUMBER(12) not null,
  prop_gis_id       VARCHAR2(20),
  prop_court_case   CHAR(1),
  prop_stop_billing CHAR(1),
  prop_status       CHAR(1) not null,
  prop_total_area   NUMBER(20,2),
  orgid             NUMBER(12) not null,
  langid            NUMBER(8),
  user_id           NUMBER(12) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  prop_flg          CHAR(1) not null,
  prop_security_dep NUMBER(15,2),
  prop_no           NVARCHAR2(30),
  prop_latitude     NVARCHAR2(50),
  prop_longitude    NVARCHAR2(50)
)
;
comment on column TB_RL_PROPERTY_MAS.prop_id
  is 'Property Id primary key';
comment on column TB_RL_PROPERTY_MAS.es_id
  is 'estate id fk ';
comment on column TB_RL_PROPERTY_MAS.pm_prmstid
  is 'property id fk of property master of Assessment';
comment on column TB_RL_PROPERTY_MAS.pm_propno
  is 'Property Number (Auto generated)(Estate No+''PT''+sequence no)';
comment on column TB_RL_PROPERTY_MAS.pm_oldpropno
  is 'Old Property Number';
comment on column TB_RL_PROPERTY_MAS.prop_name
  is 'Property name';
comment on column TB_RL_PROPERTY_MAS.prop_unit_number
  is 'Unit Number/Shop Number';
comment on column TB_RL_PROPERTY_MAS.prop_occupancy
  is 'Occupancy Type Prefix (''ROC'')';
comment on column TB_RL_PROPERTY_MAS.prop_usage
  is 'Usage type Hirarchey Prefix(''USA'')(Property prefix)-consider first level';
comment on column TB_RL_PROPERTY_MAS.prop_floor
  is 'floor   Prefix (''IDE'')(Property Prefix)';
comment on column TB_RL_PROPERTY_MAS.prop_roadtype
  is 'Road Type  Prefix(''RFT'')(Property Prefix)';
comment on column TB_RL_PROPERTY_MAS.prop_gis_id
  is 'Gis Id';
comment on column TB_RL_PROPERTY_MAS.prop_court_case
  is 'Court Case  (Court Case =''Y'' )';
comment on column TB_RL_PROPERTY_MAS.prop_stop_billing
  is 'Stop Billing  (Stop Billing =''Y'')';
comment on column TB_RL_PROPERTY_MAS.prop_status
  is 'Status of Property (''Y''-> Active,''N''-> Inactive)';
comment on column TB_RL_PROPERTY_MAS.prop_total_area
  is 'Total Property Area';
comment on column TB_RL_PROPERTY_MAS.orgid
  is 'organization id';
comment on column TB_RL_PROPERTY_MAS.langid
  is 'language id 1- english,2-regional';
comment on column TB_RL_PROPERTY_MAS.user_id
  is 'user id who created the record';
comment on column TB_RL_PROPERTY_MAS.created_date
  is 'record creation date';
comment on column TB_RL_PROPERTY_MAS.updated_by
  is 'user id who updated the record';
comment on column TB_RL_PROPERTY_MAS.updated_date
  is 'user id who updated the record';
comment on column TB_RL_PROPERTY_MAS.lg_ip_mac
  is 'machine ip address from where user has created the record';
comment on column TB_RL_PROPERTY_MAS.lg_ip_mac_upd
  is 'machine ip address from where user has updated the record';
comment on column TB_RL_PROPERTY_MAS.prop_flg
  is 'O->original,D->uploaded';
comment on column TB_RL_PROPERTY_MAS.prop_security_dep
  is 'Security Deposite in case of Occupancy Type "Rent"';
comment on column TB_RL_PROPERTY_MAS.prop_no
  is 'Property number from Tax & Revenu or Enterable field';
comment on column TB_RL_PROPERTY_MAS.prop_latitude
  is 'Lattitude';
comment on column TB_RL_PROPERTY_MAS.prop_longitude
  is 'Logitude';
alter table TB_RL_PROPERTY_MAS
  add constraint PK_PROP_ID primary key (PROP_ID);
alter table TB_RL_PROPERTY_MAS
  add constraint FK_ES_ID foreign key (ES_ID)
  references TB_RL_ESTATE_MAS (ES_ID);
alter table TB_RL_PROPERTY_MAS
  add constraint FK_PM_PRMSTID foreign key (PM_PRMSTID, ORGID)
  references TB_PROP_MAS (PM_PRMSTID, ORGID)
  disable;

prompt
prompt Creating table TB_RL_ESTATE_MAS_HIST
prompt ====================================
prompt
create table TB_RL_ESTATE_MAS_HIST
(
  es_id_hist           NUMBER(12) not null,
  es_id                NUMBER(12) not null,
  loc_id               NUMBER(12) not null,
  es_code              NVARCHAR2(30),
  es_oldestate_no      NVARCHAR2(30),
  es_name_eng          NVARCHAR2(1000) not null,
  es_address           NVARCHAR2(1000) not null,
  es_type              CHAR(1) not null,
  cod_id1_ety          NUMBER(12) not null,
  cod_id2_ety          NUMBER(12),
  es_reg_number        NVARCHAR2(30),
  es_regn_date         DATE,
  asset_id             NUMBER(30),
  es_construction_date DATE,
  es_completion_date   DATE,
  es_no_of_floors      NUMBER(3),
  es_no_of_basement    NUMBER(3),
  es_remarks           NVARCHAR2(1000),
  es_gis_id            VARCHAR2(20),
  es_area              NUMBER(18,2),
  es_active            CHAR(1) default 'N' not null,
  orgid                NUMBER(12) not null,
  langid               NUMBER(8) not null,
  lmoddate             DATE not null,
  user_id              NUMBER(12) not null,
  updated_by           NUMBER(12),
  updated_date         DATE,
  lg_ip_mac            VARCHAR2(100) not null,
  lg_ip_mac_upd        VARCHAR2(100),
  cpd_id_res           NUMBER(12),
  es_name_reg          NVARCHAR2(1000),
  h_flag               VARCHAR2(2),
  updel_date           DATE,
  updel_userid         NUMBER(12)
)
;
comment on column TB_RL_ESTATE_MAS_HIST.es_id
  is 'estate id primary key ( no organisation reset)';
comment on column TB_RL_ESTATE_MAS_HIST.loc_id
  is 'Location Id FK key ( no organisation reset)';
comment on column TB_RL_ESTATE_MAS_HIST.es_code
  is 'estate no. for estate ( Auto generated)(''EM''+''ORGID''+0000+Sequence number)';
comment on column TB_RL_ESTATE_MAS_HIST.es_oldestate_no
  is 'old estate/land number if exist';
comment on column TB_RL_ESTATE_MAS_HIST.es_name_eng
  is 'estate Name English';
comment on column TB_RL_ESTATE_MAS_HIST.es_address
  is 'estate address';
comment on column TB_RL_ESTATE_MAS_HIST.es_type
  is 'Estate Category(Single/Group)';
comment on column TB_RL_ESTATE_MAS_HIST.cod_id1_ety
  is 'estate type  (Hirarchey Prefix->ETY)';
comment on column TB_RL_ESTATE_MAS_HIST.cod_id2_ety
  is 'estate sub type (Hirarchey Prefix->ETY)';
comment on column TB_RL_ESTATE_MAS_HIST.es_reg_number
  is 'estate registration no';
comment on column TB_RL_ESTATE_MAS_HIST.es_regn_date
  is 'estate registration date';
comment on column TB_RL_ESTATE_MAS_HIST.asset_id
  is 'Asset Number';
comment on column TB_RL_ESTATE_MAS_HIST.es_construction_date
  is 'Estate Constrution Date';
comment on column TB_RL_ESTATE_MAS_HIST.es_completion_date
  is 'Estate Completion Date';
comment on column TB_RL_ESTATE_MAS_HIST.es_no_of_floors
  is 'no of floors';
comment on column TB_RL_ESTATE_MAS_HIST.es_no_of_basement
  is 'no of Basement';
comment on column TB_RL_ESTATE_MAS_HIST.es_remarks
  is 'remark';
comment on column TB_RL_ESTATE_MAS_HIST.es_gis_id
  is 'gis id';
comment on column TB_RL_ESTATE_MAS_HIST.es_area
  is 'estate area';
comment on column TB_RL_ESTATE_MAS_HIST.es_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_RL_ESTATE_MAS_HIST.orgid
  is 'orgnisation id';
comment on column TB_RL_ESTATE_MAS_HIST.langid
  is 'Langid';
comment on column TB_RL_ESTATE_MAS_HIST.lmoddate
  is 'creation date';
comment on column TB_RL_ESTATE_MAS_HIST.user_id
  is 'user id';
comment on column TB_RL_ESTATE_MAS_HIST.updated_by
  is 'updated by';
comment on column TB_RL_ESTATE_MAS_HIST.updated_date
  is 'updated date & time';
comment on column TB_RL_ESTATE_MAS_HIST.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_RL_ESTATE_MAS_HIST.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_RL_ESTATE_MAS_HIST.cpd_id_res
  is 'a-available, i-issued ';
comment on column TB_RL_ESTATE_MAS_HIST.es_name_reg
  is 'estate Name Regional';
comment on column TB_RL_ESTATE_MAS_HIST.h_flag
  is 'Update/Delete Flag';
comment on column TB_RL_ESTATE_MAS_HIST.updel_date
  is 'Update/Delete Date';
comment on column TB_RL_ESTATE_MAS_HIST.updel_userid
  is 'Update/Delete Userid';
alter table TB_RL_ESTATE_MAS_HIST
  add constraint PK_ES_ID_HIST primary key (ES_ID_HIST);

prompt
prompt Creating table TB_RL_PROPERTY_DTL
prompt =================================
prompt
create table TB_RL_PROPERTY_DTL
(
  propd_id       NUMBER(12) not null,
  prop_id        NUMBER(12) not null,
  prop_area_type NUMBER(12) not null,
  orgid          NUMBER(12) not null,
  langid         NUMBER(8),
  user_id        NUMBER(12) not null,
  created_date   DATE not null,
  updated_by     NUMBER(12),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100) not null,
  lg_ip_mac_upd  VARCHAR2(100),
  prop_area      NUMBER(12,2) not null,
  prop_active    CHAR(1)
)
;
comment on column TB_RL_PROPERTY_DTL.prop_id
  is 'Property Id primary key';
comment on column TB_RL_PROPERTY_DTL.prop_area_type
  is 'Property Area Type (Prefix ART)';
comment on column TB_RL_PROPERTY_DTL.orgid
  is 'organization id';
comment on column TB_RL_PROPERTY_DTL.langid
  is 'language id 1- english,2-regional';
comment on column TB_RL_PROPERTY_DTL.user_id
  is 'user id who created the record';
comment on column TB_RL_PROPERTY_DTL.created_date
  is 'record creation date';
comment on column TB_RL_PROPERTY_DTL.updated_by
  is 'user id who updated the record';
comment on column TB_RL_PROPERTY_DTL.updated_date
  is 'user id who updated the record';
comment on column TB_RL_PROPERTY_DTL.lg_ip_mac
  is 'machine ip address from where user has created the record';
comment on column TB_RL_PROPERTY_DTL.lg_ip_mac_upd
  is 'machine ip address from where user has updated the record';
comment on column TB_RL_PROPERTY_DTL.prop_area
  is 'Property Area ';
comment on column TB_RL_PROPERTY_DTL.prop_active
  is 'Property Active';
alter table TB_RL_PROPERTY_DTL
  add constraint PK_PROPD_ID primary key (PROPD_ID);
alter table TB_RL_PROPERTY_DTL
  add constraint FK_PROP_ID foreign key (PROP_ID)
  references TB_RL_PROPERTY_MAS (PROP_ID);


spool off
