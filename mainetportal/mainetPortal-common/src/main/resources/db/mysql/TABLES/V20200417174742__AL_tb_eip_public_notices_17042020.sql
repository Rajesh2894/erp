--liquibase formatted sql
--changeset Anil:V20200417174742__AL_tb_eip_public_notices_17042020.sql
ALTER TABLE tb_eip_public_notices ADD COLUMN LINK_NO BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200417174742__AL_tb_eip_public_notices_170420201.sql
ALTER TABLE tb_eip_public_notices_hist ADD COLUMN LINK_NO BIGINT(12) NULL;
