--liquibase formatted sql
--changeset nilima:V20170718192634__AL_WORKFLOW_MAPPING_13072017.sql
ALTER TABLE WORKFLOW_MAPPING
ADD CONSTRAINT FK_WORKFLOW_TYPE_ID
  FOREIGN KEY (WORKFLOW_TYPE_ID)
  REFERENCES WORKFLOW_TYPE (ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  
