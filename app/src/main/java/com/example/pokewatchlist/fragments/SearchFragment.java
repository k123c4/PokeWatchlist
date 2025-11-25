package com.example.pokewatchlist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokewatchlist.R;

public class SearchFragment extends Fragment {
    public interface OnSearchRequestedListener {
        void onSearchRequested(String query);
    }

    private OnSearchRequestedListener callback;

    private EditText searchET;
    private Button addBtn;

    public void setSearchCallback(OnSearchRequestedListener callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchET = root.findViewById(R.id.searchET);
        addBtn = root.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { String query = searchET.getText().toString().trim();
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(getContext(),
                            "Please enter a Pokémon name or ID.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (callback != null) {
                    callback.onSearchRequested(query);
                }
            }
        });

        return root;
    }
}