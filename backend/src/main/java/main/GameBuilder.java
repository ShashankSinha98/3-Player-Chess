package main;

import utility.Settings;

/**
 * GameBuilder used to build a new GameMain instance
 */
public class GameBuilder {
    private Settings settings = new Settings();

    /**
     * set the name of the blue player
     * @param name the name of the blue player
     * @return actual GameBuilder
     */
    public GameBuilder setBluePlayerName(String name){
        settings.setBluePlayerName(name);
        return this;
    }

    /**
     * set the name of the green player
     * @param name the name of the green player
     * @return actual GameBuilder
     */
    public GameBuilder setGreenPlayerName(String name){
        settings.setGreenPlayerName(name);
        return this;
    }

    /**
     * set the name of the red player
     * @param name the name of the red player
     * @return actual GameBuilder
     */
    public GameBuilder setRedPlayerName(String name){
        settings.setRedPlayerName(name);
        return this;
    }

    /**
     * builds a new GameMain instance
     * @return newly created GameMain instance
     */
    public GameMain build() {
        GameMain game = new GameMain();
        game.setSettings(settings);

        return game;
    }
}
