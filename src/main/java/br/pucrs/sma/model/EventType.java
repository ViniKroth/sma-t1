package br.pucrs.sma.model;

import java.io.Serializable;

public enum EventType implements Serializable {
    ARRIVAL,
    LEAVE,
    TRANSITION;

    public String getStatus() {
        return this.name();
    }
}


