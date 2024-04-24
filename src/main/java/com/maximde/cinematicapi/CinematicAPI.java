package com.maximde.cinematicapi;

import lombok.Getter;

public class CinematicAPI {

    @Getter
    private CinematicManager cinematicManager;

    private static CinematicAPI cinematicAPI;

    public CinematicAPI(CinematicManager cinematicManager) {
        this.cinematicManager = cinematicManager;
        cinematicAPI = this;
    }

    public static void setAPI(CinematicManager manager) {
        if (cinematicAPI == null) {
            cinematicAPI = new CinematicAPI(manager);
        } else {
            cinematicAPI.cinematicManager = manager;
        }
    }

    public static CinematicAPI getInstance() {
        return cinematicAPI;
    }
}
