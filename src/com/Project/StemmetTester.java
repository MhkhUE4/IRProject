package com.Project;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class StemmetTester {
    private static Printer printer = new Printer();
    public static void main(String[] args) {
        PorterStemmer porterStemmer = new PorterStemmer();
        InvertedIndex invertedIndex = new InvertedIndex();
        FileFinder fileFinder = new FileFinder();

        Map<String, LinkedList<Integer>> index;
        Map<String, LinkedList<Integer>> indexStemmed;

        String[] files = fileFinder.getFiles();
        index = invertedIndex.index(files);
        indexStemmed = invertedIndex.indexStemmed(files);

//        invertedIndex.printIndex(index);
//        invertedIndex.printIndex(indexStemmed);

        menu(indexStemmed,invertedIndex);
    }
        private static void menu(Map < String, LinkedList < Integer >> index, InvertedIndex invertedIndex){
            ComapareWithStem comapareWithStem = new ComapareWithStem();
            boolean i = true;
            /*
             * word1 AND word2 AND NOT word3
             * word1 AND word2 OR word3
             * word1 OR word2 AND NOT word3
             */
            Scanner scanner = new Scanner(System.in);
            try {
                while (i) {

                    menuPrinter();

                    int option = scanner.nextInt();
                    scanner.nextLine();
                    switch (option) {
                        case 1:
                            invertedIndex.printIndex(index);
                            printer.print("");
                            break;
                        case 2:
                            comapareWithStem.andNot(index);
                            break;
                        case 3:
                            comapareWithStem.andOr(index);
                            break;
                        case 4:
                            comapareWithStem.orNot(index);
                            break;
                        case 5:
                            comapareWithStem.and(index);
                            break;
                        case 6:
                            comapareWithStem.theOr(index);
                            break;
                        case 7:
                            menuPrinter();
                            break;
                        case 8:
                            return;
                        default:
                            printer.print("Only numbers between 1 and 6");
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("Only numbers with the option accepted");
            }
        }

        private static void menuPrinter() {
            printer.print("________________________________________________");
            printer.print("Type the number of one of the options below:\n");
            printer.print("1 - Print the inverted index.");
            printer.print("2 - Word 1 AND Word 2 AND NOT Word 3");
            printer.print("3 - Word 2 AND Word 2 OR Word 3");
            printer.print("4 - Word 1 OR Word 2 AND NOT Word 3");
            printer.print("5 - Word 1 AND Word 2");
            printer.print("6 - Word 1 OR Word 2");
            printer.print("7 - Print the menu again");
            printer.print("8 - Exit The Application");
        }
    }
