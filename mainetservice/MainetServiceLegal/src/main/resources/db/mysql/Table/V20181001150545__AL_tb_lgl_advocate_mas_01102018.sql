delete from tb_lgl_advocate_mas;
commit;
ALTER TABLE tb_lgl_advocate_mas
CHANGE COLUMN adv_feetype adv_feetype BIGINT(12) NULL DEFAULT NULL COMMENT 'C->Per Case,L->per lawyer' ;
