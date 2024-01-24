--liquibase formatted sql
--changeset priya:V20171109200636__CR_PORTAL_tb_newsletter_scubscription_det_08112017.sql
CREATE TABLE tb_newsletter_scubscription_det(
  ORGID bigint(12)  COMMENT 'Organisation Id',
  EMAILID VARCHAR(100) COMMENT 'Email id',
  SUB_STATUS VARCHAR(1) COMMENT 'Subscription Status',
  SUB_SDATE date COMMENT 'Subscription Start Date',
  SUB_EDATE date COMMENT 'Subscription Start Date'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
