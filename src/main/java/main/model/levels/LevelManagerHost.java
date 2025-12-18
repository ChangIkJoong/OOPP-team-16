package main.model.levels;

import audio.controller.AudioController;
import main.controller.Game;
import main.model.entities.entity.Player;

/**
 * Small dependency interface for {@link LevelManager}.
 *
 * This replaces the direct dependency on {@code main.controller.Game} and allows the model
 * layer to interact with only what it actually needs.
 */
public interface LevelManagerHost {

    Player getPlayer();

    AudioController getAudioController();

    void reloadPlayerCurrentLevel();

    void setGameState(Game.GameState newState);
}

