package ru.simul.world;

public enum Names {

    GRASS("☘\uFE0F"),
    TREE("\uD83C\uDF33"),
    ROCK("\u26F0"),
    HERBIVORE("\uD83D\uDC30"),
    PREDATOR("\uD83D\uDC3A"),
    CELL("\uD83D\uDFEB");

    private final String text;

    Names(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
