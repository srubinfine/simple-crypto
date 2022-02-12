package com.adgarsolutions.shared.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AsyncStoredProcedureRepository {
    public <T> Flux<T> fetchResultMany(String storedProcName, Object... params) {
        return null;
    }

    public <T> Mono<T> fetchResultOne(String storedProcName, Object... params) {
        return null;
    }
}
