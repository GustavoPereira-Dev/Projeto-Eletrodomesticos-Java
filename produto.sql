-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 06-Jul-2018 às 12:50
-- Versão do servidor: 5.7.17
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";



CREATE DATABASE IF NOT EXISTS `clientes` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `clientes`;


CREATE TABLE `tbEletrodomesticos` (
  `cod` int(255) NOT NULL,
  `tipo` varchar(60) NOT NULL,
  `marca` varchar(60) NOT NULL,
  `linha` varchar(70) NOT NULL,
  `preco` DECIMAL(7,2) NOT NULL,
  `fornecedor` varchar(100) NOT NULL,
  `descricao` varchar(250) NOT NULL,
  `garantia` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;



INSERT INTO `tbEletrodomesticos` (`cod`, `tipo`, `marca`, `linha`, `preco`, `fornecedor`, `descricao`, `garantia`) VALUES
(1, 'Geladeira', 'Electrolux', 'French Door', 1500.00, 'André', 'Descricao x', '1 ano'),
(2, 'Fogão', 'Brastemp', 'Ative!', 1000.00, 'José', 'Descricao y', '10 meses'),
(3, 'Televisão', 'LG', 'OLED', 2000.00, 'Edinaldo', 'Descricao z', '11 meses'),
(4, 'Máquina de lavar', 'Consul', 'Facilite', 2660.00, 'João', 'Descricao s', '15 meses'),
(5, 'Micro-ondas', 'Panasonic', 'NN-ST', 1380.00, 'Gustavo', 'Descricao a', '8 meses');

ALTER TABLE `tbEletrodomesticos`
  ADD PRIMARY KEY (`cod`);


ALTER TABLE `tbEletrodomesticos`
  MODIFY `cod` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;


