package com.byinal.paymentserver;

import com.byinal.model.Payment;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class PaymentServiceApplication {

    @Bean
    RouterFunction<ServerResponse> routes(PaymentRepository pr) {
        return route(GET("/payments"), req -> ok().body(pr.findAll(), Payment.class));
    }

    @Bean
    ApplicationRunner runner(PaymentRepository pr) {
        return args -> pr.deleteAll()
                .thenMany(Flux.just("alp", "simay")
                        .map(p -> new Payment(null, p))
                        .flatMap(pr::save))
                .thenMany(pr.findAll())
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}

interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {
    Flux<Payment> findByName(String name);
}

