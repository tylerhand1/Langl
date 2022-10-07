package com.tyha.langl;

import android.graphics.Color;

public class CharacterBox {
    private String letter;
    private int position; // Determines the position of a box within a level
    private int level; // Determines the vertical level a box is in
    private int backgroundColor, textColor;
    private boolean correct;

    public CharacterBox(String letter, int position, int level, int backgroundColor, int textColor, boolean correct) {
        this.letter = letter;
        this.position = position;
        this.level = level;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getBackgroundColor() {
        switch (backgroundColor) {
            case -1:
                // Incorrect
                return Color.parseColor("#7f8c8d");
            case 1:
                // Correct, but wrong position
                return Color.parseColor("#f1c40f");
            case 2:
                // Correct
                return Color.parseColor("#27ae60");
            default:
                return Color.parseColor("#ffffff");
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        switch (textColor) {
            case 0:
                // Background is white -> text color is black
                return Color.parseColor("#000000");
            default:
                // Background is not white -> text color is white
                return Color.parseColor("#ffffff");
        }
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "CharacterBox{" +
                "letter=" + letter +
                ", position=" + position +
                '}';
    }
}
