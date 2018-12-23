package com.Project;

import java.io.File;
import java.util.*;

public class InvertedIndex {
    private Printer printer = new Printer();

    /*
     * Method to index the files.
     */
    public  Map<String, LinkedList<Integer>> index(String[] files){

        Scanner scanner;
        File file;
        Set<String> theDictionary = new HashSet<String>();
        List orderedDictionary;
        ArrayList<String[]> wordsArchive = new ArrayList<String[]>();
        Map<String, LinkedList<Integer>> invertedIndex = new HashMap<String, LinkedList<Integer>>();

        ///////////////////////

        System.out.println("Reading the file........");

        for(int i = 0; i < files.length; i++){
            try{
                file = new File("./" + files[i] + ".txt");
                scanner = new Scanner(file);
                ArrayList<String> readings = new ArrayList<String>();
                while (scanner.hasNext()){
                    String word = scanner.next().toLowerCase() ;
//                    PorterStemmer porterStemmer = new  PorterStemmer();
//                    word = porterStemmer.stemWord(word);
                    theDictionary.add(word);   //They are added to the global dictionary
                    readings.add(word);      //I know the words of each file
                }
                int numberOfWords = readings.size();    //To know the number of words for the text in which we are going
                String[] words = new String[numberOfWords];    //Fix for each one is made
                for (int j = 0; j < numberOfWords; j++) {
                    words[j] = readings.get(j);     //Add to
                }
                Arrays.sort(words);        //They are saved in alphabetical order
                wordsArchive.add(words);         //the set of words is added to the global repository
            }catch (Exception e){
                System.err.println("There is no such document :(");
            }
        }
        System.out.println("Finished reading.");

        orderedDictionary = new ArrayList(theDictionary);
        Collections.sort(orderedDictionary);

        ArrayList<Map<String,Integer>> mapDeMaps = new ArrayList<>();


        //
        for(int archive = 0; archive < wordsArchive.size(); archive++){
            LinkedList<Integer> post = new LinkedList<Integer>();
            String[] wordSets = wordsArchive.get(archive);
            String palabra = "";
            Map<String, Integer> dictionary = new HashMap<String, Integer>();
            for(int p = 0; p < wordSets.length;p++){
                palabra = wordSets[p];
                if(theDictionary.contains(palabra)){
                    dictionary.put(palabra, archive+1);
                }
            }
            mapDeMaps.add(dictionary);

        }

        for(Object w: orderedDictionary){      //Every word in the ordered dictionary
            String word = (String) w;
            LinkedList<Integer> post = new LinkedList<Integer>();
            for(int i = 0; i < mapDeMaps.size(); i++){   //Each dictionary
                Map<String, Integer> eachDictionary = mapDeMaps.get(i);

                String[] words = eachDictionary.keySet().toArray(new String[eachDictionary.size()]);
                Integer[] numDocs = eachDictionary.values().toArray(new Integer[eachDictionary.size()]);

                for(int j = 0; j < words.length; j++){
                    if(word.equals(words[j])){
                        post.add(numDocs[i] - 1);
                    }
                }
            }
            invertedIndex.put(word, post);

        }
        return invertedIndex;
    }

    public  Map<String, LinkedList<Integer>> indexStemmed(String[] files){

        Scanner scanner;
        File file;
        Set<String> theDictionary = new HashSet<String>();
        List orderedDictionary;
        ArrayList<String[]> wordsArchive = new ArrayList<String[]>();
        Map<String, LinkedList<Integer>> invertedIndex = new HashMap<String, LinkedList<Integer>>();

        ///////////////////////

        System.out.println("Reading the file........");

        for(int i = 0; i < files.length; i++){
            try{
                file = new File("./" + files[i] + ".txt");
                scanner = new Scanner(file);
                ArrayList<String> readings = new ArrayList<String>();
                while (scanner.hasNext()){
                    String word = scanner.next().toLowerCase() ;
                    PorterStemmer porterStemmer = new  PorterStemmer();
                    word = porterStemmer.stemWord(word);
                    theDictionary.add(word);   //They are added to the global dictionary
                    readings.add(word);      //I know the words of each file
                }
                int numberOfWords = readings.size();    //To know the number of words for the text in which we are going
                String[] words = new String[numberOfWords];    //Fix for each one is made
                for (int j = 0; j < numberOfWords; j++) {
                    words[j] = readings.get(j);     //Add to
                }
                Arrays.sort(words);        //They are saved in alphabetical order
                wordsArchive.add(words);         //the set of words is added to the global repository
            }catch (Exception e){
                System.err.println("There is no such document :(");
            }
        }
        System.out.println("Finished reading.");

        orderedDictionary = new ArrayList(theDictionary);
        Collections.sort(orderedDictionary);

        ArrayList<Map<String,Integer>> mapDeMaps = new ArrayList<>();


        //
        for(int archive = 0; archive < wordsArchive.size(); archive++){
            LinkedList<Integer> post = new LinkedList<Integer>();
            String[] wordSets = wordsArchive.get(archive);
            String palabra = "";
            Map<String, Integer> dictionary = new HashMap<String, Integer>();
            for(int p = 0; p < wordSets.length;p++){
                palabra = wordSets[p];
                if(theDictionary.contains(palabra)){
                    dictionary.put(palabra, archive+1);
                }
            }
            mapDeMaps.add(dictionary);

        }

        for(Object w: orderedDictionary){      //Every word in the ordered dictionary
            String word = (String) w;
            LinkedList<Integer> post = new LinkedList<Integer>();
            for(int i = 0; i < mapDeMaps.size(); i++){   //Each dictionary
                Map<String, Integer> eachDictionary = mapDeMaps.get(i);

                String[] words = eachDictionary.keySet().toArray(new String[eachDictionary.size()]);
                Integer[] numDocs = eachDictionary.values().toArray(new Integer[eachDictionary.size()]);

                for(int j = 0; j < words.length; j++){
                    if(word.equals(words[j])){
                        post.add(numDocs[i] - 1);
                    }
                }
            }
            invertedIndex.put(word, post);

        }
        return invertedIndex;
    }

    public   void printIndex(Map<String, LinkedList<Integer>> invertedIndex){
        int counter = 0;
        printer.print("********************************************");
        printer.print("The inverted index for the documents are:\n");
        printer.print("DICTIONARY             POSTINGS");
        for (String word: invertedIndex.keySet()) {
            printer.print(String.format("%-15s", word) + " -->    " + invertedIndex.get(word).toString());
            counter++;
        }
        printer.print("********************************************" + counter);
    }


}
