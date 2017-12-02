package com.flyingburritoco.mobilemenu.Model;

import java.util.List;

/**
 * Created by Zach on 12/1/2017.
 */

public class Request {
    private String phone;
    private String name;
    private String store;
    private String total;
    private String status;
    private List<Order> foods;

    public Request() {
    }

    public Request(String phone, String name, String store, String total, List<Order> foods) {
        this.phone = phone;
        this.name = name;
        this.store = store;
        this.total = total;
        this.foods = foods;
        this.status = "0";
    }

    public String getPhone() {
        return phone;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

}
