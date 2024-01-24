-- Create table
create table TB_PORTAL_SMS_MAIL_TEMPLATE
(
  tp_id         NUMBER(12) not null,
  mail_sub      NVARCHAR2(2000),
  mail_header   NVARCHAR2(2000),
  mail_footer   NVARCHAR2(2000),
  mail_body     NVARCHAR2(2000),
  se_id         NUMBER(12) not null,
  sms_body      NVARCHAR2(2000),
  message_type  VARCHAR2(5),
  mail_sub_reg  NVARCHAR2(2000),
  mail_body_reg NVARCHAR2(2000),
  sms_body_reg  NVARCHAR2(2000),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lmoddate      DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
