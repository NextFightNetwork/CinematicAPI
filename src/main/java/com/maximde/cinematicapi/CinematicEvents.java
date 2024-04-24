package com.maximde.cinematicapi;

public interface CinematicEvents {
    void onPointSwitch(int currentPoint, int currentScene);
    void onSceneSwitch(int prevScene, int nextScene);
    void onFinish();
    void onPlay();
}
