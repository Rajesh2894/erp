--liquibase formatted sql
--changeset nilima:V20181008120005__AL_tb_ast_purchaser1.sql
alter table tb_ast_purchaser add column initial_book_date date null comment '-- date --' after BOOK_VALUE;
--liquibase formatted sql
--changeset nilima:V20181008120005__AL_tb_ast_purchaser2.sql
alter table tb_ast_purchaser_hist add column initial_book_date date null comment '-- date --' after BOOK_VALUE;
--liquibase formatted sql
--changeset nilima:V20181008120005__AL_tb_ast_purchaser3.sql
alter table tb_ast_purchaser_rev add column initial_book_date date null comment '-- date --' after BOOK_VALUE;


