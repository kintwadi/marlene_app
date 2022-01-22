package com.deutsch.locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deutsch.R;

import java.util.List;

public class CountryAdaper extends ArrayAdapter<Country> {


    public CountryAdaper(Context context, List<Country> countries){
         super(context,0,countries);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View  initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_spinner_row,parent, false
            );
        }

        ImageView imageFlag = convertView.findViewById(R.id.image_view_flag);
        Country country = getItem(position);
        if(country != null){
            imageFlag.setImageResource(country.getFlagImageId());
        }

        return convertView;

    }
}
