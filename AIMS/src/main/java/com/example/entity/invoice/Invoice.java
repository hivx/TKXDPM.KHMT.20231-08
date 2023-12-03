package com.example.entity.invoice;

import com.example.entity.order.Order;

public class Invoice {

    private Order order;
    private int amount;
    
    public Invoice(){

    }

    //Data coupling
    public Invoice(Order order){
        this.order = order;
    }

    //Data coupling
    public Order getOrder() {
        return order;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void saveInvoice(){
        
    }
}
