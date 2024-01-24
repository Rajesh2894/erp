 CREATE TABLE TB_RTI_MEDIA_DETAILS
 (RTI_MED_ID NUMBER,
  RTI_ID NUMBER,
  LOI_ID NUMBER,
  CARE_REQ_ID NUMBER,
  ORGID NUMBER NOT NULL ,
  MEDIA_TYPE NUMBER,
  MEDIA_QUANTITY NUMBER,
  MEDIA_AMOUNT   NUMBER(10,2),
  LANG_ID NUMBER NOT NULL ,
  USER_ID NUMBER NOT NULL ,
  LMODDATE DATE NOT NULL,
  LG_IP_MAC VARCHAR2(100) ,
  UPDATED_BY NUMBER,
  UPDATED_DATE DATE ,
  LG_IP_MAC_UPD VARCHAR2(100) ,
  ISDELETED NUMBER);

ALTER TABLE TB_RTI_MEDIA_DETAILS ADD CONSTRAINT PK_RTI_APP_MED_ID PRIMARY KEY (RTI_MED_ID);
ALTER TABLE TB_RTI_MEDIA_DETAILS ADD CONSTRAINT FK_RTI_MED_RTI_ID FOREIGN KEY (RTI_ID) REFERENCES TB_RTI_APPLICATION (RTI_ID);

comment on column TB_RTI_MEDIA_DETAILS.RTI_MED_ID
  is 'Media Type Id Primary Key';
comment on column TB_RTI_MEDIA_DETAILS.RTI_ID
  is 'RTI ID From TB_RTI_APPLICATION';
comment on column TB_RTI_MEDIA_DETAILS.LOI_ID
  is 'LOI_ID From TB_LOI_MAS';
comment on column TB_RTI_MEDIA_DETAILS.CARE_REQ_ID 
  is 'CARE_REQ_ID From tb_care_request';    
comment on column TB_RTI_MEDIA_DETAILS.orgid
  is 'Organisation Id';
comment on column TB_RTI_MEDIA_DETAILS.MEDIA_TYPE
  is 'Media Type Ex. Paper, Mail, CD etc';
comment on column TB_RTI_MEDIA_DETAILS.MEDIA_QUANTITY
  is 'Media Quantity';
comment on column TB_RTI_MEDIA_DETAILS.MEDIA_AMOUNT
  is 'Media Amount - LOI';    
comment on column TB_RTI_MEDIA_DETAILS.lang_id
  is 'Language Id';
comment on column TB_RTI_MEDIA_DETAILS.user_id
  is 'User Id';
comment on column TB_RTI_MEDIA_DETAILS.lmoddate
  is 'Entry Date';
comment on column TB_RTI_MEDIA_DETAILS.lg_ip_mac
  is 'Client Machines Login Name | IP Address | Physical Address';
comment on column TB_RTI_MEDIA_DETAILS.updated_by
  is 'Last Updated By';
comment on column TB_RTI_MEDIA_DETAILS.updated_date
  is 'Last Updated Date';
comment on column TB_RTI_MEDIA_DETAILS.lg_ip_mac_upd
  is 'last Updated Client Machines Login Name | IP Address | Physical Address';
comment on column TB_RTI_MEDIA_DETAILS.isdeleted
  is 'Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active) record';
