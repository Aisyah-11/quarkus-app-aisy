package org.aisys.entity;

import jakarta.persistence.*;
import org.aisys.constant.ProductStockStatus;

import java.time.Instant;

@Entity
@Table(name = "product_stocks")
public class ProductStock {

    @Id
    private String code;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Enumerated(EnumType.STRING)
    private ProductStockStatus status;

    @Column(name = "checkout_by")
    private String checkoutBy;

    @Column(name = "checkout_at")
    private Instant checkoutAt;

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

    public String getCheckoutBy() {
        return checkoutBy;
    }

    public void setCheckoutBy(String checkoutBy) {
        this.checkoutBy = checkoutBy;
    }

    public Instant getCheckoutAt() {
        return checkoutAt;
    }

    public void setCheckoutAt(Instant checkoutAt) {
        this.checkoutAt = checkoutAt;
    }
}