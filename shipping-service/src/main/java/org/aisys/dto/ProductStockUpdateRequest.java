package org.aisys.dto;

import org.aisys.constant.ProductStockStatus;

import java.time.Instant;

public class ProductStockUpdateRequest {
    private String checkoutBy;
    private Instant chekoutAt;
    private ProductStockStatus status;

    public String getCheckoutBy() {
        return checkoutBy;
    }

    public void setCheckoutBy(String checkoutBy) {
        this.checkoutBy = checkoutBy;
    }

    public Instant getChekoutAt() {
        return chekoutAt;
    }

    public void setChekoutAt(Instant chekoutAt) {
        this.chekoutAt = chekoutAt;
    }

    public ProductStockStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStockStatus status) {
        this.status = status;
    }
}
