package main.controller.inputs.commands;

import main.controller.Game;

public class TogglePauseCommand implements Command {

    private final Game game;

    public TogglePauseCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.togglePause();
    }
}

