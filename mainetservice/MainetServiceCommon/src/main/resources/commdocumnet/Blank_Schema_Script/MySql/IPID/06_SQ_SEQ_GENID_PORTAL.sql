CREATE TABLE `_sequences` (
  `name` varchar(70) NOT NULL,
  `next` int(11) NOT NULL,
  `inc` int(11) NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Insert into _sequences
(name,next,inc) 
values 
('SQ_SEQ_GENID',1,1);
commit;