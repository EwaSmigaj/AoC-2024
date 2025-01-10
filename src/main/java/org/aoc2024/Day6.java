package org.aoc2024;


import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Stack;

public class Day6 extends Day {
    char[][] ogMap;
    char[][] mapFilled;
    boolean escape = false;
    int loop = 0;
    int visited = 0;
    int moves = 0;
    int sameInRow = 0;
    Pair<Integer, Integer> firstPose;
    Stack<Pair<Integer, Integer>> guardPoses = new Stack<>();

    @Override
    public void part1(){
        var input = this.readFile("day6.txt");
        var lines = input.split("\n");

        ogMap = new char[lines.length][lines.length];
        int i = 0;

        for (String line : lines) {
            char[] row = line.toCharArray();
            ogMap[i] = row;
            i++;
        }
        mapFilled = new char[lines.length][lines.length];
        i = 0;
        for (String line : lines) {
            char[] row = line.toCharArray();
            mapFilled[i] = row;
            i++;
        }

        findGuard();
        firstPose = guardPoses.peek();
        while(!escape) {
            move();
        }
        printMap(mapFilled);
        System.out.println(visited+1);

    }

    @Override
    public void part2(){
        HashSet<String> ogGuardPoses = new HashSet<>();
        for(Pair<Integer, Integer> p : guardPoses) {
            ogGuardPoses.add(p.getValue0() + "," + p.getValue1());
        }

        int i = 0;

        for(String pos: ogGuardPoses) {
            String[] p = pos.split(",");
            int p0 = Integer.parseInt(p[0]);
            int p1 = Integer.parseInt(p[1]);

            System.out.println("SCENARIO " + i);
            if(firstPose.getValue0() == p0 && firstPose.getValue1() == p1 ) {
                i++;
                continue;
            }
            resetFilledMap();
            escape = false;
            guardPoses = new Stack<>();
            findGuard();
            visited = 0;
            moves = 0;
            mapFilled[p0][p1] = '#';

            while(!escape) {
//                System.out.println(sameInRow);
                move();
                if(sameInRow > visited*2) {
                    loop++;
                    System.out.println("loop found!!!");
                    break;
                }
            }
            i++;
        }
        System.out.println(loop);

    }

    private void resetFilledMap() {
        for(int i = 0; i < ogMap.length; i++) {
            System.arraycopy(ogMap[i], 0, mapFilled[i], 0, ogMap.length);
        }
    }

//    private boolean simulateGuardEscapes() {
//        while(!escape) {
//            move();
//            if(dupes >= getPosCount()) {
//                return false;
//            }
//        }
//        return true;
//    }

    private void findGuard() {
        for(int i = 0; i < mapFilled.length; i++) {
            for (int j = 0; j < mapFilled.length; j++) {
                char g = mapFilled[i][j];
                if(g == '^' || g == '>' || g=='<' || g=='v') {
                    Pair<Integer, Integer> guardPos = new Pair(i, j);
                    System.out.println("guard found in " + guardPos);
                    guardPoses.add(guardPos);
                }
            }
        }
    }

    private void move() {
        Pair<Integer, Integer> currentPos = guardPoses.peek();
//        System.out.println(currentPos);
        switch (mapFilled[currentPos.getValue0()][currentPos.getValue1()]){
            case '^':
                goUp(currentPos);
                break;
            case 'v':
                goDown(currentPos);
                break;
            case '>':
                goRight(currentPos);
                break;
            case '<':
                goLeft(currentPos);
                break;
        }
    }

    private void goUp(Pair<Integer, Integer> pos) {
        int i = pos.getValue0();
        int j = pos.getValue1();

        if(i == 0) {
            escape = true;
        } else if(mapFilled[i-1][j] == '.' || mapFilled[i-1][j] == 'X' ) {
            if(mapFilled[i-1][j] == '.') {
                visited++;
                sameInRow = 0;
            } else { sameInRow++; }
            mapFilled[i][j] = 'X';
            mapFilled[i-1][j] = '^';
            Pair<Integer, Integer> newPos = new Pair<>(i-1, j);
            guardPoses.add(newPos);
            moves++;
        } else {
            mapFilled[i][j] = '>';
        }
    }

    private void goRight(Pair<Integer, Integer> pos) {
        int i = pos.getValue0();
        int j = pos.getValue1();

        if(j == mapFilled.length-1) {
            escape = true;
        } else if(mapFilled[i][j+1] == '.'|| mapFilled[i][j+1] == 'X' ) {
            if(mapFilled[i][j+1] == '.') {
                visited++;
                sameInRow = 0;
            } else { sameInRow++; }
            mapFilled[i][j] = 'X';
            mapFilled[i][j+1] = '>';
            Pair<Integer, Integer> newPos = new Pair<>(i, j+1);
            guardPoses.add(newPos);
        } else {
            mapFilled[i][j] = 'v';
        }
    }

    private void goDown(Pair<Integer, Integer> pos) {
        int i = pos.getValue0();
        int j = pos.getValue1();

        if(i == mapFilled.length-1) {
            escape = true;
        } else if(mapFilled[i+1][j] == '.'|| mapFilled[i+1][j] == 'X' ) {
            if(mapFilled[i+1][j] == '.') {
                visited++;
                sameInRow = 0;
            } else { sameInRow++; }
            mapFilled[i][j] = 'X';
            mapFilled[i+1][j] = 'v';
            Pair<Integer, Integer> newPos = new Pair<>(i+1, j);
            guardPoses.add(newPos);
        } else {
            mapFilled[i][j] = '<';
        }
    }

    private void goLeft(Pair<Integer, Integer> pos) {
        int i = pos.getValue0();
        int j = pos.getValue1();

        if(j == 0) {
            escape = true;
        } else if(mapFilled[i][j-1] == '.'|| mapFilled[i][j-1] == 'X' ) {
            if(mapFilled[i][j-1] == '.') {
                visited++;
                sameInRow = 0;
            } else { sameInRow++; }
            mapFilled[i][j] = 'X';
            mapFilled[i][j-1] = '<';
            Pair<Integer, Integer> newPos = new Pair<>(i, j-1);
            guardPoses.add(newPos);
        } else {
            mapFilled[i][j] = '^';
        }
    }

    private void printMap(char[][] map) {
        for (char[] chars : map) {
            for (int j = 0; j < map.length; j++) {
                System.out.print(chars[j]);
            }
            System.out.print("\n");
        }
    }

    private int getPosCount() {
        int sum = 0;
        for (char[] chars : mapFilled) {
            for (int j = 0; j < mapFilled.length; j++) {
                if (chars[j] == 'X') { sum++; }
            }
        }
        return sum;
    }
}
