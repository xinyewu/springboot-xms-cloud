package com.yc.bean;

import java.io.Serializable;

//这是购物车中的一项商品
public class CartItem implements Serializable {
    private Resfood food;
    private Integer num;
    private Double smallCount;

    public Double getSmallCount(){
        if (food!=null){
            smallCount= food.getRealprice()*this.num;
        }
        return smallCount;
    }

    public Resfood getFood() {
        return food;
    }

    public void setFood(Resfood food) {
        this.food = food;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
