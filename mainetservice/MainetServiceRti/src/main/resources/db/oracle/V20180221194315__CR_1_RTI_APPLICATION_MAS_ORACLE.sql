 CREATE TABLE TB_RTI_APPLICATION
 (RTI_ID NUMBER ,
  RTI_NO VARCHAR2(20),
  APM_APPLICATION_ID NUMBER,
  APM_APPLICATION_DATE DATE,
  SM_SERVICE_ID NUMBER,
  ORGID NUMBER NOT NULL,
  APPL_REFERENCE_MODE NUMBER,
  RTI_DEPTID NUMBER,
  RTI_LOCATION_ID NUMBER,
  RTI_SUBJECT NVARCHAR2(500),
  RTI_DETAILS NVARCHAR2(2000),
  LOIAPPLICABLE CHAR(5),
  REASON_FOR_LOI_NA NVARCHAR2(1000),
  FINALDISPATCHMODE NUMBER,
  DISPATCHDATE DATE,
  STAMP_NO VARCHAR2(50),
  STAMP_AMT NUMBER,
  STAMP_DOC_PATH VARCHAR2(500),
  RTI_ACTION NUMBER,
  REASON_ID NUMBER,
  RTI_REMARK NVARCHAR2(2000),
  PARTIAL_INFO_FLAG CHAR(1),
  INWARD_TYPE NUMBER,
  INW_REFERENCE_NO NVARCHAR2(100),
  INW_REFERENCE_DATE DATE,
  INW_AUTHORITY_DEPT NVARCHAR2(100),
  INW_AUTHORITY_NAME NVARCHAR2(100),
  INW_AUTHORITY_ADDRESS NVARCHAR2(1000),
  OTH_FORWARD_PIO_ADDRESS NVARCHAR2(1000),
  OTH_FORWARD_PIO_NAME NVARCHAR2(100),
  OTH_FORWARD_DEPT_NAME NVARCHAR2(100),
  OTH_FORWARD_PIO_EMAIL NVARCHAR2(100),
  OTH_FORWARD_PIO_MOB_NO NUMBER(10),
  LANG_ID NUMBER NOT NULL,
  USER_ID NUMBER NOT NULL,
  LMODDATE DATE NOT NULL,
  LG_IP_MAC VARCHAR2(100),
  UPDATED_BY NUMBER,
  UPDATED_DATE DATE,
  LG_IP_MAC_UPD VARCHAR2(100),
  ISDELETED NUMBER);
comment on column TB_RTI_APPLICATION.rti_id
  is 'RTI ID Primary Key';
comment on column TB_RTI_APPLICATION.rti_no
  is 'RTI NO';
comment on column TB_RTI_APPLICATION.apm_application_id
  is 'Application Id';
comment on column TB_RTI_APPLICATION.apm_application_date
  is 'Application Date';
comment on column TB_RTI_APPLICATION.sm_service_id
  is 'Service Id';
comment on column TB_RTI_APPLICATION.orgid
  is 'Organisation Id';
comment on column TB_RTI_APPLICATION.appl_reference_mode
  is 'Application Reference Mode Direct,Form E, Stamp';
comment on column TB_RTI_APPLICATION.RTI_DEPTID
  is 'Department Id';
comment on column TB_RTI_APPLICATION.rti_location_id
  is 'Location Id';
comment on column TB_RTI_APPLICATION.RTI_SUBJECT
  is 'RTI SUBJECT';
comment on column TB_RTI_APPLICATION.rti_details
  is 'RTI Details';
comment on column TB_RTI_APPLICATION.loiapplicable
  is 'flag to maintain loi applicable';
comment on column TB_RTI_APPLICATION.reason_for_loi_na
  is 'reason coulumn if loi is not applicable';
comment on column TB_RTI_APPLICATION.finaldispatchmode
  is 'Final dispatch mode';
comment on column TB_RTI_APPLICATION.dispatchdate
  is 'Dispatch Date';
comment on column TB_RTI_APPLICATION.stamp_no
  is 'Stamp No.';
comment on column TB_RTI_APPLICATION.stamp_amt
  is 'Stamp Amount';
comment on column TB_RTI_APPLICATION.stamp_doc_path
  is 'Stamp Doc Path';
comment on column TB_RTI_APPLICATION.rti_action
  is 'Status information from RRS prefix';
comment on column TB_RTI_APPLICATION.reason_id
  is 'Foreign reference from common reason master';
comment on column TB_RTI_APPLICATION.RTI_REMARK
  is 'Remark';
comment on column TB_RTI_APPLICATION.PARTIAL_INFO_FLAG
  is 'flag for maintianing full or partial provided information';
comment on column TB_RTI_APPLICATION.inward_type
  is 'Inward Type Ex. Form E';
comment on column TB_RTI_APPLICATION.inw_reference_no
  is 'Inward Reference No. In case of FORM E';
comment on column TB_RTI_APPLICATION.inw_reference_date
  is 'Inward Reference Date';
comment on column TB_RTI_APPLICATION.inw_authority_dept
  is 'Inward Reference Authority Department in case of FORM E';
comment on column TB_RTI_APPLICATION.inw_authority_name
  is 'Inward - name of the applicant authority- In case of FORM E';
comment on column TB_RTI_APPLICATION.inw_authority_address
  is 'Inward - address of the applicant authority- In case of FORM E';
comment on column TB_RTI_APPLICATION.oth_forward_pio_address
  is 'Forwarded To Other Organisation PIO Address';
comment on column TB_RTI_APPLICATION.oth_forward_pio_name
  is 'Forwarded To Other Organisation - PIO Name';
comment on column TB_RTI_APPLICATION.oth_forward_dept_name
  is 'Forwarded To Other Organisation - Department Name';
comment on column TB_RTI_APPLICATION.oth_forward_pio_email
  is 'Forwarded To Other Organisation - PIO Email Id';
comment on column TB_RTI_APPLICATION.oth_forward_pio_mob_no
  is 'Forwarded To Other Organisation - PIO Email Id';
comment on column TB_RTI_APPLICATION.lang_id
  is 'Language Id';
comment on column TB_RTI_APPLICATION.user_id
  is 'User Id';
comment on column TB_RTI_APPLICATION.lmoddate
  is 'Entry Date';
comment on column TB_RTI_APPLICATION.lg_ip_mac
  is 'Client Machines Login Name | IP Address | Physical Address';
comment on column TB_RTI_APPLICATION.updated_by
  is 'Last Updated By';
comment on column TB_RTI_APPLICATION.updated_date
  is 'Last Updated Date';
comment on column TB_RTI_APPLICATION.lg_ip_mac_upd
  is 'last Updated Client Machines Login Name | IP Address | Physical Address';
comment on column TB_RTI_APPLICATION.isdeleted
  is 'Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active) record';


ALTER TABLE TB_RTI_APPLICATION ADD CONSTRAINT PK_RTI_APP_MAS_RTI_ID PRIMARY KEY (RTI_ID);
CREATE INDEX IX_RTI_NO ON TB_RTI_APPLICATION (RTI_NO);
ALTER TABLE TB_RTI_APPLICATION ADD CONSTRAINT FK_RTI_APPLICATION_ID FOREIGN KEY (APM_APPLICATION_ID) REFERENCES tb_cfc_application_mst (APM_APPLICATION_ID);
