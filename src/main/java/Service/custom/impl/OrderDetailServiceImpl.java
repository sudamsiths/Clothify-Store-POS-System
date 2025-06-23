package Service.custom.impl;

import DTO.OrderDetails;
import util.CRUDutil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailServiceImpl {
    public Boolean addOrderDetails(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails details : orderDetails){
            Boolean isAdd = addOrderDetails(details);
            if(!isAdd){
                return false;
            }
        }
        return true;
    }
    public Boolean addOrderDetails(OrderDetails orderDetails) throws SQLException {
        return CRUDutil.execute("INSERT INTO order_details VALUES (?,?,?,?,?,?)",
                orderDetails.getId(),
                orderDetails.getItem_code(),
                orderDetails.getCategory(),
                orderDetails.getSize(),
                orderDetails.getQty(),
                orderDetails.getUnit_price()
        );
    }
}
