package com.example.pokewatchlist;

import android.os.Bundle;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.pokewatchlist.fragments.ProfileFragment;
import com.example.pokewatchlist.fragments.SearchFragment;
import com.example.pokewatchlist.fragments.WatchlistFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private WatchlistFragment watchlistFragment;
    private ProfileFragment profileFragment;

    // Data
    private ArrayList<PokemonSummary> watchlist;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        initFragments();
        initDataAndAdapter();
        wireSearchFragment();
        wireWatchlistFragment();

    }

    private void initFragments() {
        searchFragment = (SearchFragment)
                getSupportFragmentManager().findFragmentById(R.id.searchFragmentFCV);

        watchlistFragment = (WatchlistFragment)
                getSupportFragmentManager().findFragmentById(R.id.watchlistFragmentFCV);

        profileFragment = (ProfileFragment)
                getSupportFragmentManager().findFragmentById(R.id.profileFragmentFCV);
    }
    private void initDataAndAdapter() {
        watchlist = new ArrayList<PokemonSummary>();

        pokemonAdapter = new PokemonAdapter(this, watchlist);

        if (watchlistFragment != null) {
            watchlistFragment.setWatchlistAdapter(pokemonAdapter);
        }
    }
    private void wireSearchFragment() {
        if (searchFragment != null) {
            searchFragment.setSearchCallback(new SearchFragment.OnSearchRequestedListener() {
                @Override
                public void onSearchRequested(String query) {
                    handlePokemonSearch(query);
                }
            });
        }
    }
    private void wireWatchlistFragment() {
        if (watchlistFragment != null) {
            watchlistFragment.setOnWatchlistItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                    PokemonSummary selected = watchlist.get(position);

                    String query = selected.getName();
                    fetchPokemonFromApi(query);
                }
            });
        }
    }
    private void handlePokemonSearch(String query) {
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Please enter a Pokémon name or ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidInput(query)) {
            Toast.makeText(this, "Invalid input. Check characters or ID range.", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchPokemonFromApi(query);
    }

    private boolean isValidInput(String query) {
        String forbidden = "%&*()@!;:<>[";
        for (int i = 0; i < forbidden.length(); i++) {
            if (query.indexOf(forbidden.charAt(i)) >= 0) {
                return false;
            }
        }


        boolean isNumeric = true;
        for (int i = 0; i < query.length(); i++) {
            if (!Character.isDigit(query.charAt(i))) {
                isNumeric = false;
                break;
            }
        }

        if (isNumeric) {
            try {
                int value = Integer.parseInt(query);
                if (value < 1 || value > 1010) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private void fetchPokemonFromApi(final String query) {

        String url = "https://pokeapi.co/api/v2/pokemon/{pokemon}";

        AndroidNetworking.get(url)
                .addPathParameter("pokemon", query.toLowerCase().trim())
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(PokemonDetail.class, new ParsedRequestListener<PokemonDetail>() {
                    @Override
                    public void onResponse(PokemonDetail response) {
                        handlePokemonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this,
                                "Error fetching Pokémon. Check name/ID or network.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void handlePokemonResponse(PokemonDetail detail) {
        if (detail == null) {
            Toast.makeText(this, "No data returned from API.", Toast.LENGTH_SHORT).show();
            return;
        }

        addOrUpdateWatchlist(detail);
        android.util.Log.d("PokeWatchlist", "watchlist size = " + watchlist.size());
        pokemonAdapter.notifyDataSetChanged();

        updateProfileFromDetail(detail);
    }

    private void addOrUpdateWatchlist(PokemonDetail detail) {
        int id = detail.getId();
        String name = detail.getName();

        int size = watchlist.size();
        for (int i = 0; i < size; i++) {
            PokemonSummary existing = watchlist.get(i);
            if (existing.getId() == id) {
                return;
            }
        }

        PokemonSummary summary = new PokemonSummary(id, name);
        watchlist.add(summary);
    }

    private void updateProfileFromDetail(PokemonDetail detail) {
        if (profileFragment == null || detail == null) {
            return;
        }

        int id = detail.getId();
        String name = detail.getName();
        int height = detail.getHeight();
        int weight = detail.getWeight();
        int baseXp = detail.getBaseExperience();
        String moveName = detail.getFirstMoveName();
        String abilityName = detail.getFirstAbilityName();
        String spriteUrl = detail.getSpriteUrl();

        profileFragment.updateProfile(
                name,
                id,
                height,
                weight,
                baseXp,
                moveName,
                abilityName,
                spriteUrl
        );
    }
}