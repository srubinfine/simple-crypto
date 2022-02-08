package com.adgarsolutions.services.orders;

import com.adgarsolutions.shared.model.Order;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FilePublisher implements Publisher<String> {

    private final BufferedReader reader;

    public FilePublisher(String fileName) {
        this.reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)));
    }

    @Override
    public void subscribe(Subscriber<? super String> s) {
        String line = null;
        try {
            while (null != (line = this.reader.readLine())) {
                s.onNext(line);
            }
            s.onComplete();
        } catch (Exception ex) {
            s.onError(ex);
        }
    }
}

class OrdersServiceTest {

    @Test
    void getAllOrdersForAccount() {
        Flux.from(new FilePublisher("test1.txt")).
           map(OrdersServiceTest::mapToOrder).subscribe(System.out::println);

    }

    @Test
    void createOrder() {
    }

    private static Order mapToOrder(String symbol) {
       return new Order(UUID.randomUUID().toString(), symbol, BigDecimal.valueOf(125.4), BigDecimal.valueOf(20.1), "Buy");
    }
}