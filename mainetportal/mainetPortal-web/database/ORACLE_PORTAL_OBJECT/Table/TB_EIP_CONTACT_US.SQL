-- Create table
create table TB_EIP_CONTACT_US
(
  contact_id           NUMBER(19) not null,
  address1_en          VARCHAR2(500),
  address1_reg         NVARCHAR2(1000),
  address2_en          VARCHAR2(500),
  address2_reg         NVARCHAR2(1000),
  city_en              VARCHAR2(200),
  city_reg             NVARCHAR2(400),
  cname_en             VARCHAR2(100),
  cname_reg            NVARCHAR2(200),
  country_en           VARCHAR2(100),
  country_reg          VARCHAR2(200),
  created_date         DATE,
  designation_en       VARCHAR2(100),
  designation_reg      NVARCHAR2(200),
  email1               VARCHAR2(100),
  email2               VARCHAR2(100),
  faxno1_en            VARCHAR2(15),
  faxno1_reg           NVARCHAR2(30),
  faxno2_en            VARCHAR2(15),
  isdeleted            VARCHAR2(1),
  lg_ip_mac            VARCHAR2(600),
  lg_ip_mac_upd        VARCHAR2(250),
  org_id               NUMBER(19) not null,
  pincode_en           VARCHAR2(10),
  pincode_mr           VARCHAR2(20),
  remarks_en           VARCHAR2(100),
  state_en             VARCHAR2(100),
  telno1_en            VARCHAR2(15),
  telno2_en            VARCHAR2(15),
  telno3_en            VARCHAR2(15),
  updated_by           NUMBER(19),
  updated_date         DATE,
  userid               NUMBER(19),
  faxno2_reg           NVARCHAR2(30),
  remarks_reg          NVARCHAR2(200),
  state_reg            VARCHAR2(200),
  telno1_reg           NVARCHAR2(30),
  telno2_reg           NVARCHAR2(30),
  telno3_reg           VARCHAR2(30),
  dept_en              VARCHAR2(100),
  dept_reg             VARCHAR2(100),
  flag                 VARCHAR2(20),
  muncipality_name     VARCHAR2(100),
  sequence_no          NUMBER(3),
  muncipality_name_reg VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_EIP_CONTACT_US.contact_id
  is 'Contact Id (PK)';
comment on column TB_EIP_CONTACT_US.address1_en
  is 'Address1 in english';
comment on column TB_EIP_CONTACT_US.address1_reg
  is 'Address1 in regional';
comment on column TB_EIP_CONTACT_US.address2_en
  is 'Address2 in english';
comment on column TB_EIP_CONTACT_US.address2_reg
  is 'Address2 in regional.';
comment on column TB_EIP_CONTACT_US.city_en
  is 'City in english';
comment on column TB_EIP_CONTACT_US.city_reg
  is 'City in regional';
comment on column TB_EIP_CONTACT_US.cname_en
  is 'Name of contact person in english';
comment on column TB_EIP_CONTACT_US.cname_reg
  is 'Name of contact person in regional';
comment on column TB_EIP_CONTACT_US.country_en
  is 'Country in English';
comment on column TB_EIP_CONTACT_US.country_reg
  is 'Country in regional';
comment on column TB_EIP_CONTACT_US.created_date
  is 'Created Date';
comment on column TB_EIP_CONTACT_US.designation_en
  is 'Designation in english';
comment on column TB_EIP_CONTACT_US.designation_reg
  is 'Designation in regional';
comment on column TB_EIP_CONTACT_US.email1
  is 'E-mail address 1';
comment on column TB_EIP_CONTACT_US.email2
  is 'E-mail address 1';
comment on column TB_EIP_CONTACT_US.faxno1_en
  is 'Fax no 1 in English';
comment on column TB_EIP_CONTACT_US.faxno1_reg
  is 'Fax no 1 in regional ';
comment on column TB_EIP_CONTACT_US.faxno2_en
  is 'Fax no 2 in English';
comment on column TB_EIP_CONTACT_US.isdeleted
  is 'Deleted the ''Y'' and if not deleted then ''N''';
comment on column TB_EIP_CONTACT_US.lg_ip_mac
  is 'For user name,Ip address, Machine Physical address';
comment on column TB_EIP_CONTACT_US.lg_ip_mac_upd
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_CONTACT_US.org_id
  is 'Foreign Key';
comment on column TB_EIP_CONTACT_US.pincode_en
  is 'Pincode in english';
comment on column TB_EIP_CONTACT_US.pincode_mr
  is 'Pincode in regional ';
comment on column TB_EIP_CONTACT_US.remarks_en
  is 'Remark in english';
comment on column TB_EIP_CONTACT_US.state_en
  is 'State In English';
comment on column TB_EIP_CONTACT_US.telno1_en
  is 'Telephone no1 in english';
comment on column TB_EIP_CONTACT_US.telno2_en
  is 'Telephone no2 in english';
comment on column TB_EIP_CONTACT_US.telno3_en
  is 'Telephone no2 in english';
comment on column TB_EIP_CONTACT_US.updated_by
  is 'Modified By';
comment on column TB_EIP_CONTACT_US.updated_date
  is 'Modification Date';
comment on column TB_EIP_CONTACT_US.userid
  is 'UserId';
comment on column TB_EIP_CONTACT_US.faxno2_reg
  is 'Fax no 2 in regional ';
comment on column TB_EIP_CONTACT_US.remarks_reg
  is 'Remark in regional ';
comment on column TB_EIP_CONTACT_US.state_reg
  is 'State In regional ';
comment on column TB_EIP_CONTACT_US.telno1_reg
  is 'Telephone no1 in regional ';
comment on column TB_EIP_CONTACT_US.telno2_reg
  is 'Telephone no2 in regional ';
comment on column TB_EIP_CONTACT_US.telno3_reg
  is 'Telephone no2 in regional ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_CONTACT_US
  add constraint EIP_CONTACT_US_KP primary key (CONTACT_ID)
  using index 
  tablespace MAINET_DATA
  pctfree 10
  initrans 2
  maxtrans 255;
alter table TB_EIP_CONTACT_US
  add constraint FKA98B6F21187DF1D0 foreign key (USERID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_CONTACT_US
  add constraint FKA98B6F2138B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_CONTACT_US
  add constraint FK_EIP_CONTACTUS_ORG_ID foreign key (ORG_ID)
  references TB_ORGANISATION (ORGID);
