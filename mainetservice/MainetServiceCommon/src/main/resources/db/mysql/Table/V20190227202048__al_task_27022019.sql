--liquibase formatted sql
--changeset nilima:V20190227202048__al_task_27022019.sql
ALTER TABLE task
CHANGE COLUMN subject subject VARCHAR(3000) NULL;
