package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class JumpPressCommand implements Command {

    private final IGameActions actions;

    public JumpPressCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.jumpPressed();
    }
}
