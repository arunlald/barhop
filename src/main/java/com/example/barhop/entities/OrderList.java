
package com.example.barhop.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mail;

    private String bar_list;

    public OrderList(int number_of_bars) {
        this.number_of_bars = number_of_bars;

    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int number_of_bars;
    private double cost;

    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBar_list() {
        return bar_list;
    }

    public void setBar_list(String bar_list) {
        this.bar_list = bar_list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber_of_bars() {
        return number_of_bars;
    }

    public void setNumber_of_bars(int number_of_bars) {
        this.number_of_bars = number_of_bars;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void calcCost(){
        if (this.number_of_bars>=2)
            this.cost =  BigDecimal.valueOf((2.99 + ((this.number_of_bars-2)*0.99))*this.quantity)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
        else
            this.cost =  0.0;
    }

    public void addBar(String barName){
        if (number_of_bars == 0){
            this.bar_list = barName;
            number_of_bars = 1;
        }else {
            ArrayList<String> tempList = convertToList();
            if (!tempList.contains(barName)) {
                tempList.add(barName);
                number_of_bars = tempList.size();
                calcCost();
                this.bar_list = convertToString(tempList);
            }
        }
    }

    public void removeBar(String barName){
        ArrayList<String> tempList = convertToList();
        if (tempList.contains(barName)){
            tempList.remove(barName);
            number_of_bars = tempList.size();
            calcCost();
            this.bar_list = convertToString(tempList);
        }
    }

    public ArrayList<String> convertToList(){
        return new ArrayList<>(Arrays.asList(this.bar_list.split("\\s*,\\s*")));
    }

    public String convertToString(ArrayList<String> list){
        String returnValue = list.toString();
        returnValue = returnValue.substring(1, returnValue.length() - 1);
        return returnValue;
    }

}

