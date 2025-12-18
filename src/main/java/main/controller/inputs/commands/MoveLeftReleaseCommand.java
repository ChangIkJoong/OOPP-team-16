package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class MoveLeftReleaseCommand implements Command {

    private final IGameActions actions;

    public MoveLeftReleaseCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.moveLeftReleased();
    }
}
