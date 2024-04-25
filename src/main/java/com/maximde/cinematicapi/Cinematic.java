package com.maximde.cinematicapi;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class Cinematic {

    public final HashMap<Integer, Scene> sceneHashMap = new HashMap<>();
    @Getter
    private final String name;
    @Getter
    private Settings settings = new Settings();
    public List<Effect> effects;

    @Getter
    private List<Player> players;
    @Setter @Getter
    private ItemDisplay display;
    private int globalDistance;
    private boolean simulate = false;
    @Getter
    private final CinematicEvents cinematicEvents;

    @Getter @Setter
    private HashMap<UUID, GameMode> prevGameModes = new HashMap<>();

    public Cinematic(String name) {
        this.name = name;
        this.cinematicEvents = new CinematicEvents() {
            public void onPointSwitch(int currentPoint, int currentScene) {}
            public void onSceneSwitch(int prevScene, int nextScene) {}
            public void onFinish() {}
            public void onPlay() {}
        };
    }

    public Cinematic(String name, CinematicEvents cinematicEvents) {
        this.name = name;
        this.cinematicEvents = cinematicEvents;
    }

    public void setPlayers(List<Player> players, boolean simulate) {
        this.players = players;
        this.simulate = simulate;
        if(!simulate) setPrevGameModes(players);
    }

    public void setPrevGameModes(List<Player> players) {
        players.forEach(player -> prevGameModes.put(player.getUniqueId(), player.getGameMode()));
    }

    public void load() {
        AtomicInteger globalDistance = new AtomicInteger();

        this.sceneHashMap.values().forEach(scene1 -> {
            for(int i = 0; i < scene1.getLocations().size() -1; i++) {
                globalDistance.addAndGet((int) scene1.getLocations().get(i).distance(scene1.getLocations().get(i + 1)));
            }
        });
        this.globalDistance = globalDistance.get();
    }

    @Getter @Setter @Accessors(chain = true)
    public static class Scene {
        private long duration = 20 * 5;
        private int endDelay = 0;
        private boolean fadeIn = true;
        private boolean fadeOut = true;
        private List<Location> locations = new ArrayList<>();
        public Scene addLocation(Location location) {
            locations.add(location);
            return this;
        }
    }

    @Getter
    @Setter
    public static class Settings {
        private int fov = 0;
        private int startDelay = 0;
        /**
         * This world is applied to all locations in the scenes
         */
        private String world = "world";
        private boolean bars = true;
        private boolean fadeIn;
        private boolean fadeOut;
    }

    public int getLocationSwitchDuration(int sceneNum, int currentLocation) {
        Scene scene = this.sceneHashMap.get(sceneNum);
        if (scene == null) {
            return 20;
        }

        List<Location> locations = scene.getLocations();
        int sceneDistance = calculateSceneDistance(locations);

        if (currentLocation >= 0 && currentLocation < locations.size() - 1) {
            int distance = calculateDistance(locations, currentLocation);
            double durationRatio = (double) distance / sceneDistance;
            return (int) (durationRatio * scene.getDuration());
        } else {
            return 20;
        }
    }

    private int calculateSceneDistance(List<Location> locations) {
        int sceneDistance = 0;
        for (int i = 0; i < locations.size() - 1; i++) {
            sceneDistance += (int) locations.get(i).distance(locations.get(i + 1));
        }
        return sceneDistance;
    }

    private int calculateDistance(List<Location> locations, int currentLocation) {
        return (int) locations.get(currentLocation).distance(locations.get(currentLocation + 1));
    }


    public static class Effect {

    }
}
