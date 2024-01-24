--liquibase formatted sql
--changeset nilima:V20181001211559__AL_TB_WORKFLOW_TASK_21092018.sql
ALTER TABLE TB_WORKFLOW_TASK
ADD COLUMN WORKFLOW_REQ_ID BIGINT(12) NULL AFTER WFTASK_ID,
ADD INDEX FK_REQID_idx (WORKFLOW_REQ_ID ASC);

--liquibase formatted sql
--changeset nilima:V20181001211559__AL_TB_WORKFLOW_TASK_210920181.sql
ALTER TABLE TB_WORKFLOW_TASK 
ADD CONSTRAINT FK_REQID
  FOREIGN KEY (WORKFLOW_REQ_ID)
  REFERENCES tb_workflow_request (WORKFLOW_REQ_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
