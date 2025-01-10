package org.aoc2024;


import org.javatuples.Pair;

import java.util.HashMap;
import java.util.HashSet;

public class Day10 extends Day{

    int[][] map;
    int sum = 0;
    HashMap<Integer, HashSet<Pair<Integer, Integer>>> heads;

    @Override
    public void part1() {
        heads = new HashMap<>();


        var input = this.readFile("day10.txt");
        var lines = input.split("\n");

        map = new int[lines.length][lines.length];

        for(int i=0; i < lines.length; i++) {
           for(int j=0; j< lines.length; j++) {
               map[i][j] = Integer.parseInt(String.valueOf(lines[i].charAt(j)));
           }
        }

        int headId = 0;

        for(int i=0; i < lines.length; i++) {
            for(int j=0; j< lines.length; j++) {
                if(map[i][j] == 0) {
                    sum += findPath(i, j, -1, headId, false);
                    headId++;
                }
            }
        }

        System.out.println(sum);

    }

    private int findPath(int i, int j, int previousVal, int headId, boolean isPart2) {
        int thisVal = map[i][j];

        if(thisVal != previousVal + 1) {
            return 0;
        }
        if(thisVal == 9) {
            Pair<Integer, Integer> pos = new Pair<>(i, j);
            if(!isPart2){
            if(heads.containsKey(headId)) {
                if(heads.get(headId).contains(pos)) {
                    return 0;
                }
            } else {
                HashSet<Pair<Integer, Integer>> emptySet = new HashSet<>();
                heads.put(headId, emptySet);
            }}
            HashSet<Pair<Integer, Integer>> set = heads.get(headId);
            set.add(new Pair<>(i, j));
            heads.put(headId, set);
            return 1;
        }

        int up = i >= 1 ? findPath(i-1, j,  thisVal, headId, isPart2) : 0;
        int down = i < map.length-1 ? findPath(i+1, j, thisVal, headId, isPart2) : 0;
        int left = j >= 1 ? findPath(i, j-1, thisVal, headId, isPart2) : 0;
        int right = j < map.length-1 ? findPath(i, j+1, thisVal, headId, isPart2) : 0;

        return up+down+left+right;
    }

    private void printMap() {
        for (int[] ints : map) {
            for (int j = 0; j < map.length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println("");
        }
    }

    @Override
    public void part2() {
        sum = 0;
        int headId = 0;
        for(int i=0; i < map.length; i++) {
            for(int j=0; j< map.length; j++) {
                if(map[i][j] == 0) {
                    sum += findPath(i, j, -1, headId, true);
                    headId++;
                }
            }
        }
        System.out.println(sum);
    }
}
