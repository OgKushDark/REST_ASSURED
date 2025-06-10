package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Delete;


import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteBooking implements  Task {

    private final String token;
    private final String bookingId;


    public DeleteBooking() {
        this.bookingId = OnStage.theActorInTheSpotlight().recall("bookingId");
        this.token = OnStage.theActorInTheSpotlight().recall("token");
    }

    public static Performable fromPage() {
        return instrumented(DeleteBooking.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(Delete.from("/booking/" + bookingId)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .header("Cookie", "token=" + token)
                        .log().all()));
    }
}
