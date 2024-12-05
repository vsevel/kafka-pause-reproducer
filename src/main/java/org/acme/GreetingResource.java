package org.acme;

import io.smallrye.reactive.messaging.ChannelRegistry;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    MyMessagingApplication messagingApplication;

    @Inject
    ChannelRegistry channelRegistry;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/send")
    public String send() {
        messagingApplication.send();
        return "sent";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/pause")
    public String pause() {
        channelRegistry.getPausable("words-in").pause();
        return "paused";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/resume")
    public String resume() {
        channelRegistry.getPausable("words-in").resume();
        return "resumed";
    }

}
