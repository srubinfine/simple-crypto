package com.adgarsolutions.services.orders;

import com.adgarsolutions.shared.model.Order;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.util.function.Tuple2;

import java.io.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

class FilePublisher {

    public static Flux<String> createFluxFromFile(Function<String, BufferedReader> f, String fileNameOrPath) throws IOException {
        var reader = new BufferedReader(new FileReader(new File(fileNameOrPath)));
        return Flux.create(s -> {
            String line;
            try {
                while (null != (line = reader.readLine())) {
                    s.next(line);
                }
                s.complete();
            } catch (Exception ex) {
                s.error(ex);
            }
        }, FluxSink.OverflowStrategy.BUFFER);
    }

    public static Flux<String> createFluxFromResourceFile(String fileName) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)));
        return Flux.create(s -> {
            String line;

            try {
                while (null != (line = reader.readLine())) {
                    s.next(line);
                }
                s.complete();
            } catch (Exception ex) {
                s.error(ex);
            }
        }, FluxSink.OverflowStrategy.BUFFER);
    }
}

class OrdersServiceTest {

    @Test
    void getAllOrdersForAccount() throws IOException {

        // NOTE:  there are 3 symbols in the file, but only 2 idx, so zip is going to generate ONLY 2 recs
        Flux.just(1,2).zipWith(FilePublisher.createFluxFromResourceFile("test1.txt"))
                .map(OrdersServiceTest::mapToOrder1).subscribe(System.out::println);

        // OR using static

        Flux f1 = Flux.just(1,2,3,4,5);
        Flux f2 = FilePublisher.createFluxFromResourceFile("test1.txt");

        // EITHER 2 LINES OR CAST Flux<Tuple2<String, Integer>> zipped = (Flux<Tuple2<String, Integer>>)Flux.zip(f2, f1);
        // zipped.map(OrdersServiceTest::mapToOrder2).subscribe(System.out::println);
        // NOTE: there are 3 symbols in the file, so zip is going to generate 3 recs, EVEN THOUGH there are 5 idxs
        ((Flux<Tuple2<String, Integer>>)Flux.zip(f2, f1)).map(OrdersServiceTest::mapToOrder2).subscribe(System.out::println);
    }


    @Test
    void createOrder() {
    }

    private static Order mapToOrder1(Tuple2<Integer, String> tpl) {
       return new Order(UUID.randomUUID().toString(), tpl.getT2(), BigDecimal.valueOf(125.4), BigDecimal.valueOf(tpl.getT1()), "Buy");
    }

    private static Order mapToOrder2(Tuple2<String, Integer> tpl) {
        return new Order(UUID.randomUUID().toString(), tpl.getT1(), BigDecimal.valueOf(125.4), BigDecimal.valueOf(tpl.getT2()), "Buy");
    }
}