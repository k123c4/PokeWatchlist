package com.example.pokewatchlist.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokewatchlist.R;
import com.bumptech.glide.Glide;

public class    ProfileFragment extends Fragment {

    private ImageView pokemonIV;
    private TextView nameTV;
    private TextView idTV;
    private TextView heightTV;
    private TextView weightTV;
    private TextView baseXpTV;
    private TextView moveTV;
    private TextView abilityTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        pokemonIV = root.findViewById(R.id.pokemonIV);
        nameTV = root.findViewById(R.id.nameTV);
        idTV = root.findViewById(R.id.idTV);
        heightTV = root.findViewById(R.id.heightTV);
        weightTV = root.findViewById(R.id.weightTV);
        baseXpTV = root.findViewById(R.id.baseXpTV);
        moveTV = root.findViewById(R.id.moveTV);
        abilityTV = root.findViewById(R.id.abilityTV);

        return root;

}
    public void clearProfile() {
        if (nameTV == null) {
            return;
        }

        nameTV.setText("Name:");
        idTV.setText("ID:");
        heightTV.setText("Height:");
        weightTV.setText("Weight:");
        baseXpTV.setText("Base XP:");
        moveTV.setText("Move:");
        abilityTV.setText("Ability:");
        if (pokemonIV != null) {
            pokemonIV.setImageDrawable(null);
        }
    }
    public void updateProfile(String name,
                              int id,
                              int height,
                              int weight,
                              int baseXp,
                              String move,
                              String ability,
                              String spriteUrl) {

        if (nameTV == null) {
            // View not created yet; nothing to update safely
            return;
        }

        nameTV.setText("Name: " + name);
        idTV.setText("ID: " + id);
        heightTV.setText("Height: " + height);
        weightTV.setText("Weight: " + weight);
        baseXpTV.setText("Base XP: " + baseXp);
        moveTV.setText("Move: " + move);
        abilityTV.setText("Ability: " + ability);
        if (pokemonIV != null) {
            if (spriteUrl != null && spriteUrl.length() > 0) {
                Glide.with(this)
                        .load(spriteUrl)
                        .into(pokemonIV);
            } else {
                pokemonIV.setImageDrawable(null);
            }
        }
    }
}
