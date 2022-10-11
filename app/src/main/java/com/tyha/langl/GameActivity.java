package com.tyha.langl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private RecyclerView characterBoxesRecView;
    private CharacterBoxesRecViewAdapter adapter;
    private Button btnEnter, btnBack;

    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    private ArrayList<CharacterBox> boxes;
    private int currentLevel, currentPosition, currentBox;
    private int lang, length;
    private static final String TAG = "GameActivity";

    private DataBaseHelper dataBaseHelper;

    private String correctWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        // To grab the chosen language
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            lang = extras.getInt("lang");
            length = extras.getInt("length");
            Log.d(TAG, "Lang num chosen: " + lang);
            Log.d(TAG, "Length: " + length);
        }

        // Disable auto screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        dataBaseHelper = new DataBaseHelper(this, lang, 5);

        correctWord = dataBaseHelper.getCorrectWord();

        currentLevel = 0;
        currentPosition = 0;
        currentBox = 0;

        generateBoxes();
        generateKeyboardBtns();

        setLetterKeyBoardOnClickListeners();
    }

    private static LinkedHashMap<String, Integer> countFrequencies(char[] word) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

        for (char i : word) {
            map.put(String.valueOf(i), map.containsKey(String.valueOf(i)) ? map.get(String.valueOf(i)) + 1 : 1);
        }

        return map;
    }

    private static LinkedHashMap<Integer, String> getPositions(char[] word) {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

        for (int i = 0; i < word.length; i++) {
            map.put(new Integer(i), String.valueOf(word[i]));
        }

        return map;
    }

    private void setLetterKeyBoardOnClickListeners() {
        for (int i = 0; i < letters.length; i++) {
            int btnId = getResources().getIdentifier("btn" + letters[i], "id", getPackageName());
            Button btn = findViewById(btnId);

            int finalI = i;
            btn.setOnClickListener((View v) -> {
                boxes.get(currentBox).setLetter(letters[finalI]);
                adapter.notifyItemChanged(currentBox);
                incrementPosition();
            });
        }

        // Back
        btnBack.setOnClickListener((View v) -> {
            if (boxes.get(currentBox).getLetter().isEmpty()) {
                // Current box is empty, go to previous one
                decrementPosition();
            }
            boxes.get(currentBox).setLetter("");
            adapter.notifyItemChanged(currentBox);

        });
        // Enter
        btnEnter.setOnClickListener((View v) -> makeGuess());
    }

    private void decrementPosition() {
        if (currentPosition > 0) {
            // Go back in position and currentBox count
            currentPosition--;
            currentBox--;
        }
    }

    private void incrementPosition() {
        // A letter has been inserted, so increment the currentBox and position
        if (currentPosition < 4) {
            // Current position is not at the end
            // So, increment it by one
            currentPosition++;
            currentBox++;
        }
    }

    private void generateKeyboardBtns() {
        btnEnter = findViewById(R.id.btnEnter);
        btnBack = findViewById(R.id.btnBack);
    }

    public void generateBoxes() {
        characterBoxesRecView = findViewById(R.id.characterBoxesRecView);

        boxes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                CharacterBox box = new CharacterBox("", j, i, 0, 0, false);

                boxes.add(box);
            }
        }

        adapter = new CharacterBoxesRecViewAdapter(this);
        adapter.setCharacterBoxes(boxes);

        characterBoxesRecView.setAdapter(adapter);
        characterBoxesRecView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    public boolean makeGuess() {
        StringBuilder guess = new StringBuilder();

        // Check if each box is filled in the current level
        int start = currentLevel * 5;

        for (int i = start; i < start + 5; i++) {
            if (boxes.get(i).getLetter().isEmpty()) {
                Toast.makeText(this, "You need to have five letters", Toast.LENGTH_SHORT).show();
                return false;
            }
            guess.append(boxes.get(i).getLetter());
        }

        // Check guess word against others in database to filter nonsense
        ArrayList<String> words = dataBaseHelper.getWords();
        boolean validWord = false;

        for (int i = 0; i < words.size(); i++) {
            if (guess.toString().equals(words.get(i))) {
                validWord = true;
            }
        }

        // Check guess word with current word of the game
        if (validWord) {
            // Generate hashmaps for the letter counts and positions for the guess and correct word
            LinkedHashMap<String, Integer> guessLetterFrequencies = countFrequencies(guess.toString().toCharArray());
            LinkedHashMap<Integer, String> guessLetterPositions = getPositions(guess.toString().toCharArray());

            LinkedHashMap<String, Integer> correctLetterFrequencies = countFrequencies(correctWord.toCharArray());
            LinkedHashMap<Integer, String> correctLetterPositions = getPositions(correctWord.toCharArray());

            Log.d(TAG, guessLetterFrequencies.toString());
            Log.d(TAG, guessLetterPositions.toString());
            Log.d(TAG, guess.toString());

            Log.d(TAG, correctLetterFrequencies.toString());
            Log.d(TAG, correctLetterPositions.toString());
            Log.d(TAG, correctWord);

            /*
                Iterate over positions hashmaps
                If letter same for both, set corresponding box to green and decrement corresponding frequencies
                Else do nothing
            */
            int iter = start;
            for (Map.Entry<Integer, String> pos : guessLetterPositions.entrySet()) {
                int btnId = getResources().getIdentifier("btn" + boxes.get(iter).getLetter(), "id", getPackageName());
                Button btn = findViewById(btnId);
                if (correctLetterPositions.containsKey(pos.getKey()) && pos.getValue().equals(correctLetterPositions.get(pos.getKey())) && correctLetterFrequencies.get(pos.getValue()) > 0) {
                    // Letter is in correct position
                    // Decrement frequency
                    Integer frequency = correctLetterFrequencies.get(pos.getValue());
                    correctLetterFrequencies.put(pos.getValue(), frequency - 1);
                    frequency = guessLetterFrequencies.get(pos.getValue());
                    guessLetterFrequencies.put(pos.getValue(), frequency - 1);

                    // Set color of keyboard button and box
                    // Change the color of the boxes
                    boxes.get(iter).setBackgroundColor(2);
                    boxes.get(iter).setTextColor(1);
                    boxes.get(iter).setCorrect(true);
                    adapter.notifyItemChanged(iter);
                } else if (!correctLetterPositions.containsValue(pos.getValue())) {
                    // Letter not in correct word
                    boxes.get(iter).setBackgroundColor(-1);
                    boxes.get(iter).setTextColor(1);
                    boxes.get(iter).setCorrect(true);
                    adapter.notifyItemChanged(iter);
                }
                btn.setBackgroundColor(boxes.get(iter).getBackgroundColor());
                btn.setTextColor(Color.parseColor("#ffffff"));
                iter++;
            }
            /*
                Iterate over frequency hashmaps
                If not in correct position and count greater than 0, set to yellow
                Else do nothing
            */
            iter = start;
            for (Map.Entry<Integer, String> pos : guessLetterPositions.entrySet()) {
                int btnId = getResources().getIdentifier("btn" + boxes.get(iter).getLetter(), "id", getPackageName());
                Button btn = findViewById(btnId);
                if (correctLetterPositions.containsValue(pos.getValue())) {
                    if (boxes.get(iter).getBackgroundColor() == -1) {
                        if (correctLetterFrequencies.get(pos.getValue()) > 0) {
                            // Letter exists and is in incorrect position
                            // Set color of keyboard button and box
                            // Change the color of the boxes
                            boxes.get(iter).setBackgroundColor(1);
                            boxes.get(iter).setTextColor(1);
                            boxes.get(iter).setCorrect(true);
                            adapter.notifyItemChanged(iter);

                            btn.setBackgroundColor(boxes.get(iter).getBackgroundColor());
                        } else {
                            System.out.println(correctLetterFrequencies.get(pos.getValue()));
                            boxes.get(iter).setBackgroundColor(-1);
                            boxes.get(iter).setTextColor(1);
                            boxes.get(iter).setCorrect(true);
                            adapter.notifyItemChanged(iter);
                        }
                    }
                }
                btn.setBackgroundColor(boxes.get(iter).getBackgroundColor());
                btn.setTextColor(Color.parseColor("#ffffff"));
                iter++;
            }
            if (guess.toString().equals(correctWord)) {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                disableKeyboardOnClickListeners();
                return true;
            } else {
                if (currentLevel < 5) {
                    // Incorrect guess made, go to next row
                    currentLevel++;
                    currentBox++;
                    currentPosition = 0;
                } else {
                    // Too many incorrect guesses
                    Toast.makeText(this, "You lose. The word was " + correctWord, Toast.LENGTH_SHORT).show();
                    disableKeyboardOnClickListeners();
                }
            }
        } else {
            Toast.makeText(this, "Word not found.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void disableKeyboardOnClickListeners() {
        for (int i = 0; i < letters.length; i++) {
            int btnId = getResources().getIdentifier("btn" + letters[i], "id", getPackageName());
            Button btn = findViewById(btnId);
            btn.setOnClickListener(null);
        }
        btnBack.setOnClickListener(null);
        btnEnter.setOnClickListener(null);
    }
}