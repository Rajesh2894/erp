--liquibase formatted sql
--changeset PramodPatil:V20231130121748__AL_MM_ITEMOPENINGBALANCE_30112023.sql
ALTER TABLE MM_ITEMOPENINGBALANCE modify openingbalance double(12,1);

--liquibase formatted sql
--changeset PramodPatil:V20231130121748__AL_MM_ITEMOPENINGBALANCE_301120231.sql
ALTER TABLE MM_ITEMOPENINGBALANCE_DET modify quantity double(12,1);