package org.aisys.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ProductData {
    private String id;

    @NotBlank
    private String item;

    @NotBlank
    private String item_code;

    @NotBlank
    private String price;

    private byte[] photo;

    private List<ProductDataListStock> stocks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<ProductDataListStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductDataListStock> stocks) {
        this.stocks = stocks;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
