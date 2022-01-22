package com.deutsch.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"word"},unique = true)})
public class Word implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String overView;
    private String grammar;
    private String definition;
    private String tip;
    private String eg;
    private String phoneticText;
    private String phoneticAudio;
    private String toEnglish;
    private String present;
    private String past;
    private String future;
    private String nominative;
    private String accusative;
    private String dative;
    private String genitive;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getEg() {
        return eg;
    }

    public void setEg(String eg) {
        this.eg = eg;
    }

    public String getPhoneticText() {
        return phoneticText;
    }

    public void setPhoneticText(String phoneticText) {
        this.phoneticText = phoneticText;
    }

    public String getPhoneticAudio() {
        return phoneticAudio;
    }

    public void setPhoneticAudio(String phoneticAudio) {
        this.phoneticAudio = phoneticAudio;
    }

    public String getToEnglish() {
        return toEnglish;
    }

    public void setToEnglish(String toEnglish) {
        this.toEnglish = toEnglish;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getNominative() {
        return nominative;
    }

    public void setNominative(String nominative) {
        this.nominative = nominative;
    }

    public String getAccusative() {
        return accusative;
    }

    public void setAccusative(String accusative) {
        this.accusative = accusative;
    }

    public String getDative() {
        return dative;
    }

    public void setDative(String dative) {
        this.dative = dative;
    }

    public String getGenitive() {
        return genitive;
    }

    public void setGenitive(String genitive) {
        this.genitive = genitive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Word{");
        sb.append("id=").append(id);
        sb.append(", word='").append(word).append('\'');
        sb.append(", overView='").append(overView).append('\'');
        sb.append(", grammar='").append(grammar).append('\'');
        sb.append(", definition='").append(definition).append('\'');
        sb.append(", tip='").append(tip).append('\'');
        sb.append(", eg='").append(eg).append('\'');
        sb.append(", phoneticText='").append(phoneticText).append('\'');
        sb.append(", phoneticAudio='").append(phoneticAudio).append('\'');
        sb.append(", toEnglish='").append(toEnglish).append('\'');
        sb.append(", present='").append(present).append('\'');
        sb.append(", past='").append(past).append('\'');
        sb.append(", future='").append(future).append('\'');
        sb.append(", nominative='").append(nominative).append('\'');
        sb.append(", accusative='").append(accusative).append('\'');
        sb.append(", dative='").append(dative).append('\'');
        sb.append(", genitive='").append(genitive).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
