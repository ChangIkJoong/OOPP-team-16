package main.controller.inputs.commands;

import main.controller.Game;
import main.controller.Game.GameState;

public class GoToMenuCommand implements Command {

    private final Game game;

    public GoToMenuCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.setGameState(GameState.MENU);
    }
}

