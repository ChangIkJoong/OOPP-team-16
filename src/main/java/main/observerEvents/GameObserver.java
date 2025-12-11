package main.observerEvents;

public interface GameObserver {
    void onPlayerDied();
    void onPlayerRespawn();
    void onLevelCompleted();
    void onLevelLoadRequested();
    void onTransitionComplete();
}