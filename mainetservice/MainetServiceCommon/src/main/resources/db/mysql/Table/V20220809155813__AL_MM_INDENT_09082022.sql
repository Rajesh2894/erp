--liquibase formatted sql
--changeset Kanchan:V20220809155813__AL_MM_INDENT_09082022.sql
Alter table MM_INDENT modify column reportingmgr bigint(12) null default null;