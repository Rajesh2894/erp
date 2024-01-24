--liquibase formatted sql
--changeset nilima:V20181120123443__CR_TB_BPM_DEPLOYMENT_HIST_16112018.sql
CREATE TABLE TB_BPM_DEPLOYMENT_HIST(
  DP_STID_H bigint(12) NOT NULL,
  DP_STID bigint(12),
  GROUP_ID varchar(50) ,
  ARTIFACT_ID varchar(50) ,
  VERSION varchar(200) ,
  PROCESS_ID varchar(200) ,
  BPM_RUNTIME varchar(50) ,
  RULEFLOWGROUP varchar(200) ,
  ARTIFACTTYPE varchar(200) ,
  STATUS char(1) ,
  H_STATUS char(1) , 
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (DP_STID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='deployment server Hist';