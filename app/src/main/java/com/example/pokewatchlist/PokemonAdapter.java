package com.example.pokewatchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PokemonAdapter extends ArrayAdapter<PokemonSummary> {

    private LayoutInflater inflater;

    public PokemonAdapter(@NonNull Context context, @NonNull List<PokemonSummary> items) {
        super(context, android.R.layout.simple_list_item_2, items);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new ViewHolder();

            holder.nameTV = row.findViewById(android.R.id.text1);
            holder.idTV = row.findViewById(android.R.id.text2);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        PokemonSummary item = getItem(position);

        if (item != null) {
            holder.nameTV.setText(item.getName());
            holder.idTV.setText("ID: " + item.getId());
        }

        return row;
    }

    private static class ViewHolder {
        TextView nameTV;
        TextView idTV;
    }
}
