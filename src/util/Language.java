package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public enum Language {
    ENGLISH(readWords("src\\data\\wordle.txt")), SLOVENE(readWords("src\\data\\besedle.txt"));

    final String[] words;

    Language(String[] words){
        this.words = words;
    }

    public String[] getWords() { return this.words; }

    public boolean containsWord(String word){
        for (String str: words) {
            if(str.equals(word.toLowerCase())) return true;
        }
        return false;
    }
    /*
    public HashSet<String> getWordsSet() {
        HashSet<String> set = new HashSet<>(Arrays.asList(words));
        return set;
    }

     */


    public static String[] readWords(String dat){
        try (BufferedReader br = new BufferedReader(new FileReader(dat))) {
            //BufferedReader in = new BufferedReader(new FileReader(dat));
            HashSet<String> words = new HashSet<>();
            while (br.ready()) {
                String row = br.readLine().trim();
                if (row.equals("")) continue;
                String[] wordsRow = row.split(",");
                words.addAll(Arrays.asList(wordsRow));
            }
            String[] arrayWords = words.toArray(new String[words.size()]);
            return arrayWords;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
