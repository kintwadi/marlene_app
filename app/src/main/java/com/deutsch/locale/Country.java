package com.deutsch.locale;

public class Country {

    private String name;
    private int flagImageId;
    private int dialCode;
    private String language;

    public Country(){

    }
    public Country(String name, int flagImageId,int dialCode,String language) {
        this.name = name;
        this.flagImageId = flagImageId;
        this.dialCode = dialCode;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlagImageId() {
        return flagImageId;
    }

    public void setFlagImageId(int flagImageId) {
        this.flagImageId = flagImageId;
    }

    public int getDialCode() {
        return dialCode;
    }

    public void setDialCode(int dialCode) {
        this.dialCode = dialCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country{");
        sb.append("name='").append(name).append('\'');
        sb.append(", flagImageId=").append(flagImageId);
        sb.append(", dialCode=").append(dialCode);
        sb.append(", language='").append(language).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
