package com.deutsch.util;

import com.deutsch.R;
import com.deutsch.locale.Country;
import com.deutsch.model.User;
import com.deutsch.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public class Util {


    public static Map<String, String> errorMessageMap;
    public static String ERROR_MESSAGE = "Invalid";
    public static Properties presentProperties;
    public static Properties pasProperties;
    public static Properties futureProperties;

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateUser(User user, String repeatPassword) {
        errorMessageMap = new HashMap<String, String>();
        if (user.getName() != null && user.getName().equals("")) {
            errorMessageMap.put("key", "verify user name");
            return false;
        }
        if (user.getEmail() != null && user.getEmail().equals("") ) {
            errorMessageMap.put("key", "invalid E-mail");
            return false;
        }
        if (user.getTelephone() != null && user.getTelephone().equals("")) {
            errorMessageMap.put("key", "invalid telephone");
            return false;
        }
        if (user.getPassword() != null && user.getPassword().equals("") ) {

            errorMessageMap.put("key", "invalid password");
            return false;

        }
        if (user.getPassword() != null && !user.getPassword().equals(repeatPassword)) {
            errorMessageMap.put("key", "password does not match");
            return false;

        }

        if (!isEmailValid(user.getEmail())) {

            errorMessageMap.put("key", "invalid E-mail");
            return false;
        }

        return true;
    }


    public static List<Country> getCountryList() {

        List<Country> countryList = new ArrayList<Country>();
        countryList.add(new Country("USA", R.drawable.us, +1,"en"));
        countryList.add(new Country("Germany", R.drawable.de, +49,"de"));
        countryList.add(new Country("Angola", R.drawable.ao, +244,"pt"));
        countryList.add(new Country("Brazil", R.drawable.br, +55,"pt"));
        countryList.add(new Country("Portugal", R.drawable.pt, +351,"pt"));

        return countryList;

    }

    public static  List<Word> localWordData(){

        List<Word> words = new ArrayList<>();
        Word word1 = new Word();
        word1.setWord("Hallo");

        word1.setGrammar("Substantiv");
        word1.setDefinition("umgangssprachlich, informell- lautes [freudiges] Rufen; allgemeine freudige Aufregung, Geschrei");
        word1.setOverView("umgangssprachlich, informell");
        word1.setTip("chen und –lein machen alles klein!- bildet mit Wörtern unterschiedlicher Wortart eine Interjektion");
        word1.setEg("Hallo! Welt");
        word1.setPhoneticAudio("no audio.mp3");
        word1.setPhoneticText("həˈloʊ");
        word1.setToEnglish("Hello");

        Word word2 = new Word();
        word2.setWord("Die");
        word2.setGrammar("Artikel");
        word2.setDefinition("Anmeldung, Plural: - en die Meldung EN; 1. Raum, in dem man sich anmeldet");
        word2.setOverView("bestimmter Artikel");
        word2.setTip("Adjektive: telefonisch, verbindlich, persönlich, rechtzeitig, schriftlich; Verben: erbeten, erfolgen, vorliegen ; Substantiv: Rückfrage, Information, Uhr, Datum; Nominativ die ; Genitiv der ; Dativ der ; Akkusativ die");
        word2.setEg("die Frau");
        word2.setPhoneticAudio("no audio.mp3");
        word2.setPhoneticText("di");
        word2.setToEnglish("The");
        word2.setNominative("die");
        word2.setAccusative("die");
        word2.setDative("der");
        word2.setGenitive("der");

        Word word3 = new Word();
        word3.setWord("Sein");
        word3.setGrammar("Verb");
        word3.setDefinition("irregular verb:");
        word3.setOverView("verb");
        word3.setTip("");
        word3.setEg("ich bin Marlene!");
        word3.setPhoneticAudio("no audio.mp3");
        word3.setPhoneticText("zein");
        word3.setToEnglish("to be");
        word3.setPresent("bin,bist,ist,sind,said,sind");
        word3.setPast("war,warst,war,waren,wart,waren");
        word3.setFuture("werde sein, wirst sein, wird sein, werden sein, werdet sein,werden sein");
        words.add(word1);
        words.add(word2);
        words.add(word3);

       return words;
    }

    public static void loadVerbProperty(Word word){
        presentProperties = getProperties(word.getPresent());
        pasProperties = getProperties(word.getPast());
        futureProperties = getProperties(word.getFuture());
    }

    public static Properties getProperties(String str){

        Properties properties = new Properties();
        StringTokenizer st = new StringTokenizer(str,",");
        if(st.hasMoreTokens()) {
            properties.setProperty("ich",st.nextToken());
            properties.setProperty("du",st.nextToken());
            properties.setProperty("er",st.nextToken());
            properties.setProperty("wir",st.nextToken());
            properties.setProperty("ihr",st.nextToken());
            properties.setProperty("Sie",st.nextToken());
        }
        return properties;
    }

}
