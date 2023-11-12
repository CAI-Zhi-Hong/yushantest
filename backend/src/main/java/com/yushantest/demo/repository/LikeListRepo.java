package com.yushantest.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.yushantest.demo.entities.LikeList;



// @NamedNativeQuery(name = "Select_User_Like_List",
//                   query = "CALL Select_User_Like_List(:In_Uid);",
//                   resultSetMapping = "SelectLikeListViewDtoMapping")

// @SqlResultSetMapping (
//     name = "SelectLikeListViewDtoMapping",
//     classes = @ConstructorResult(
//         targetClass = SelectLikeListViewDto.class,
//         columns = {
//             @ColumnResult(name = "sn", type = Integer.class),
//             @ColumnResult(name = "account", type = String.class),
//             @ColumnResult(name = "orderName", type = Integer.class),
//             @ColumnResult(name = "totalAmount", type = Integer.class),
//             @ColumnResult(name = "totalFee", type = Integer.class),
//             @ColumnResult(name = "no", type = int.class),
//             @ColumnResult(name = "feeRate", type = double.class),
//             @ColumnResult(name = "price", type = Integer.class),
//             @ColumnResult(name = "productName", type = String.class),
//             @ColumnResult(name = "defaultAccount", type = String.class),
//             @ColumnResult(name = "email", type = String.class)
//         }
//     )
// )

public interface LikeListRepo extends JpaRepository<LikeList, Integer>{
    @Procedure(name = "Insert_User_Like_List")
    void Insert_User_Like_List(
        @Param("In_Uid") String In_Uid,
        @Param("In_Product_Name") String In_Product_Name,
        @Param("In_Product_Price") int In_Product_Price,
        @Param("In_Product_FeeRate") Double In_Product_FeeRate,
        @Param("In_Default_Account") String In_Default_Account,
        @Param("In_Order_Name") int In_Order_Name
    );
    
    /*
        DROP PROCEDURE IF EXISTS `Insert_User_Like_List`;
        DELIMITER //
        CREATE PROCEDURE `Insert_User_Like_List` (IN `In_Uid` VARCHAR(255), IN `In_Product_Name` VARCHAR(255), IN `In_Product_Price` INT, IN `In_Product_FeeRate` DOUBLE, IN `In_Default_Account` VARCHAR(255), `In_Order_Name` INT)   
        main:BEGIN
                
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
        END
        //
        DELIMITER ;
    */

    
    
    @Query(nativeQuery = true, value = "CALL Select_User_Like_List(:In_Uid)")
    List<Object[]> Select_User_Like_List(
        @Param("In_Uid") String In_Uid
    );
    /*
        DROP PROCEDURE IF EXISTS `Select_User_Like_List`;
        DELIMITER //
        CREATE PROCEDURE `Select_User_Like_List` (IN In_Uid VARCHAR(255))   
            main:BEGIN
                SELECT 
                    `like list`.`sn`,
                    `like list`.`account`, 
                    `like list`.`order_name` as `orderName`, 
                    `like list`.`total_amount` as `totalAmount`, 
                    `like list`.`total_fee` as `totalFee`, 
                    `product`.`no`, 
                    `product`.`fee_rate` as `feeRate`, 
                    `product`.`price`, 
                    `product`.`product_name` as `productName`
                FROM `like list` INNER JOIN `product` ON `like list`.`no` = `product`.`no` INNER JOIN `user` ON `like list`.`user_id` = `user`.`user_id`
                WHERE `like list`.`user_id` = In_Uid;            
            END
            //
        DELIMITER ;
    */

    
    @Query(nativeQuery = true, value = "CALL Select_User(:In_Uid)")
    List<Object[]> Select_User(
        @Param("In_Uid") String In_Uid
    );
    /* 
        DROP PROCEDURE IF EXISTS `Select_User`;
        DELIMITER //
        CREATE PROCEDURE `Select_User` (IN In_Uid VARCHAR(255))   
            main:BEGIN
                SELECT `user`.`user_id`,`user`.`account`,`user`.`email`,`user`.`user_name`
                FROM `user`
                WHERE `user`.`user_id` = In_Uid;
            END
            //
        DELIMITER ; 
    */


    @Procedure(name = "Delete_User_Like_List")
    void Delete_User_Like_List(
        @Param("In_Uid") String uid, 
        @Param("In_Sn") Integer likeListSN);
    /*
        DROP PROCEDURE IF EXISTS `Delete_User_Like_List`;
        DELIMITER //
        CREATE PROCEDURE `Delete_User_Like_List` (IN In_Uid VARCHAR(255), IN In_Sn INT)
            main:BEGIN
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
            END
            //
        DELIMITER ;
    */

    @Procedure(name = "Update_User_Like_List")
    void Update_User_Like_List(
        @Param("In_Uid") String uid, 
        @Param("In_Sn") Integer likeListSN,
        @Param("In_Account") String account, 
        @Param("In_Order_name") Integer orderName,
        @Param("In_Fee_Rate") Double feeRate,
        @Param("In_Price") Integer price, 
        @Param("In_Product_Name") String productName);
    /*
    DROP PROCEDURE IF EXISTS `Update_User_Like_List`;
    DELIMITER //
    CREATE PROCEDURE `Update_User_Like_List` (
        IN In_Uid VARCHAR(255), 
        IN In_Sn INT, 
        IN In_Account VARCHAR(255), 
        IN In_Order_name INT, 
        IN In_Fee_Rate DOUBLE, 
        IN In_Price INT, 
        IN In_Product_Name VARCHAR(255)
    )
    	main:BEGIN
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
        END
        //
    DELIMITER ; 

     */
}
