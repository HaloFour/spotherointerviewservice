package com.spothero.resources;

import javax.ws.rs.container.AsyncResponse;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public class AsyncResource {
    public static void async(AsyncResponse response, Supplier<CompletionStage<?>> action) {
        CompletionStage<?> stage = action.get();
        stage.whenComplete((result, exception) -> {
            if (exception != null) {
                response.resume(exception);
            }
            else {
                response.resume(result);
            }
        });
    }
}
