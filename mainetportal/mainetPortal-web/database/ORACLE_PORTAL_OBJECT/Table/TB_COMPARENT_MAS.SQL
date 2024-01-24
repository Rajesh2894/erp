-- Create table
create table TB_COMPARENT_MAS
(
  com_id             NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  com_desc           NVARCHAR2(200) not null,
  com_value          NVARCHAR2(200) not null,
  com_level          NUMBER(3) not null,
  com_status         NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  com_desc_mar       NVARCHAR2(400),
  com_replicate_flag CHAR(1) default 'N',
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100)
);
-- Add comments to the table 
comment on table TB_COMPARENT_MAS
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP';
-- Add comments to the columns 
comment on column TB_COMPARENT_MAS.com_id
  is 'PRIMARY KEY';
comment on column TB_COMPARENT_MAS.cpm_id
  is 'CPM ID OF TB_COMPARAM_MAS TABLE';
comment on column TB_COMPARENT_MAS.com_desc
  is 'DESCRIPTION OF PARENT';
comment on column TB_COMPARENT_MAS.com_value
  is 'SHORT DESCRIPTION OR PREFIX OF PARENT';
comment on column TB_COMPARENT_MAS.com_level
  is 'LEVEL ON WHICH EACH RECORD RELATES TO EACH OTHER';
comment on column TB_COMPARENT_MAS.com_status
  is 'Record status';
comment on column TB_COMPARENT_MAS.orgid
  is 'ORGANISATION ID';
comment on column TB_COMPARENT_MAS.user_id
  is 'USER ID';
comment on column TB_COMPARENT_MAS.lang_id
  is 'LANGUAGE ID';
comment on column TB_COMPARENT_MAS.lmoddate
  is 'CREATION DATE';
comment on column TB_COMPARENT_MAS.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_COMPARENT_MAS.updated_date
  is 'UPDATION DATE';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARENT_MAS
  add constraint PK_PDCOMID primary key (COM_ID);
alter table TB_COMPARENT_MAS
  add constraint FK_CPMID foreign key (CPM_ID)
  references TB_COMPARAM_MAS (CPM_ID);
