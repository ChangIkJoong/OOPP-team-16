package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class TogglePauseCommand implements Command {

    private final IGameActions actions;

    public TogglePauseCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.togglePause();
    }
}
