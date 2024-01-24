--liquibase formatted sql
--changeset Kanchan:V20220705111151__AL_TB_ADJUSTMENT_MAS_05072022.sql
alter table TB_ADJUSTMENT_MAS modify column adj_date datetime not null,
modify column created_date datetime not null,
modify column updated_date datetime null default null;
--liquibase formatted sql
--changeset Kanchan:V20220705111151__AL_TB_ADJUSTMENT_MAS_050720221.sql
alter table TB_ADJUSTMENT_DET modify column created_date datetime not null,
modify column updated_date datetime null default null;