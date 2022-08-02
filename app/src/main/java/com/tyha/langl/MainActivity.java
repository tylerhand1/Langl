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
    private Button btnQ, btnW, btnE, btnR, btnT, btnY, btnU, btnI, btnO, btnP, btnA, btnS, btnD,
            btnF, btnG, btnH, btnJ, btnK, btnL, btnZ, btnX, btnC, btnV, btnB, btnN, btnM, btnEnter,
            btnBack;

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
        // First row

        // Q
        btnQ.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("Q");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // W
        btnW.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("W");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // E
        btnE.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("E");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // R
        btnR.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("R");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // T
        btnT.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("T");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // Y
        btnY.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("Y");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // U
        btnU.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("U");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // I
        btnI.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("I");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // O
        btnO.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("O");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // P
        btnP.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("P");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });

        // Second row

        // A
        btnA.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("A");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // S
        btnS.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("S");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // F
        btnF.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("F");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // D
        btnD.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("D");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // G
        btnG.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("G");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // H
        btnH.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("H");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // J
        btnJ.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("J");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // K
        btnK.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("K");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // L
        btnL.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("L");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });

        // Third row

        // Z
        btnZ.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("Z");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // X
        btnX.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("X");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // C
        btnC.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("C");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // V
        btnV.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("V");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // B
        btnB.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("B");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // N
        btnN.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("N");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
        // M
        btnM.setOnClickListener((View v) -> {
            boxes.get(currentBox).setLetter("M");
            adapter.notifyItemChanged(currentBox);
            incrementPosition();
        });
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
        btnQ = findViewById(R.id.btnQ);
        btnW = findViewById(R.id.btnW);
        btnE = findViewById(R.id.btnE);
        btnR = findViewById(R.id.btnR);
        btnT = findViewById(R.id.btnT);
        btnY = findViewById(R.id.btnY);
        btnU = findViewById(R.id.btnU);
        btnI = findViewById(R.id.btnI);
        btnO = findViewById(R.id.btnO);
        btnP = findViewById(R.id.btnP);
        btnA = findViewById(R.id.btnA);
        btnS = findViewById(R.id.btnS);
        btnD = findViewById(R.id.btnD);
        btnF = findViewById(R.id.btnF);
        btnG = findViewById(R.id.btnG);
        btnH = findViewById(R.id.btnH);
        btnJ = findViewById(R.id.btnJ);
        btnK = findViewById(R.id.btnK);
        btnL = findViewById(R.id.btnL);
        btnZ = findViewById(R.id.btnZ);
        btnX = findViewById(R.id.btnX);
        btnC = findViewById(R.id.btnC);
        btnV = findViewById(R.id.btnV);
        btnB = findViewById(R.id.btnB);
        btnN = findViewById(R.id.btnN);
        btnM = findViewById(R.id.btnM);
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
        // repeats will be used to determine the repetition count for each letter

        if (validWord) {
            if (guess.equals(correctWord)) {
                // Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 5; i++) {
                    boxes.get(i + start).setBackgroundColor(2);
                    boxes.get(i + start).setTextColor(1);
                    boxes.get(i + start).setCorrect(true);
                    adapter.notifyItemChanged(i + start);
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
                        // Check for repeats
                        int repeatCount = 0;
                        for (int j = 0; j < 5; j++) {
                            if (boxes.get(j + start).getBackgroundColor() == Color.parseColor("#f1c40f")) {
                                repeatCount++;
                            }
                        }
                        int repeatLetterCount = 0;
                        for (int j = 0; j < 5; j++) {
                            if (guess.charAt(i) == correctWord.charAt(j)) {
                                repeatCount++;
                            }
                        }
                        boxes.get(i + start).setBackgroundColor(1);
                        boxes.get(i + start).setTextColor(1);
                        adapter.notifyItemChanged(i + start);

                        int count = 0;
                        for (int j = 0; j < 5; j++) {
                            if (guess.charAt(j) == correctWord.charAt(j)) {
                                break;
                            }
                            count++;
                        }

                        // Check if the letter is repeat and already in correct slot
                        // If it is in correct slot previously, keep button green
                        // Else, change button to yellow
                        if (!boxes.get(count + start).isCorrect()) {
                            // Change background color of the button to yellow
                            btn.setBackgroundColor(boxes.get(i + start).getBackgroundColor());
                        }


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
        // Count frequencies of yellow boxes
        ArrayList<CharacterBox> boxesYellow = new ArrayList<>();
        for (int i = start; i < start + 5; i++) {
            if (boxes.get(i).getBackgroundColor() == Color.parseColor("#f1c40f")) {
                boxesYellow.add(boxes.get(i));
            }
        }
        // Count frequencies of green boxes
        ArrayList<CharacterBox> boxesGreen = new ArrayList<>();
        for (int i = start; i < start + 5; i++) {
            if (boxes.get(i).getBackgroundColor() == Color.parseColor("#27ae60")) {
                boxesGreen.add(boxes.get(i));
            }
        }
        // Count frequencies of letters in correct word
        ArrayList<String> correctWordLetters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            correctWordLetters.add(String.valueOf(correctWord.charAt(i)));
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
                    if (greenCount == correctCount) {
                        // Turn all yellow boxes gray
                        if (boxes.get(i).getBackgroundColor() != Color.parseColor("#27ae60")) {
                            boxes.get(i).setBackgroundColor(-1);
                            adapter.notifyItemChanged(i + start);
                        }
                    } else {
                        System.out.println(greenCount + " " + correctCount);
                        if (greenCount != 0) {
                            // May need to keep some yellow boxes yellow

                        }
                    }
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
        btnQ.setOnClickListener(null);
        btnW.setOnClickListener(null);
        btnE.setOnClickListener(null);
        btnR.setOnClickListener(null);
        btnT.setOnClickListener(null);
        btnY.setOnClickListener(null);
        btnU.setOnClickListener(null);
        btnI.setOnClickListener(null);
        btnO.setOnClickListener(null);
        btnP.setOnClickListener(null);
        btnA.setOnClickListener(null);
        btnS.setOnClickListener(null);
        btnD.setOnClickListener(null);
        btnF.setOnClickListener(null);
        btnG.setOnClickListener(null);
        btnH.setOnClickListener(null);
        btnJ.setOnClickListener(null);
        btnK.setOnClickListener(null);
        btnL.setOnClickListener(null);
        btnZ.setOnClickListener(null);
        btnX.setOnClickListener(null);
        btnC.setOnClickListener(null);
        btnV.setOnClickListener(null);
        btnB.setOnClickListener(null);
        btnN.setOnClickListener(null);
        btnM.setOnClickListener(null);
        btnBack.setOnClickListener(null);
        btnEnter.setOnClickListener(null);
    }
}