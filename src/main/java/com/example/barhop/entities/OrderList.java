
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

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int number_of_bars;
    private double cost;



    public void calcCost(){
        if (this.number_of_bars>2)
            this.cost =  BigDecimal.valueOf(2.99 + ((this.number_of_bars-2)*0.99))
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
        else if (this.number_of_bars < 2) {
            this.cost =  0.0;
        }else
            this.cost = 2.99;
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

