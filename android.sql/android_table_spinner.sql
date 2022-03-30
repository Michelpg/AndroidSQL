
-- --------------------------------------------------------

--
-- Structure de la table `spinner`
--

DROP TABLE IF EXISTS `spinner`;
CREATE TABLE IF NOT EXISTS `spinner` (
  `spinner` varchar(50) NOT NULL,
  PRIMARY KEY (`spinner`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `spinner`
--

INSERT INTO `spinner` (`spinner`) VALUES
('Association X'),
('Association Y'),
('Association Z');
