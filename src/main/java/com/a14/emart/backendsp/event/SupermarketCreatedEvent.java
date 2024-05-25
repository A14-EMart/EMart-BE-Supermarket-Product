package com.a14.emart.backendsp.event;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupermarketCreatedEvent {
    private Long pengelola;

    public SupermarketCreatedEvent() {
    }

    public SupermarketCreatedEvent(Long pengelola) {
        this.pengelola = pengelola;
    }
}

