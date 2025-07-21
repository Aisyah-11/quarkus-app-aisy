package org.aisys.dto;

import org.aisys.constant.ProductStockStatus;

public class ProductDataListStock {
    private String code;
    private ProductStockStatus status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProductStockStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStockStatus status) {
        this.status = status;
    }
}
