package com.deutsch.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deutsch.R;
import com.deutsch.dialog.AppDialog;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;


public class SearchDetails extends Fragment {
    private static final String TAG = "SearchDetails";
    OnSearchDetailsMessageListener detailsMessageListener;
    TextView textViewBack;
    ImageView imageViewBack;
    TextView textViewWord;
    TextView textViewPhonetic;
    TextView definitionValue;
    TextView grammarValue;
    TextView exampleValue;
    TextView tipValue;
    Button verbDialog;
    View view = null;
    MediaPlayer player;

    public SearchDetails() {
        // Required empty public constructor
    }
    public interface OnSearchDetailsMessageListener{

        void back();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search_details, container, false);
        textViewBack = view.findViewById(R.id.text_view_back);
        imageViewBack = view.findViewById(R.id.image_back);

        textViewWord = view.findViewById(R.id.txt_word);
        textViewPhonetic = view.findViewById(R.id.txt_phonetic);

        grammarValue = view.findViewById(R.id.grammarValue);
        definitionValue = view.findViewById(R.id.definitionValue);
        exampleValue = view.findViewById(R.id.egValue);

        tipValue = view.findViewById(R.id.tipValue);
        verbDialog = view.findViewById(R.id.appDialog);

        readData();

        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsMessageListener.back();
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsMessageListener.back();
            }
        });
        textViewBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detailsMessageListener.back();
                return false;
            }
        });

        if(!Search.words.isEmpty()){
            ImageView play_button = (ImageView  ) view.findViewById(R.id.txt_sound);
            play_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String audio = Search.getWord().getPhoneticAudio();
                    if(!audio.isEmpty()){
                        play(audio);
                    }

                }
            });

            textViewWord.setText(Search.getWord().getWord());
            textViewPhonetic.setText(Search.getWord().getPhoneticText());
            definitionValue.setText(Search.getWord().getDefinition());
            grammarValue.setText(Search.getWord().getGrammar());
            exampleValue.setText(Search.getWord().getEg());
            tipValue.setText(Search.getWord().getTip());
            if(Search.word.getGrammar().toLowerCase().equals("verb") ||
                    Search.word.getGrammar().toLowerCase().equals("artikel")
            ){
                verbDialog.setVisibility(View.VISIBLE);
            }
        }

        verbDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVerbDialog();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity)context;
        detailsMessageListener = (OnSearchDetailsMessageListener)activity;
    }
    public void play(String audio) {

        if (player == null) {

            int id = getResourceId("raw", audio);
            player = MediaPlayer.create(getContext(), id);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(getContext(), "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
    }
    private  int getResourceId(String type, String resourceName) {

        Resources res = getContext().getResources();
        return res.getIdentifier(resourceName, type, getContext().getPackageName());
    }

    public void openVerbDialog(){
        if(Search.getWord().getGrammar().toLowerCase().equals("verb")){
            AppDialog appDialog = new AppDialog(Search.getWord());
            appDialog.show(getFragmentManager(), "app dialog");
        }



    }

    public  void csvLoader(String file)
    {
        try {

            //InputStream inputStream = getContext().getResources().openRawResource(R.raw.verbs);

           // getContext().getResources().

            FileReader filereader = new FileReader(file);
            // create csvReader object and skip first Line

            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            List<String[]> allData = csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                    Log.i(TAG, "seleted item: "+cell + "\t");
                }
                Log.i(TAG, "");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.getLocalizedMessage());
        }
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.verbs);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {

                Log.i("file line" , line);
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
}