package org.aoc2024;


import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends Day{

    Long sum = 0L;
    List<String> disc;

    //disc in form of list of elemetns and their occurences
    //example:
    //disc: ["0", "0", ".", ".", ".", "1", "1", "1", ".", ".", ".", "2", ".", ".", ".", "3", "3"]
    //discPaired: [("0", 2),(".", 3),("1", 3),(".", 3),("2", 1),(".", 3),("3", 2)]
    List<Pair<String, Integer>> discPaired = new ArrayList<>();

    @Override
    public void part1() {
        String input = this.readFile("day9.txt");
        disc = new ArrayList<>();

        for(int i = 0; i< input.length(); i++) {
            int repeat = Integer.parseInt(String.valueOf(input.charAt(i)));
            if(i%2 == 0) {
                for(int j = 0; j< repeat; j++) {
                    disc.add(String.valueOf(i/2));
                }
            } else {
                for(int j = 0; j< repeat; j++) {
                    disc.add(".");
                }
            }
        }

        int leftIdx = disc.indexOf(".");
        int rightIdx = disc.size()-1;

        List<String> discModified = new ArrayList<>(disc);

        while(leftIdx < rightIdx) {
            if(!discModified.get(rightIdx).equals(".") && discModified.get(leftIdx).equals(".")) {
                discModified.set(leftIdx, discModified.get(rightIdx));
                discModified.set(rightIdx, ".");
                rightIdx--;
                leftIdx++;
            } else if(discModified.get(rightIdx).equals(".")) {
                rightIdx--;
            } else if(!discModified.get(leftIdx).equals(".")) {
                leftIdx++;
            }
        }
        System.out.println(discModified);


        System.out.println(calculateSum(discModified));

    }

    private Long calculateSum(List<String> discToCalc){
        Long output = 0L;
        for(int i=0; i< discToCalc.size(); i++) {
            if(!discToCalc.get(i).equals(".")) {
                output += Long.parseLong(discToCalc.get(i)) * i;
            }
        }
        return output;
    }

    private List<Pair<String, Integer>> translateDiscToDiscPaired(List<String> discToCalc) {
        int counter = 1;
        List<Pair<String, Integer>> output = new ArrayList<>();
        for(int i = 1; i< discToCalc.size(); i++) {
            String prev = discToCalc.get(i-1);
            String current = discToCalc.get(i);

            if(prev.equals(current)) {
                counter++;
            } else {
                output.add(new Pair<>(prev, counter));
                counter = 1;
            }

            if(i == disc.size()-1) {
                output.add(new Pair<>(current, counter));
            }
        }
        return output;
    }

    private List<String> translateDiscPairedToDisc(List<Pair<String, Integer>> discToCalc) {
        List<String> output = new ArrayList<>();

        for(Pair<String, Integer> element : discToCalc) {
            for(int i = 0; i < element.getValue1(); i++) {
                output.add(element.getValue0());
            }
        }

        return output;
    }

    @Override
    public void part2() {
        discPaired = translateDiscToDiscPaired(disc);

        int i;

        for(int a = 0; a < discPaired.size(); a++) {
            Pair<String, Integer> element = discPaired.get(a);
            i = discPaired.size()-1;
            if(!element.getValue0().equals(".")) {
                continue;
            }
            int freeSpace = element.getValue1();

            boolean moved = false;
            while(!moved && i > a) {
                if(!discPaired.get(i).getValue0().equals(".")) {

                    int numbAmount = discPaired.get(i).getValue1();

                    if(numbAmount <= freeSpace) {
                        discPaired.set(a, discPaired.get(i));
                        discPaired.set(i, new Pair<>(".", numbAmount));
                        if(numbAmount<freeSpace) {discPaired.add(a+1, new Pair<>(".", freeSpace-numbAmount));}
                        moved = true;
                    }
                }
                i--;
            }
        }
        System.out.println(discPaired);
        System.out.println(calculateSum(translateDiscPairedToDisc(discPaired)));
    }
}
