-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 15, 2013 at 04:14 PM
-- Server version: 5.1.33
-- PHP Version: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gdlmediasport_fraser2`
--

-- --------------------------------------------------------

--
-- Table structure for table `showroom_stockout_info`
--

CREATE TABLE IF NOT EXISTS `showroom_stockout_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodCode` varchar(100) DEFAULT NULL,
  `purchaseRate` double NOT NULL,
  `sellingRate` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `sRoomName` varchar(100) DEFAULT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `showroom_stockout_info`
--


-- --------------------------------------------------------

--
-- Table structure for table `show_room_products`
--

CREATE TABLE IF NOT EXISTS `show_room_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodName` varchar(100) DEFAULT NULL,
  `prodCode` varchar(100) DEFAULT NULL,
  `barCode` varchar(100) DEFAULT NULL,
  `brandName` varchar(100) DEFAULT NULL,
  `purchaseRate` double NOT NULL,
  `sellingRate` double NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `prodCode` (`prodCode`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `show_room_products`
--


-- --------------------------------------------------------

--
-- Table structure for table `warehouse_stockout_info`
--

CREATE TABLE IF NOT EXISTS `warehouse_stockout_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodCode` varchar(100) DEFAULT NULL,
  `purchaseRate` double NOT NULL,
  `sellingRate` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `sRoomName` varchar(100) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `warehouse_stockout_info`
--


-- --------------------------------------------------------

--
-- Table structure for table `ware_house_products`
--

CREATE TABLE IF NOT EXISTS `ware_house_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodName` varchar(100) DEFAULT NULL,
  `prodCode` varchar(100) NOT NULL,
  `barCode` varchar(100) DEFAULT NULL,
  `brandName` varchar(100) DEFAULT NULL,
  `purchaseRate` double NOT NULL,
  `sellingRate` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `prodCode` (`prodCode`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `ware_house_products`
--

