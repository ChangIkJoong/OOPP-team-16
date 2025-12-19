package main.controller.inputs.commands;

import main.controller.facades.IGameActions;

public class JumpReleaseCommand implements Command {

    private final IGameActions actions;

    public JumpReleaseCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.jumpReleased();
    }
}
