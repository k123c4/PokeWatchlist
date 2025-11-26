package com.example.pokewatchlist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokewatchlist.R;


public class WatchlistFragment extends Fragment {

    private ListView watchlistLV;

    private ListAdapter pendingAdapter;
    private AdapterView.OnItemClickListener pendingClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
        watchlistLV = root.findViewById(R.id.watchlistLV);

        if (pendingAdapter != null) {
            watchlistLV.setAdapter(pendingAdapter);
        }

        if (pendingClickListener != null) {
            watchlistLV.setOnItemClickListener(pendingClickListener);
        }
        return root;
    }
    public void setWatchlistAdapter(ListAdapter adapter) {
        pendingAdapter = adapter;
        if (watchlistLV != null) {
            watchlistLV.setAdapter(adapter);
        }
    }

    public void setOnWatchlistItemClickListener(AdapterView.OnItemClickListener listener) {

        pendingClickListener = listener;

        if (watchlistLV != null) {
            watchlistLV.setOnItemClickListener(listener);
        }
    }

    public ListView getWatchlistLV() {
        return watchlistLV;
    }

}