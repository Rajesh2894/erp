--liquibase formatted sql
--changeset Kanchan:V20210405094731__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_05042021.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST_HIST modify column purpose_remark   varchar(1000)  NULL;
--liquibase formatted sql
--changeset Kanchan:V20210405094731__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_050420211.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST  modify column PROPOSAL_DETAILS varchar(3000);
--liquibase formatted sql
--changeset Kanchan:V20210405094731__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_050420212.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST  modify column purpose_remark varchar(1000);
--liquibase formatted sql
--changeset Kanchan:V20210405094731__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_050420213.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST_HIST  modify column PROPOSAL_DETAILS varchar(3000);

