package com.example.barhop.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {
    OrderList ol1 = new OrderList(2,1);
    OrderList ol2 = new OrderList(3,1);
    OrderList ol3 = new OrderList(4,1);
    OrderList ol4 = new OrderList(5,1);

    OrderList addBarOl1 = new OrderList();
    OrderList RemBarOl1 = new OrderList();


    @Test
    void calcCost() {
        ol1.calcCost();
        assertEquals(2.99,ol1.getCost());
        ol4.calcCost();
        assertEquals(5.96,ol4.getCost());
        ol3.calcCost();
        assertEquals(4.97,ol3.getCost());
        ol2.calcCost();
        assertEquals(3.98,ol2.getCost());
    }

    @Test
    void addBar() {
        addBarOl1.addBar("Bar1");
        addBarOl1.addBar("Bar2");
        addBarOl1.addBar("Bar3");
        String expected = "Bar1, Bar2, Bar3";
        assertEquals(expected,addBarOl1.getBar_list());
    }

    @Test
    void removeBar() {
        RemBarOl1.addBar("Bar1");
        RemBarOl1.addBar("Bar2");
        RemBarOl1.addBar("Bar3");
        RemBarOl1.removeBar("Bar2");
        String expected = "Bar1, Bar3";
        assertEquals(expected,RemBarOl1.getBar_list());


    }
}