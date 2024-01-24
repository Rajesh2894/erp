-- Create table
create table TB_EIP_SUB_LINK_FIELDS_DTL
(
  sub_link_field_dtl_id  NUMBER not null,
  sub_links_mas_id       NUMBER not null,
  txt_01_en              VARCHAR2(150),
  txt_01_rg              VARCHAR2(150),
  txt_02_en              VARCHAR2(150),
  txt_02_rg              VARCHAR2(150),
  txt_03_en              VARCHAR2(150),
  txt_03_rg              VARCHAR2(150),
  txt_04_en              VARCHAR2(150),
  txt_04_rg              VARCHAR2(150),
  txt_05_en              VARCHAR2(150),
  txt_05_rg              VARCHAR2(150),
  txt_06_en              VARCHAR2(150),
  txt_06_rg              VARCHAR2(150),
  txt_07_en              VARCHAR2(150),
  txt_07_rg              VARCHAR2(150),
  txt_08_en              VARCHAR2(150),
  txt_08_rg              VARCHAR2(150),
  txt_09_en              VARCHAR2(150),
  txt_10_en              VARCHAR2(150),
  txt_10_rg              VARCHAR2(150),
  txt_11_en              VARCHAR2(150),
  txt_11_rg              VARCHAR2(150),
  txt_12_en              VARCHAR2(150),
  txt_12_rg              VARCHAR2(150),
  txta_01_en             NVARCHAR2(2000),
  txta_01_rg             NVARCHAR2(2000),
  txta_02_en             NVARCHAR2(2000),
  txta_02_rg             NVARCHAR2(2000),
  att_01                 VARCHAR2(1000),
  att_02                 VARCHAR2(1000),
  date_01                DATE,
  date_02                DATE,
  date_03                DATE,
  date_04                DATE,
  isdeleted              CHAR(1) default 'N',
  txt_09_rg              VARCHAR2(150),
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7) not null,
  lang_id                NUMBER not null,
  created_date           DATE not null,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100) not null,
  lg_ip_mac_upd          VARCHAR2(100),
  profile_img_path       VARCHAR2(1000),
  sub_link_field_dtl_ord NUMBER(10,2),
  txt_03_en_number       NUMBER(12),
  txt_03_en_blob         BLOB
);
-- Add comments to the columns 
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.sub_links_mas_id
  is 'Foreign key column of TB_SUB_LINKS_MASTER';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_01_en
  is 'Text form field 01 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_01_rg
  is 'Text form field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_02_en
  is 'Text form field 02 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_02_rg
  is 'Text form field 02 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_03_en
  is 'Text form field 03 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_03_rg
  is 'Text form field 03 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_04_en
  is 'Text form field 04 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_04_rg
  is 'Text form field 04 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_05_en
  is 'Text form field 05 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_05_rg
  is 'Text form field 05 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_06_en
  is 'Text form field 06 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_06_rg
  is 'Text form field 06 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_07_en
  is 'Text form field 07 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_07_rg
  is 'Text form field 07 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_08_en
  is 'Text form field 08 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_08_rg
  is 'Text form field 08 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_09_en
  is 'Text form field 09 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_10_en
  is 'Text form field 10 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_10_rg
  is 'Text form field 10 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_11_en
  is 'Text form field 11 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_11_rg
  is 'Text form field 11 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_12_en
  is 'Text form field 12 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_12_rg
  is 'Text form field 12 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_01_en
  is 'Text area field 01 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_01_rg
  is 'Text area field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_02_en
  is 'Text area field 02 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_02_rg
  is 'Text area field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.att_01
  is 'Attachment field 01';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.att_02
  is 'Attachment field 02';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_01
  is 'Date field 1';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_02
  is 'Date field 2';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_03
  is 'Date field 3';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_04
  is 'Date field 4';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.isdeleted
  is 'Record deleted flag status';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_09_rg
  is 'Text form field 09 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.user_id
  is 'User Id who insert new record';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint PK_FIELD_DTL_ID primary key (SUB_LINK_FIELD_DTL_ID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD1838B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD18418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD186078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK_LINKS_FIELD_MAS_ID foreign key (SUB_LINKS_MAS_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;
