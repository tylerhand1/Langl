package com.tyha.langl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView characterBoxesRecView;
    private CharacterBoxesRecViewAdapter adapter;
    private Button btnEnter, btnBack;

    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    private ArrayList<CharacterBox> boxes;
    private int currentLevel, currentPosition, currentBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLevel = 0;
        currentPosition = 0;
        currentBox = 0;

        generateBoxes();
        generateKeyboardBtns();
        setLetterKeyBoardOnClickListeners();
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
        String guess = "";

        // Check if each box is filled in the current level
        int start;
        switch (currentLevel) {
            case 1:
                start = 5;
                break;
            case 2:
                start = 10;
                break;
            case 3:
                start = 15;
                break;
            case 4:
                start = 20;
                break;
            case 5:
                start = 25;
                break;
            default:
                start = 0;
                break;
        }
        for (int i = start; i < start + 5; i++) {
            if (boxes.get(i).getLetter().isEmpty()) {
                Toast.makeText(this, "You need to five letters", Toast.LENGTH_SHORT).show();
                return false;
            }
            guess += boxes.get(i).getLetter();
        }

        // Check guess word against others in database to filter nonsense

        String[] test = {"CHANT", "PLANT", "GRANT", "ROOMY", "CRUMB", "CALLS", "COLOR", "OOOOO", "OORRR"};
        boolean validWord = false;

        for (int i = 0; i < test.length; i++) {
            if (guess.equals(test[i])) {
                validWord = true;
            }
        }

        // Check guess word with current word of the game

        String correctWord = test[6];

        if (validWord) {
            if (guess.equals(correctWord)) {
                // Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 5; i++) {
                    // Change the color of the boxes
                    boxes.get(i + start).setBackgroundColor(2);
                    boxes.get(i + start).setTextColor(1);
                    boxes.get(i + start).setCorrect(true);
                    adapter.notifyItemChanged(i + start);

                    // Change the color of the buttons
                    int btnId = getResources().getIdentifier("btn" + boxes.get(i + start).getLetter(), "id", getPackageName());
                    Button btn = findViewById(btnId);
                    btn.setBackgroundColor(boxes.get(i + start).getBackgroundColor());
                    btn.setTextColor(Color.parseColor("#ffffff"));
                }
                disableKeyboardOnClickListeners();
            } else {
                if (currentLevel < 5) {
                    // Incorrect guess made, go to next row
                    currentLevel++;
                    currentBox++;
                    currentPosition = 0;

                } else {
                    // Too many incorrect guesses
                    Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show();
                    disableKeyboardOnClickListeners();

                }

                // Update the color of the TextViews and Buttons
                // Iterate over each character
                for (int i = 0; i < 5; i++) {
                    // Get the id for the button to change its color
                    int btnId = getResources().getIdentifier("btn" + boxes.get(i + start).getLetter(), "id", getPackageName());
                    Button btn = findViewById(btnId);
                    btn.setTextColor(Color.parseColor("#ffffff"));

                    if (guess.charAt(i) == correctWord.charAt(i)) {
                        // The character in the box is correct, set the TextView and Button background to green
                        // Toast.makeText(this, Character.toString(guess.charAt(i)), Toast.LENGTH_SHORT).show();
                        boxes.get(i + start).setBackgroundColor(2);
                        boxes.get(i + start).setTextColor(1);
                        boxes.get(i + start).setCorrect(true);
                        adapter.notifyItemChanged(i + start);

                        // Set background color of button to green
                        btn.setBackgroundColor(boxes.get(i + start).getBackgroundColor());

                    } else if (correctWord.indexOf(guess.charAt(i)) > 0) {
                        // The letter is in the word, but not in the correct position
                        // Toast.makeText(this, "Char " + Character.toString(guess.charAt(i)) + " found elsewhere", Toast.LENGTH_SHORT).show();
                        // Set box to yellow

                        boxes.get(i + start).setBackgroundColor(1);
                        boxes.get(i + start).setTextColor(1);
                        adapter.notifyItemChanged(i + start);

                        // Check if another box with the same letter is marked as correct
                        int count = 0;
                        for (int j = 0; j < 5; j++) {
                            if (guess.charAt(j) == correctWord.charAt(j)) {
                                break;
                            }
                            count++;
                        }

                        // Change the background color of the button to the background color of its corresponding box
                        btn.setBackgroundColor(boxes.get(i + start).getBackgroundColor());

                    } else {
                        // The letter is not in the correct word
                        // Toast.makeText(this, "Char not found at all", Toast.LENGTH_SHORT).show();
                        boxes.get(i + start).setBackgroundColor(-1);
                        boxes.get(i + start).setTextColor(1);
                        adapter.notifyItemChanged(i + start);

                        // Set background color of button to dark gray
                        btn.setBackgroundColor(boxes.get(i + start).getBackgroundColor());
                    }
                }
            }
        } else {
            // Word is not found in database
            Toast.makeText(this, "Word not found", Toast.LENGTH_SHORT).show();
        }
        // Count frequencies of yellow boxes, green boxes, and correct letters
        ArrayList<CharacterBox> boxesYellow = new ArrayList<>();
        ArrayList<CharacterBox> boxesGreen = new ArrayList<>();
        ArrayList<String> correctWordLetters = new ArrayList<>();
        for (int i = start; i < start + 5; i++) {
            if (boxes.get(i).getBackgroundColor() == Color.parseColor("#f1c40f")) {
                boxesYellow.add(boxes.get(i));
            }
            if (boxes.get(i).getBackgroundColor() == Color.parseColor("#27ae60")) {
                boxesGreen.add(boxes.get(i));
            }
            correctWordLetters.add(String.valueOf(correctWord.charAt(i - start)));
        }

        // Generate maps of their frequencies
        Map yellowLetterFrequencies = countFrequencies(boxesYellow);
        Map greenLetterFrequencies = countFrequencies(boxesGreen);
        Map correctWordLetterFrequencies = countCorrectFrequencies(correctWordLetters);

        // Compare the two maps
        for (int i = start; i < start + 5; i++) {
            // First check if letter is in both maps
            if (yellowLetterFrequencies.containsKey(boxes.get(i).getLetter()) && greenLetterFrequencies.containsKey(boxes.get(i).getLetter())) {
                // Compare the letter
                int yellowCount = (int) yellowLetterFrequencies.get(boxes.get(i).getLetter());
                int greenCount = (int) greenLetterFrequencies.get(boxes.get(i).getLetter());
                int correctCount = (int) correctWordLetterFrequencies.get(boxes.get(i).getLetter());
                int sum = yellowCount + greenCount;
                if (sum >= correctCount) {
                    // Sum is greater than or equal to frequency in correct word, need to change at least one yellow box to gray
                    int btnId = getResources().getIdentifier("btn" + boxes.get(i).getLetter(), "id", getPackageName());
                    Button btn = findViewById(btnId);
                    if (greenCount == correctCount) {
                        // Turn all yellow boxes gray
                        if (boxes.get(i).getBackgroundColor() != Color.parseColor("#27ae60")) {
                            boxes.get(i).setBackgroundColor(-1);
                            adapter.notifyItemChanged(i + start);
                        }
                    }
                    btn.setBackgroundColor(Color.parseColor("#27ae60"));
                }
            }
        }


        return true;
    }

    private static Map countFrequencies(ArrayList<CharacterBox> boxes) {
        Map<String, Integer> map = new HashMap<>();

        for (CharacterBox i : boxes) {
            Integer j = map.get(i.getLetter());
            map.put(i.getLetter(), (j == null) ? 1 : j + 1);
        }

        return map;
    }

    private static Map countCorrectFrequencies(ArrayList<String> letter) {
        Map<String, Integer> map = new HashMap<>();

        for (String i : letter) {
            Integer j = map.get(i);
            map.put(i, (j == null) ? 1 : j + 1);
        }

        return map;
    }

    private void disableKeyboardOnClickListeners() {
        for (int i = 0; i < letters.length; i++) {
            int btnId = getResources().getIdentifier("btn" + letters[i], "id", getPackageName());
            Button btn = findViewById(btnId);

            int finalI = i;
            btn.setOnClickListener(null);
        }
        btnBack.setOnClickListener(null);
        btnEnter.setOnClickListener(null);
    }
}