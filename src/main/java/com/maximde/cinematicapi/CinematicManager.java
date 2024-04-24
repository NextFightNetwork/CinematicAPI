package com.maximde.cinematicapi;

import org.bukkit.entity.Player;

import java.util.List;

public interface CinematicManager {
    void playCinematic(List<Player> players, String cinematicName, int duration);
    void playCinematic(List<Player> players, Cinematic cinematic);

    /**
     * Save a cinematic in the config file
     * @param cinematic
     */
    void saveCinematic(Cinematic cinematic);

    /**
     * Delete a cinematic from the config file
     * @param name
     */
    void deleteCinematic(String name);
}
