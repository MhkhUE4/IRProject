package com.Project;

import java.io.File;
import java.util.*;

public class Main {


    /*
     * Method to index the files.
     */
    public static Map<String, LinkedList<Integer>> index(String[] files){

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



    /*
     * Methods to search, intersect and Booleans
     */
    public static LinkedList<Integer> SearchWord(String theWord, Map<String, LinkedList<Integer>> invertedIndex) {
        LinkedList<Integer> posts = new LinkedList<>();
        Iterator word = invertedIndex.keySet().iterator();
        while (word.hasNext()){
            String p = (String)word.next();
            if(p.equals(theWord)){
                posts = invertedIndex.get(p);
            }
        }
        return posts;
    }
    public static LinkedList<Integer> intersect(LinkedList<Integer> p1, LinkedList<Integer> p2){

        /*
         *   IMPLEMENTATION OF THE NEXT ALGORITHM:
         *
         *     INTERSECT(p1, p2)
         *        answer←⟨⟩
         *        while p1 ̸= NIL and p2 ̸= NIL
         *        do if docID(p1) = docID(p2)
         *             then ADD(answer, docID(p1))
         *                  p1 ← next(p1)
         *                  p2 ← next(p2)
         *             else if docID(p1) < docID(p2)
         *                  then p1 ← next(p1)
         *                  else p2 ← next(p2)
         *        return answer
         */

        LinkedList<Integer> answer = new LinkedList<>();
        LinkedList<Integer> pos1 = (LinkedList<Integer>) p1.clone();
        LinkedList<Integer> pos2 = (LinkedList<Integer>) p2.clone();

        while (!pos1.isEmpty() && !pos2.isEmpty()){
            if(pos1.getFirst() == pos2.getFirst()){
                answer.add(pos1.getFirst());
                pos1.removeFirst();
                pos2.removeFirst();
            }else{
                if (pos1.getFirst() < pos2.getFirst()){
                    pos1.removeFirst();
                }else{
                    pos2.removeFirst();
                }
            }
        }
        print("The searched words are in the file: " + answer.toString());
        return answer;
    }
    public static List<Integer> not(LinkedList<Integer> p1, LinkedList<Integer> p2){
        LinkedList<Integer> answer = (LinkedList<Integer>) p1.clone();
        LinkedList<Integer> remove = (LinkedList<Integer>) p2.clone();

        for (Integer i: remove) {
            if(answer.contains(i)){
                answer.remove(i);
            }
        }
        return answer;
    }
    public static LinkedList<Integer> or(LinkedList<Integer> p1, LinkedList<Integer> p2){
        LinkedList<Integer> answer = (LinkedList<Integer>) p1.clone();
        LinkedList<Integer> remove = (LinkedList<Integer>) p2.clone();

        for (Integer element: remove) {
            if(!answer.contains(element))
                answer.add(element);
        }
        Collections.sort(answer);
        return answer;
    }


    /*
     * Method to make the user interface in console
     */
    public static void menu(Map<String, LinkedList<Integer>> invertedIndex){
        boolean i = true;
        /*
         * word1 AND word2 AND NOT word3
         * word1 AND word2 OR word3
         * word1 OR word2 AND NOT word3
         */
        try {
            while (i) {
                Scanner scanner = new Scanner(System.in);
                print("________________________________________________");
                print("Type the number of one of the options below:\n");
                print("0) Print the inverted index.");
                print("1) word1 AND word2 AND NOT word3");
                print("2) word1 AND word2 OR word3");
                print("3) word1 OR word2 AND NOT word3");
                print("4) word1 AND word2");
                print("5) word1 OR word2");

                int option = scanner.nextInt();
                switch (option) {
                    case 0:
                        printIndex(invertedIndex);
                        print("");
                        break;
                    case 1:
                        print("word1 AND word2 AND NOT word3");
                        print("   word1: ");
                        String word1 = new Scanner(System.in).nextLine();
                        print("   word2: ");
                        String word2 = new Scanner(System.in).nextLine();
                        print("   word3: ");
                        String word3 = new Scanner(System.in).nextLine();
                        LinkedList<Integer> r1 = intersect(SearchWord(word1, invertedIndex), SearchWord(word2, invertedIndex));
                        print(word1 + " --> " + SearchWord(word1,invertedIndex).toString());
                        print(word2 + " --> " + SearchWord(word2,invertedIndex).toString());
                        print(word3 + " --> " + SearchWord(word3,invertedIndex).toString());
                        print("\nRESULT FOR THE QUERY:\n" + word1 + " AND " + word2 + " AND NOT " + word3 + " --> " + not(r1, SearchWord(word3, invertedIndex)).toString());
                        break;
                    case 2:
                        print("word1 AND word2 OR word3");
                        print("   word1: ");
                        String word21 = new Scanner(System.in).nextLine();
                        print("   word2: ");
                        String word22 = new Scanner(System.in).nextLine();
                        print("   word3: ");
                        String word23 = new Scanner(System.in).nextLine();
                        print(word21 + " --> " + SearchWord(word21,invertedIndex).toString());
                        print(word22 + " --> " + SearchWord(word22,invertedIndex).toString());
                        print(word23 + " --> " + SearchWord(word23,invertedIndex).toString());
                        LinkedList<Integer> and = intersect(SearchWord(word21, invertedIndex), SearchWord(word22, invertedIndex));
                        print("\nRESULT FOR THE QUERY:\n" + word21 + " AND " + word22 + " OR " + word23 + " --> " + or(and, SearchWord(word23, invertedIndex)).toString());
                        break;
                    case 3:
                        print("word1 OR word2 AND NOT word3");
                        print("   word1: ");
                        String word31 = new Scanner(System.in).nextLine();
                        print("   word2: ");
                        String word32 = new Scanner(System.in).nextLine();
                        print("   word3: ");
                        String word33 = new Scanner(System.in).nextLine();
                        LinkedList<Integer> or = or(SearchWord(word31,invertedIndex), SearchWord(word32,invertedIndex));
                        print(word31 + " --> " + SearchWord(word31,invertedIndex).toString());
                        print(word32 + " --> " + SearchWord(word32,invertedIndex).toString());
                        print(word33 + " --> " + SearchWord(word33,invertedIndex).toString());
                        print("\nRESULT FOR THE QUERY:\n" + word31 + " OR " + word32 + " AND NOT " + word33 + " --> " + not(or, SearchWord(word33, invertedIndex)).toString());
                        break;
                    case 4:
                        print("word1 AND word2");
                        print("   word1: ");
                        String word41 = new Scanner(System.in).nextLine();
                        print("   word2: ");
                        String word42 = new Scanner(System.in).nextLine();
                        print(word41 + " --> " + SearchWord(word41,invertedIndex).toString());
                        print(word42 + " --> " + SearchWord(word42,invertedIndex).toString());
                        print("\nRESULT FOR THE QUERY:\n" + word41 + " AND " + word42 + " --> " + intersect(SearchWord(word41, invertedIndex), SearchWord(word42, invertedIndex)).toString());
                        break;
                    case 5:
                        print("word1 OR word2");
                        print("   word1: ");
                        String word51 = new Scanner(System.in).nextLine();
                        print("   word2: ");
                        String word52 = new Scanner(System.in).nextLine();
                        print(word51 + " --> " + SearchWord(word51,invertedIndex).toString());
                        print(word52 + " --> " + SearchWord(word52,invertedIndex).toString());
                        print("\nRESULT FOR THE QUERY:\n" + word51 + " OR " + word52 + " --> " + or(SearchWord(word51, invertedIndex), SearchWord(word52, invertedIndex)).toString());
                        break;
                    default:
                        print("Only numbers bewteen 1 and 4");
                }
            }
        }catch (InputMismatchException e){
            System.err.println("Only numbers with the option acepted");
        }

    }


    /*
     * Main and methods for printing
     */
    public static void main(String[] args) {

        Map<String, LinkedList<Integer>> invertedIndex;

        ////////////


        int m = 0;
        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getName().endsWith(".txt")){
                    m++;
                };
            }
        }

        String files[] = new String[m];
        int n = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getName().endsWith(".txt")){
                    files[n] = file.getName().replaceFirst("[.][^.]+$", "");
                    n++;
                };
            }
        }


        invertedIndex = index(files);
        menu(invertedIndex);
    }

    private static void printIndex(Map<String, LinkedList<Integer>> invertedIndex){
        print("********************************************");
        print("The inverted index for the documents are:\n");
        print("DICTIONARY             POSTINGS" );
        for (String word: invertedIndex.keySet()) {
            print(String.format("%-15s", word) + " -->    " + invertedIndex.get(word).toString());
        }
        print("********************************************");
    }

    private static void print(String string){
        System.out.println(string);
    }
}

