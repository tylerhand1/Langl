package com.tyha.langl;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Locale;

public class CharacterBoxesRecViewAdapter extends RecyclerView.Adapter<CharacterBoxesRecViewAdapter.ViewHolder> {

    private ArrayList<CharacterBox> characterBoxes = new ArrayList<>();

    private Context context;

    public CharacterBoxesRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_box_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the text
        holder.characterTV.setText(characterBoxes.get(position).getLetter());

        // Set the background
        if (characterBoxes.get(position).getBackgroundColor() == Color.parseColor("#ffffff")) {
            // Set the background to white
            holder.characterTV.setBackgroundResource(R.drawable.edit_text_background);

        } else {
            // Set the background color to other cases
            holder.characterTV.setBackgroundColor(characterBoxes.get(position).getBackgroundColor());
        }

        // Set the text color
        holder.characterTV.setTextColor(characterBoxes.get(position).getTextColor());
    }

    @Override
    public int getItemCount() {
        return characterBoxes.size();
    }

    public void setCharacterBoxes(ArrayList<CharacterBox> characterBoxes) {
        this.characterBoxes = characterBoxes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView characterTV;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterTV = (TextView) itemView.findViewById(R.id.characterTV);

            parent = itemView.findViewById(R.id.parent);
        }
    }
}
