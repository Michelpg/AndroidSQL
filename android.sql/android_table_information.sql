
-- --------------------------------------------------------

--
-- Structure de la table `information`
--

DROP TABLE IF EXISTS `information`;
CREATE TABLE IF NOT EXISTS `information` (
  `pseudo` varchar(20) NOT NULL,
  `motdepas` varchar(50) NOT NULL,
  `confirmermdp` varchar(50) NOT NULL,
  `association` varchar(50) DEFAULT NULL,
  `semaine` varchar(300) DEFAULT NULL,
  `niveau` varchar(19) DEFAULT NULL,
  `commentaire` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
