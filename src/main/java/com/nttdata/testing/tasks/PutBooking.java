package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutBooking implements Task {
    private final String firstname, lastname, totalprice;
    private final String token;
    private final String bookingId;


    public PutBooking(String firstname, String lastname, String totalprice) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.bookingId = OnStage.theActorInTheSpotlight().recall("bookingId");
        this.token = OnStage.theActorInTheSpotlight().recall("token");
    }

    public static Performable fromPage(String firstname, String lastname, String totalprice) {
        return instrumented(PutBooking.class, firstname, lastname, totalprice);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(Put.to("/booking/" + bookingId)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .header("Cookie", "token=" + token)
                        .body("{\"firstname\":" + firstname + ","
                                + "\"lastname\":" + lastname + ","
                                + "\"totalprice\":" + totalprice + "}")
                        .log().all()));
    }
}
