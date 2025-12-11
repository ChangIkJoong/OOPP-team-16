package main.model;

import java.util.ArrayList;
import java.util.List;

import Levels.LevelManager;
import entities.Player;
import main.Game.GameState;
import main.observerEvents.GameObserver;

public class GameModel {

    private final Player player;
    private final LevelManager levelManager;

    // Observer List
    private final List<GameObserver> observers = new ArrayList<>();

    private GameState gameState = GameState.MENU;

    // Transition State
    private boolean inTransition = false;
    private float transitionScale = 0f;
    private boolean scalingUp = true;
    private boolean isLevelLoaded = false;
    private final float TRANSITION_SPEED = 0.015f;

    // Logic Flags
    private boolean wasPlayerDead = false;

    // Stats
    private String playerName = "Player1";
    private long startTime;
    private int totalDeaths;

    private boolean paused = false;

    public GameModel(Player player, LevelManager levelManager) {
        this.player = player;
        this.levelManager = levelManager;
    }

    // --- Observer Pattern Logic ---
    public void addObserver(GameObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyPlayerDied() {
        for (GameObserver obs : observers) obs.onPlayerDied();
    }

    private void notifyPlayerRespawn() {
        for (GameObserver obs : observers) obs.onPlayerRespawn();
    }

    private void notifyLevelCompleted() {
        for (GameObserver obs : observers) obs.onLevelCompleted();
    }

    private void notifyLevelLoadRequested() {
        for (GameObserver obs : observers) obs.onLevelLoadRequested();
    }

    private void notifyTransitionComplete() {
        for (GameObserver obs : observers) obs.onTransitionComplete();
    }

    // --- Update Loop ---
    public void update() {
        if (inTransition) {
            updateTransition();
            return;
        }

        if (gameState == GameState.PLAYING) {
            updatePlaying();
        }
    }

    private void updatePlaying() {
        if (paused) return;

        player.update();
        levelManager.update();

        boolean isPlayerDead = player.getHitbox().x > 1500;

        if (!wasPlayerDead && isPlayerDead) {
            // Player just died
            totalDeaths++;
            notifyPlayerDied();
        } else if (wasPlayerDead && !isPlayerDead) {
            notifyPlayerRespawn();
        }
        wasPlayerDead = isPlayerDead;

        if (player.hasReachedLevelEnd()) {
            notifyLevelCompleted();
            startLevelTransition();
            player.resetLevelEnd();
        }
    }

    private void updateTransition() {
        if (scalingUp) {
            transitionScale += TRANSITION_SPEED;
            if (transitionScale >= 2f) {
                transitionScale = 2f;
                if (!isLevelLoaded) {
                    // Logic for swapping levels
                    levelManager.setLevelScore(player.getDeathCount());
                    player.resetDeathCount();
                    levelManager.loadNextLevel();
                    player.resetLevelEnd();

                    isLevelLoaded = true;
                    notifyLevelLoadRequested();
                }
                scalingUp = false;
            }
        } else {
            transitionScale -= TRANSITION_SPEED;
            if (transitionScale <= 0f) {
                transitionScale = 0f;
                inTransition = false;
                notifyTransitionComplete();
            }
        }
    }

    public void startLevelTransition() {
        inTransition = true;
        scalingUp = true;
        isLevelLoaded = false;
        transitionScale = 0f;
    }

    public void resetTransition() {
        inTransition = false;
        scalingUp = true;
        isLevelLoaded = false;
        transitionScale = 0f;
    }

    public void resetStats() {
        totalDeaths = 0;
        startTime = 0L;
    }

    public void startNewTimer() {
        totalDeaths = 0;
        startTime = System.nanoTime();
    }

    public void togglePause() {
        if (gameState == GameState.PLAYING && !inTransition) {
            paused = !paused;
        }
    }

    // --- Getters & Setters ---
    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isInTransition() {
        return inTransition;
    }

    public float getTransitionScale() {
        return transitionScale;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        if (name != null && !name.isBlank()) {
            this.playerName = name.trim();
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }
}