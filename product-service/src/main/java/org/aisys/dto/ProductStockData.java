package org.aisys.dto;

import org.aisys.constant.ProductStockStatus;

public class ProductStockData {
    private String code;
    private String productId;
    private String productName;
    private ProductStockStatus status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductStockStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStockStatus status) {
        this.status = status;
    }
}
