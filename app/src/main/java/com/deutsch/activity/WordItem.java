package com.deutsch.activity;

public class WordItem {


    private String word;
    private String grammar;
    private String overView;


  public WordItem(){

  }
    public WordItem(String word, String overView, String grammar) {

        this.word = word;
        this.overView = overView;
        this.grammar = grammar;
    }


    public String getWord() {
        return word;
    }

    public String getOverView() {
        return overView;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }
    public String getGrammar() {
        return grammar;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }


}
