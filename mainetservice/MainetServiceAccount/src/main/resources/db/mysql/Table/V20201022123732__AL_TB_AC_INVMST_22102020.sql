--liquibase formatted sql
--changeset Kanchan:V20201022123732__AL_TB_AC_INVMST_22102020.sql
alter table TB_AC_INVMST  add column ORG_PRINCIPAL_AMT decimal(12,2), add NO_TIMES_RENEWAL  bigint(10);
--liquibase formatted sql
--changeset Kanchan:V20201022123732__AL_TB_AC_INVMST_221020201.sql
alter table TB_AC_INVMST_HIST  add column ORG_PRINCIPAL_AMT decimal(12,2), add NO_TIMES_RENEWAL  bigint(10);
--liquibase formatted sql
--changeset Kanchan:V20201022123732__AL_TB_AC_INVMST_221020202.sql
alter table TB_AC_INVMST_HIST modify IN_INTRATE decimal(16,2);
--liquibase formatted sql
--changeset Kanchan:V20201022123732__AL_TB_AC_INVMST_221020203.sql
alter table TB_AC_INVMST_HIST modify  IN_INVNO varchar(100);











