CREATE TABLE `usuario` (
  `UF` char(2) NOT NULL,
  `password` char(50) NOT NULL,
  `rol` char(10) NOT NULL,
  `piso` char(4) NOT NULL,
  `depto` char(4) NOT NULL,
  `email` char(50) DEFAULT '',
  `email2` char(50) DEFAULT '',
  PRIMARY KEY (`UF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `reserva` (
  `reserva_id` int(11) NOT NULL AUTO_INCREMENT,
  `UF` int(11) NOT NULL,
  `inicio` datetime NOT NULL,
  `fin` datetime NOT NULL,
  PRIMARY KEY (`reserva_id`)
) ENGINE=InnoDB AUTO_INCREMENT=439 DEFAULT CHARSET=utf8;