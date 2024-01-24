-- Create table
create table TB_ORGANISATION
(
  orgid             NUMBER not null,
  o_nls_orgname     NVARCHAR2(50),
  user_id           NUMBER(7),
  lang_id           NUMBER(4),
  lmoddate          DATE,
  tds_accountno     NVARCHAR2(20),
  tds_circle        NVARCHAR2(20),
  tds_pan_gir_no    NVARCHAR2(20),
  org_tax_ded_name  NVARCHAR2(70),
  org_tax_ded_addr  NVARCHAR2(200),
  org_short_nm      NVARCHAR2(10),
  o_logo            LONG RAW,
  org_address       NVARCHAR2(200),
  o_nls_orgname_mar NVARCHAR2(100),
  org_address_mar   NVARCHAR2(200),
  updated_by        NUMBER(7),
  updated_date      DATE,
  app_start_date    DATE,
  esdt_date         DATE,
  org_status        NVARCHAR2(1),
  org_cpd_id        NUMBER(12),
  default_status    CHAR(1),
  org_cpd_id_div    NUMBER(12),
  org_cpd_id_ost    NUMBER(12),
  org_cpd_id_dis    NUMBER,
  org_email_id      NVARCHAR2(100),
  vat_circle        NVARCHAR2(200),
  vat_ded_name      NVARCHAR2(500)
);
-- Add comments to the columns 
comment on column TB_ORGANISATION.tds_accountno
  is 'TDS Account Number of Organsation. Used in TDS Certificates';
comment on column TB_ORGANISATION.tds_circle
  is 'TDS Circle of Organsation where Annual return is to be delivered. Used in TDS Certificates';
comment on column TB_ORGANISATION.tds_pan_gir_no
  is 'Organisation PAN/GIR No';
comment on column TB_ORGANISATION.org_tax_ded_name
  is 'Name of person against deduction tax';
comment on column TB_ORGANISATION.org_tax_ded_addr
  is 'Address of person against deduction tax';
comment on column TB_ORGANISATION.org_short_nm
  is 'Short name which may use as abbrivation';
comment on column TB_ORGANISATION.org_cpd_id_div
  is 'Division';
comment on column TB_ORGANISATION.org_cpd_id_ost
  is 'Organisation Sub-Type';
comment on column TB_ORGANISATION.org_cpd_id_dis
  is 'cpdId';
comment on column TB_ORGANISATION.org_email_id
  is 'emailId';
comment on column TB_ORGANISATION.vat_circle
  is 'circle ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_ORGANISATION
  add constraint PK_ORGANISATION primary key (ORGID);
