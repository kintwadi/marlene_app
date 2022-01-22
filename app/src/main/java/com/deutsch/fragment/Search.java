package com.deutsch.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.deutsch.R;
import com.deutsch.activity.WordAdapter;
import com.deutsch.activity.WordItem;
import com.deutsch.model.Word;
import com.deutsch.service.AppService;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment implements SearchView.OnQueryTextListener {
    private static final String TAG = "Search";
    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  List<WordItem> wordItems;
    private SearchView wordSearch;
    private OnSearchMessageListener messageSendListener;
    private static  int position = 0;
    private static String searchedWord = "";
    public static   List<Word>  words ;
    public static Word word;

    public Search() {
        // Required empty public constructor
        words = new ArrayList<>();
        word = new Word();
    }
    public interface OnSearchMessageListener {

        void onSearchMessageSend(int position, String message);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        wordItems = new ArrayList<>();
        AppService appService = new AppService(getContext());
        words =  appService.findAllWords();
        for(Word word: words){
            WordItem wordItem  = new WordItem();
            wordItem.setWord(word.getWord());
            wordItem.setOverView(word.getOverView());
            wordItem.setGrammar(word.getGrammar());
            wordItems.add(wordItem);
        }

        //Log.i(TAG, "this words: "+ words);


        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);
        wordSearch = rootView.findViewById(R.id.word_search_action);
        wordSearch.setOnQueryTextListener(this);
        BuildRecyclerView(rootView );

        return rootView;
    }
    public void BuildRecyclerView(View rootView ){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.words_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new WordAdapter(wordItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                messageSendListener.onSearchMessageSend(position,"searchViewAction");
                Search.position = position;
                AppService appService = new AppService(getContext());
                if(searchedWord.equals("")){
                    words =  appService.findAllWords();
                    //Log.i(TAG, " All onItemClick words: "+ words.toString());
                }else{
                    words = fillSearchedWord(searchedWord);
                    //Log.i(TAG, " search onItemClick words: "+ words.toString());
                }
                word = words.get(position);
                //Log.i(TAG, " onItemClick: "+ position);
            }
        });
    }
    public WordAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        searchedWord = newText;
        words = fillSearchedWord(newText);
        Log.i(TAG, "searched : "+newText);
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity)context;
        try {
            messageSendListener = (OnSearchMessageListener) activity;
        }catch (ClassCastException e){
            Log.i(TAG, "error : "+e.getMessage());
        }
    }
    public  List<Word> fillSearchedWord(String searchWord){
        //Log.i(TAG, "this searchWord : "+searchWord);
        AppService appService = new AppService(getContext());
        return appService.findWords(searchWord);

    }
    public static Word getWord(){
        return word;
    }

       /*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    */
}