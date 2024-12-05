package org.acme;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

@ApplicationScoped
public class MyMessagingApplication {

    Logger log = LoggerFactory.getLogger(MyMessagingApplication.class);

    @Inject
    @Channel("words-out")
    Emitter<String> emitter;

    /**
     * Sends message to the "words-out" channel, can be used from a JAX-RS resource or any bean of your application.
     * Messages are sent to the broker.
     **/
    void onStart(@Observes StartupEvent ev) {
        Stream.of("Hello", "with", "Quarkus", "Messaging", "message").forEach(string -> emitter.send(string));
    }

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     **/
    @Incoming("words-in")
    // @Blocking
    @RunOnVirtualThread
    public void toUpperCase(String message) {
        log.info("received " + message);
    }

    void send() {
        String string = ZonedDateTime.now().toString();
        log.info("sending now {}", string);
        emitter.send(string);
    }
}
