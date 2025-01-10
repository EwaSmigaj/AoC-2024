package org.aoc2024;

import org.javatuples.Pair;

import java.util.*;


public class Day8 extends Day{
    Character[][] tab;
    HashSet<Pair<String, String>> calculatedPairs = new HashSet<>();
    HashSet<Pair<Integer, Integer>> signalPlace = new HashSet<>();

    // <'a', [(3,3),(4,5)]>
    HashMap<Character, List<Pair<Integer, Integer>>> antennas = new HashMap<>();

    @Override
    public void part1() {
        var input = this.readFile("day8.txt");
        var lines = input.split("\n");
        tab = new Character[lines.length][lines.length];
        int i = 0;
        int j = 0;

        for(String l : lines) {
            for(Character ch : l.toCharArray()) {
                if(ch != '.') {
                    if(antennas.containsKey(ch)) {
                        List<Pair<Integer, Integer>> posList = antennas.get(ch);
                        posList.add(new Pair<>(i, j));
                        antennas.put(ch, posList);
                    } else {
                        Pair<Integer, Integer> pos = new Pair<>(i, j);
                        List<Pair<Integer, Integer>> posList = new ArrayList<>();
                        posList.add(pos);
                        antennas.put(ch, posList);
                    }
                }
                tab[i][j] = ch;
                j++;
            }
            i++;
            j=0;
        }
        System.out.println(antennas);

        for(Character ch : antennas.keySet()) {
//            System.out.println("Analyzing char " + ch);
            int l = 0;
            int p = 1;
            while(l < antennas.get(ch).size()-1) {
                while(p < antennas.get(ch).size()) {
                    if(!calculatedPairs.contains(new Pair<>(antennas.get(ch).get(l).toString(), antennas.get(ch).get(l).toString()))) {
                        putSignalForTwoAntennas(antennas.get(ch).get(l), antennas.get(ch).get(p));
                    }
                    p++;
                }
                l++;
                p = l+1;
            }
        }
        printTab();
        System.out.println(signalPlace.size());

    }

    private void putSignalForTwoAntennas(Pair<Integer, Integer> a0, Pair<Integer, Integer> a1) {

        calculatedPairs.add(new Pair<>(a0.toString(), a1.toString()));
        calculatedPairs.add(new Pair<>(a1.toString(), a0.toString()));
        signalPlace.add(a0);
        signalPlace.add(a1);
        Pair<Integer, Integer> vector = new Pair<>(a0.getValue0()-a1.getValue0(), a0.getValue1()-a1.getValue1());
//        System.out.println("vector of antennas " + a0 + " " + a1 + " is " + vector);

        //signal for first
        int row = a0.getValue0() + vector.getValue0();
        int col = a0.getValue1() + vector.getValue1();


        while(row >= 0 && row < tab.length && col >= 0 && col < tab.length) {
            signalPlace.add(new Pair<>(row, col));
            if(tab[row][col] == '.') {
                tab[row][col] = '#';
            }
            row += vector.getValue0();
            col += vector.getValue1();
        }

        //signal for second
        row = a1.getValue0() - vector.getValue0();
        col = a1.getValue1() - vector.getValue1();

        while(row >= 0 && row < tab.length && col >= 0 && col < tab.length) {
            signalPlace.add(new Pair<>(row, col));
            if(tab[row][col] == '.') {
                tab[row][col] = '#';
            }
            row -= vector.getValue0();
            col -= vector.getValue1();
        }

    }

    @Override
    public void part2() {


    }

    private void printTab() {
        for (Character[] characters : tab) {
            for (int j = 0; j < tab.length; j++) {
                System.out.print(characters[j]);
            }
            System.out.print("\n");
        }
    }
}
