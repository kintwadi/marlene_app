package com.deutsch.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deutsch.R;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordsViewHolder> implements Filterable {

    private final List<WordItem> wordItems;
    private final List<WordItem> wordItemsFull;
    private OnItemClickListener mListener;
    public WordAdapter(List<WordItem> wordItems){
        this.wordItems = wordItems;
        wordItemsFull = new ArrayList<>(wordItems);
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }
    public static class WordsViewHolder extends RecyclerView.ViewHolder{


        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public WordsViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public WordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        WordsViewHolder wordsViewHolder = new WordsViewHolder(view,mListener);
        return wordsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordsViewHolder holder, int position) {

        WordItem currentWordItem = wordItems.get(position);
        holder.mTextView1.setText(currentWordItem.getWord());
        holder.mTextView2.setText(currentWordItem.getOverView());
        holder.mTextView3.setText(currentWordItem.getGrammar());

    }

    @Override
    public int getItemCount() {
        return wordItems.size();
    }

    @Override
    public Filter getFilter() {
        return wordsFilter;
    }

    private final Filter wordsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<WordItem> filteredList = new ArrayList<>();
           if(constraint == null || constraint.length() == 0){
               filteredList.addAll(wordItemsFull);
           }else{
              String filterPattern = constraint.toString().toLowerCase().trim();
              for(WordItem word : wordItemsFull){
                  if(word.getWord().toLowerCase().contains(filterPattern)){
                      filteredList.add(word);
                  }
               }
           }
           FilterResults results = new FilterResults();
           results.values = filteredList;
           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            wordItems.clear();
            wordItems.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
