-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-11-11 10:53:47
-- 伺服器版本： 10.4.28-MariaDB
-- PHP 版本： 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `yushan`
--

DELIMITER $$
--
-- 程序
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `Delete_User_Like_List` (IN `In_Uid` VARCHAR(255), IN `In_Sn` INT)   main:BEGIN
        	DECLARE productNo INT DEFAULT NULL;
            
        	SELECT `like list`.`no` INTO productNo
            FROM `like list`
            WHERE `like list`.`user_id` = In_Uid AND `like list`.`sn` = In_Sn;
            
            IF productNo IS NOT NULL THEN
            	BEGIN
                	DELETE `like list` FROM `like list` WHERE `like list`.`sn` = In_Sn;
                    DELETE `product` FROM `product` WHERE `product`.`no` = productNo;
                END;
            END IF;
        END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Insert_User_Like_List` (IN `In_Uid` VARCHAR(255), IN `In_Product_Name` VARCHAR(255), IN `In_Product_Price` INT, IN `In_Product_FeeRate` DOUBLE, IN `In_Default_Account` VARCHAR(255), `In_Order_Name` INT)   main:BEGIN
            
            INSERT INTO `product`(`fee_rate`,`price`,`product_name`)
            SELECT In_Product_FeeRate, In_Product_Price, In_Product_Name
            FROM `user`
            WHERE `user`.`user_id` = IN_Uid;
            
            IF LAST_INSERT_ID() > 0 THEN
                INSERT INTO `like list`(`account`, `order_name`, `total_amount`, `total_fee`, `no`, `user_id`)
                SELECT In_Default_Account, In_Order_Name, ROUND((In_Product_Price*In_Product_FeeRate*In_Order_Name) + (In_Product_Price*In_Order_Name),0), (In_Product_Price*In_Product_FeeRate*In_Order_Name), LAST_INSERT_ID(), In_Uid
                FROM `user`
                WHERE `user`.`user_id` = IN_Uid;
			END IF;
   END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Select_User` (IN `In_Uid` VARCHAR(255))   main:BEGIN
        	SELECT `user`.`user_id`,`user`.`account`,`user`.`email`,`user`.`user_name`
            FROM `user`
            WHERE `user`.`user_id` = In_Uid;
        END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Select_User_Like_List` (IN `In_Uid` VARCHAR(255))   main:BEGIN
        	SELECT `like list`.`sn`, `like list`.`account`, `like list`.`order_name` as `orderName` , `like list`.`total_amount` as `totalAmount`, `like list`.`total_fee` as `totalFee`, `product`.`no`, `product`.`fee_rate` as `feeRate`, `product`.`price`, `product`.`product_name` as `productName`
            FROM `like list` INNER JOIN `product` ON `like list`.`no` = `product`.`no` INNER JOIN `user` ON `like list`.`user_id` = `user`.`user_id`
            WHERE `like list`.`user_id` = In_Uid;            
        END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Update_User_Like_List` (IN `In_Uid` VARCHAR(255), IN `In_Sn` INT, IN `In_Account` VARCHAR(255), IN `In_Order_name` INT, IN `In_Fee_Rate` DOUBLE, IN `In_Price` INT, IN `In_Product_Name` VARCHAR(255))   main:BEGIN
        	UPDATE `like list` INNER JOIN `product` ON `like list`.`no` = `product`.`no`
            SET 
            	`like list`.`account` = In_Account,
                `like list`.`order_name` = In_Order_name,
                `product`.`fee_rate` = In_Fee_Rate,
                `product`.`price` = In_Price,
                `product`.`product_name` = In_Product_Name,
                `like list`.`total_fee` = ROUND(In_Fee_Rate * In_Order_name * In_Price, 0 ),
                `like list`.`total_amount` = ROUND(In_Fee_Rate * In_Order_name * In_Price, 0 ) +  (In_Order_name * In_Price)
            WHERE `like list`.`user_id` = In_Uid AND `like list`.`sn` = In_Sn;
        END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- 資料表結構 `like list`
--

CREATE TABLE `like list` (
  `sn` int(11) NOT NULL,
  `account` varchar(255) NOT NULL,
  `order_name` int(11) NOT NULL,
  `total_amount` int(11) NOT NULL,
  `total_fee` int(11) NOT NULL,
  `no` int(11) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `like list`
--

INSERT INTO `like list` (`sn`, `account`, `order_name`, `total_amount`, `total_fee`, `no`, `user_id`) VALUES
(1, '11115555555', 2, 192, 32, 1, 'A123456789'),
(2, '11133344455', 1, 6000, 1000, 2, 'A123456789'),
(3, '1166554488', 4, 5200, 1200, 3, 'A123456789');

-- --------------------------------------------------------

--
-- 資料表結構 `product`
--

CREATE TABLE `product` (
  `no` int(11) NOT NULL,
  `fee_rate` double NOT NULL,
  `price` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `product`
--

INSERT INTO `product` (`no`, `fee_rate`, `price`, `product_name`) VALUES
(1, 0.2, 80, '金融商品A'),
(2, 0.2, 5000, '金融商品BB'),
(3, 0.3, 1000, '金融AA');

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `user`
--

INSERT INTO `user` (`user_id`, `account`, `email`, `user_name`) VALUES
('A123456789', '1111999666', 'test@email.com', '王X明');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `like list`
--
ALTER TABLE `like list`
  ADD PRIMARY KEY (`sn`),
  ADD KEY `FKkrxtetvrl6cgbikm6vt35xbvt` (`no`),
  ADD KEY `FKlh1rmpyc7oeapcui0uch183jg` (`user_id`);

--
-- 資料表索引 `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`no`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `like list`
--
ALTER TABLE `like list`
  MODIFY `sn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `product`
--
ALTER TABLE `product`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `like list`
--
ALTER TABLE `like list`
  ADD CONSTRAINT `FKkrxtetvrl6cgbikm6vt35xbvt` FOREIGN KEY (`no`) REFERENCES `product` (`no`),
  ADD CONSTRAINT `FKlh1rmpyc7oeapcui0uch183jg` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
