CREATE TABLE `_sequences` (
  `name` varchar(70) NOT NULL,
  `next` int(11) NOT NULL,
  `inc` int(11) NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
