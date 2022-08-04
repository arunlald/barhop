
package com.example.barhop.repositories;


import com.example.barhop.entities.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository  extends JpaRepository<OrderList,Long> {

    OrderList findOrderById (long kw);
    OrderList findOrderByMail(String kn);
}
