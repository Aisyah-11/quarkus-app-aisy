package org.aisys.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

public class OrderProductRequest {
//    @NotBlank
//    private String userName;
    @NotBlank
    //private String productStock;
    private String productId;

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

//    public String getProductStock() {
//        return productStock;
//    }
//
//    public void setProductStock(String productStock) {
//        this.productStock = productStock;
//    }


//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
