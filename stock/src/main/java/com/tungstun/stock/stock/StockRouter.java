//package com.tungstun.gateway.stock;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//
//@Configuration
//public class StockRouter {
//    @Bean
//    public RouterFunction<ServerResponse> stockRoutes(StockHandler handler){
//        return RouterFunctions
//                .route(GET("/stock/{id}").and(accept(APPLICATION_JSON)), handler::get);
//    }
//}
