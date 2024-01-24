--liquibase formatted sql
--changeset nilima:V20180720200531__AL_TB_AST_INFO_MST1.sql
alter table TB_AST_INFO_MST drop index UN_RFI_ID;
--liquibase formatted sql
--changeset nilima:V20180720200531__AL_TB_AST_INFO_MST2.sql
ALTER TABLE TB_AST_INFO_MST ADD CONSTRAINT UN_RFI_ID UNIQUE (RFI_ID,ORGID);


