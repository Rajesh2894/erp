--liquibase formatted sql
--changeset Kanchan:V20220811120808__AL_TB_WMS_WORKESTIMATE_MAS_HIST_11082022.sql
alter table TB_WMS_WORKESTIMATE_MAS_HIST add column WORK_EST_STATUS char(1) null default null;