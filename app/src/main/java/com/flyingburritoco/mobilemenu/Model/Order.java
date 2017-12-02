package com.flyingburritoco.mobilemenu.Model;

import java.util.ArrayList;

/**
 * Created by Zach on 11/30/2017.
 */

public class Order {
    private String ProductID;
    private String ProductName;
    private String Extras;
    private String Quantity;
    private String Price;
    private String Discount;

    public Order() {
    }

    public Order(String productID, String productName, String extras, String quantity, String price, String discount) {
        ProductID = productID;
        ProductName = productName;
        Extras = extras;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getExtras() {
        return Extras;
    }

    public void setExtras(String extras) {
        Extras = extras;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
