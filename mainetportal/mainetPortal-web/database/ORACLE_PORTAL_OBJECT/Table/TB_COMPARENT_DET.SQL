-- Create table
create table TB_COMPARENT_DET
(
  cod_id        NUMBER(12) not null,
  com_id        NUMBER(12) not null,
  cod_desc      NVARCHAR2(200) not null,
  cod_value     NVARCHAR2(200) not null,
  parent_id     NUMBER(12),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_default   CHAR(1) default 'N',
  cod_status    NVARCHAR2(1) default 'Y',
  cod_desc_mar  NVARCHAR2(400),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the table 
comment on table TB_COMPARENT_DET
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP. THIS IS A CHILD TABLE';
-- Add comments to the columns 
comment on column TB_COMPARENT_DET.cod_id
  is 'Primary Key';
comment on column TB_COMPARENT_DET.com_id
  is 'COM ID OF TB_COMPARENT_DET TABLE(FK)';
comment on column TB_COMPARENT_DET.cod_desc
  is 'DESCRIPTION OF CHILD';
comment on column TB_COMPARENT_DET.cod_value
  is 'REGIONAL DESCRIPTION OF CHILD';
comment on column TB_COMPARENT_DET.parent_id
  is 'PARENT ID IF PRESENT TO RELATES EACH RECORD WITH OTHERS';
comment on column TB_COMPARENT_DET.orgid
  is 'ORGANISATION ID';
comment on column TB_COMPARENT_DET.user_id
  is 'USER ID';
comment on column TB_COMPARENT_DET.lang_id
  is 'LANGUAGE ID';
comment on column TB_COMPARENT_DET.lmoddate
  is 'CREATION DATE';
comment on column TB_COMPARENT_DET.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_COMPARENT_DET.updated_date
  is 'UPDATION DATE';
comment on column TB_COMPARENT_DET.cod_status
  is 'Status of the record';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARENT_DET
  add constraint PK_PDCODID primary key (COD_ID);
alter table TB_COMPARENT_DET
  add constraint FK_PDCODID foreign key (COM_ID)
  references TB_COMPARENT_MAS (COM_ID);
alter table TB_COMPARENT_DET
  add constraint FK_PDPRNTID foreign key (PARENT_ID)
  references TB_COMPARENT_DET (COD_ID);
