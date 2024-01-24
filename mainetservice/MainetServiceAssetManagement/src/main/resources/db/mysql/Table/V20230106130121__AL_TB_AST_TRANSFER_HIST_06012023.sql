--liquibase formatted sql
--changeset Kanchan:V20230106130121__AL_TB_AST_TRANSFER_HIST_06012023.sql
alter table TB_AST_TRANSFER_HIST modify column TRANSFER_DEPART bigint(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230106130121__AL_TB_AST_TRANSFER_HIST_060120231.sql
alter table TB_AST_TRANSFER modify column TRANSFER_DEPART bigint(12) null default null;