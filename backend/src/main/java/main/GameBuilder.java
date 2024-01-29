package main;

import utility.Settings;

/**
 * GameBuilder used to build a new GameMain instance
 */
public class GameBuilder {
    private Settings settings = new Settings();

    public GameBuilder setBluePlayerName(String name){
        settings.setBluePlayerName(name);
        return this;
    }

    public GameBuilder setGreenPlayerName(String name){
        settings.setGreenPlayerName(name);
        return this;
    }

    public GameBuilder setRedPlayerName(String name){
        settings.setRedPlayerName(name);
        return this;
    }

    public GameMain build() {
        GameMain game = new GameMain();
        game.setSettings(settings);

        return game;
    }
}
