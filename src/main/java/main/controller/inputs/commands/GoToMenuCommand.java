package main.controller.inputs.commands;

import main.controller.api.IGameActions;

public class GoToMenuCommand implements Command {

    private final IGameActions actions;

    public GoToMenuCommand(IGameActions actions) {
        this.actions = actions;
    }

    @Override
    public void execute() {
        actions.goToMenu();
    }
}
