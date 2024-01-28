package main;

public class GameBuilder {
    private Settings settings = new Settings();
    public GameBuilder setBluePlayerName(String name){
        settings.setBluePlayerName(name);
        return this;
    }

    public GameBuilder setGreenPlayerName(String name){
        settings.setBluePlayerName(name);
        return this;
    }

    public GameBuilder setRedPlayerName(String name){
        settings.setBluePlayerName(name);
        return this;
    }

    public GameMain build() {
        GameMain game = new GameMain();
        game.setSettings(settings);

        return game;
    }
}
