package com.reimbursement.Model;

public class Ticket {
    private int id;
    private int user_id;
    private float amount;
    private String type;
    private String desc;
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getUserId() {
        return this.user_id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public Ticket(float amount, String type, String desc) {
        this.amount = amount;
        this.type = type;
        this.desc = desc;
    } 

    public Ticket() {
        
    }

    public String toString() {
        return "{ id:" + this.id + ", user_id: " + this.user_id + ", amount: " + this.amount + 
        ", type: " + this.type + ", desc: " + this.desc + ", status: " + this.status + "}";
    }
}
