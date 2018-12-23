package com.Project;

import java.util.*;

public class Compare {
    private Printer printer = new Printer();

    private   LinkedList<Integer> SearchWord(String theWord, Map<String, LinkedList<Integer>> invertedIndex) {
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

    /*
     * Methods to search, intersect and Booleans
     */
    private   LinkedList<Integer> intersect(LinkedList<Integer> p1, LinkedList<Integer> p2){

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
        printer.print("The searched words are in the file: " + answer.toString());
        return answer;
    }
    private   List<Integer> not(LinkedList<Integer> p1, LinkedList<Integer> p2){
        LinkedList<Integer> answer = (LinkedList<Integer>) p1.clone();
        LinkedList<Integer> remove = (LinkedList<Integer>) p2.clone();

        for (Integer i: remove) {
            if(answer.contains(i)){
                answer.remove(i);
            }
        }
        return answer;
    }
    private   LinkedList<Integer> or(LinkedList<Integer> p1, LinkedList<Integer> p2){
        LinkedList<Integer> answer = (LinkedList<Integer>) p1.clone();
        LinkedList<Integer> remove = (LinkedList<Integer>) p2.clone();

        for (Integer element: remove) {
            if(!answer.contains(element))
                answer.add(element);
        }
        Collections.sort(answer);
        return answer;
    }

    public   void theOr(Map<String, LinkedList<Integer>> index) {
        printer.print("word1 OR word2");
        printer.print("   word1: ");
        String word51 = new Scanner(System.in).nextLine();
        printer.print("   word2: ");
        String word52 = new Scanner(System.in).nextLine();
        printer.print(word51 + " --> " + SearchWord(word51,index).toString());
        printer.print(word52 + " --> " + SearchWord(word52,index).toString());
        printer.print("\nRESULT FOR THE QUERY:\n" + word51 + " OR " + word52 + " --> " + or(SearchWord(word51, index), SearchWord(word52, index)).toString());
    }

    public   void and(Map<String, LinkedList<Integer>> index) {
        printer.print("word1 AND word2");
        printer.print("   word1: ");
        String word41 = new Scanner(System.in).nextLine();
        printer.print("   word2: ");
        String word42 = new Scanner(System.in).nextLine();
        printer.print(word41 + " --> " + SearchWord(word41,index).toString());
        printer.print(word42 + " --> " + SearchWord(word42,index).toString());
        printer.print("\nRESULT FOR THE QUERY:\n" + word41 + " AND " + word42 + " --> " + intersect(SearchWord(word41, index), SearchWord(word42, index)).toString());
    }

    public   void orNot(Map<String, LinkedList<Integer>> index) {
        printer.print("word1 OR word2 AND NOT word3");
        printer.print("   word1: ");
        String word31 = new Scanner(System.in).nextLine();
        printer.print("   word2: ");
        String word32 = new Scanner(System.in).nextLine();
        printer.print("   word3: ");
        String word33 = new Scanner(System.in).nextLine();
        LinkedList<Integer> or = or(SearchWord(word31,index), SearchWord(word32,index));
        printer.print(word31 + " --> " + SearchWord(word31,index).toString());
        printer.print(word32 + " --> " + SearchWord(word32,index).toString());
        printer.print(word33 + " --> " + SearchWord(word33,index).toString());
        printer.print("\nRESULT FOR THE QUERY:\n" + word31 + " OR " + word32 + " AND NOT " + word33 + " --> " + not(or, SearchWord(word33, index)).toString());
    }

    public   void andOr(Map<String, LinkedList<Integer>> index) {
        printer.print("word1 AND word2 OR word3");
        printer.print("   word1: ");
        String word21 = new Scanner(System.in).nextLine();
        printer.print("   word2: ");
        String word22 = new Scanner(System.in).nextLine();
        printer.print("   word3: ");
        String word23 = new Scanner(System.in).nextLine();
        printer.print(word21 + " --> " + SearchWord(word21,index).toString());
        printer.print(word22 + " --> " + SearchWord(word22,index).toString());
        printer.print(word23 + " --> " + SearchWord(word23,index).toString());
        LinkedList<Integer> and = intersect(SearchWord(word21, index), SearchWord(word22, index));
        printer.print("\nRESULT FOR THE QUERY:\n" + word21 + " AND " + word22 + " OR " + word23 + " --> " + or(and, SearchWord(word23, index)).toString());
    }

    public   void andNot(Map<String, LinkedList<Integer>> index) {
        printer.print("word1 AND word2 AND NOT word3");
        printer.print("   word1: ");
        String word1 = new Scanner(System.in).nextLine();
        printer.print("   word2: ");
        String word2 = new Scanner(System.in).nextLine();
        printer.print("   word3: ");
        String word3 = new Scanner(System.in).nextLine();
        LinkedList<Integer> r1 = intersect(SearchWord(word1, index), SearchWord(word2, index));
        printer.print(word1 + " --> " + SearchWord(word1,index).toString());
        printer.print(word2 + " --> " + SearchWord(word2,index).toString());
        printer.print(word3 + " --> " + SearchWord(word3,index).toString());
        printer.print("\nRESULT FOR THE QUERY:\n" + word1 + " AND " + word2 + " AND NOT " + word3 + " --> " + not(r1, SearchWord(word3, index)).toString());
    }
}
