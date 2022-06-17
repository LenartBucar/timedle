package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

public enum Language {
    ENGLISH(readWords("src\\data\\wordle.txt")), SLOVENE(readWords("src\\data\\besedle.txt"));

    final String[] words;

    Language(String[] words){
        this.words = words;
    }

    public String[] getWords() { return this.words; }

    /**
     * checks whether wordlist contains word
     * @param word word to check in lower or upper case letters
     * @return
     */
    public boolean containsWord(String word){
        String w = word.toLowerCase();
        for (String str: words) {
            if(str.equals(w)) return true;
        }
        return false;
    }

    /**
     * reads wordlist from dat
     * @param dat name of dat to read wordlist from
     * @return array of word in wordlist
     */
    private static String[] readWords(String dat){
        try (BufferedReader br = new BufferedReader(new FileReader(dat))) {
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
