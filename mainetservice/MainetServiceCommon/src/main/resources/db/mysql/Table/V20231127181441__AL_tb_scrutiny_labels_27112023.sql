--liquibase formatted sql
--changeset PramodPatil:V20231127181441__AL_tb_scrutiny_labels_27112023.sql
alter table tb_scrutiny_labels modify SL_LABEL longtext;