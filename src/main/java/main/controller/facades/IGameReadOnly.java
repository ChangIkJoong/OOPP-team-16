package main.controller.facades;

import audio.controller.AudioController;
import main.controller.Game;
import main.view.states.Leaderboard;
import main.view.states.MainMenu;

public interface IGameReadOnly {
    Game.GameState getGameState();

    MainMenu getMainMenu();

    Leaderboard getLeaderboard();

    AudioController getAudioController();
}
