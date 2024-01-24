--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_09122020.sql
alter table tb_ast_info_mst add processor bigint(20) null ;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220201.sql
alter table tb_ast_info_mst add ram_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220202.sql
alter table tb_ast_info_mst add screen_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220203.sql
alter table tb_ast_info_mst add os_name bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220204.sql
alter table tb_ast_info_mst add hard_disk_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220205.sql
alter table tb_ast_info_mst add manufacturing_year datetime null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220206.sql
alter table tb_ast_info_mst_hist add processor bigint(20) null ;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220207.sql
alter table tb_ast_info_mst_hist add ram_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220208.sql
alter table tb_ast_info_mst_hist add screen_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_091220209.sql
alter table tb_ast_info_mst_hist add os_name bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_0912202010.sql
alter table tb_ast_info_mst_hist add hard_disk_size bigint(20) null;
--liquibase formatted sql
--changeset Kanchan:V20201209200051__AL_tb_ast_info_mst_0912202011.sql
alter table tb_ast_info_mst_hist add manufacturing_year datetime null;
