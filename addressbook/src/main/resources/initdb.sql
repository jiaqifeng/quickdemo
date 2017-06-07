CREATE TABLE if not exists `addresses` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '',
  `address` varchar(64) NOT NULL COMMENT '',
  `mobile` varchar(24) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into addresses (name,address,mobile) values ('jack', 'beijing', '13900010001');
insert into addresses (name,address,mobile) values ('tom', 'shanghai', '13900010002');
insert into addresses (name,address,mobile) values ('alice', 'shenzhen', '13900010003');
