package org.aoc2024;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Day {

    HashMap<Integer, HashSet<Integer>> rulesTable = new HashMap<>();
    List<List<Integer>> records = new ArrayList<>();
    List<List<Integer>> incorrectRecords = new ArrayList<>();
    Integer sum = 0;


    @Override
    public void part1() {
        var input = this.readFile("day5.txt");
        var lines = input.split("\n");
        int i = 0;
        String line = lines[i];

        while(!line.equals("")) {
            String[] r = line.split("[|]");
            Integer r0 = Integer.valueOf(r[0]);

            if(!rulesTable.containsKey(r0)) {
                HashSet<Integer> set = new HashSet<>();
                set.add(Integer.parseInt(r[1]));
                rulesTable.put(Integer.parseInt(r[0]), set);
            } else {
                HashSet<Integer> set = rulesTable.get(Integer.parseInt(r[0]));
                set.add(Integer.parseInt(r[1]));
                rulesTable.put(Integer.parseInt(r[0]), set);
            }
            i++;
            line = lines[i];
        }

        i++;
        for(int j = i; j < lines.length; j++) {
            List<Integer> record = new ArrayList<>();
            for (String s: lines[j].split(",")) {
                record.add(Integer.parseInt(s));
            }
            records.add(record);
        }

        for(List<Integer> record: records) {
            if(isCorrectOrder(record)) {
                int middleIdx = record.size()/2;
                sum += record.get(middleIdx);
            }
        }
        System.out.println(sum);
    }

    @Override
    public void part2() {
        sum = 0;
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                if(!i1.equals(i2)) {
                    if(rulesTable.containsKey(i1)){
                        if (rulesTable.get(i1).contains(i2)) {
                            return -1;
                        }
                    }
                    if(rulesTable.containsKey(i2)){
                        if (rulesTable.get(i2).contains(i1)) {
                            return 1;
                        }
                    }
                }
                return 0;
            }
        };

        for(List<Integer> unsorted: incorrectRecords) {
            unsorted.sort(comparator);
            int middleIdx = unsorted.size()/2;
            sum += unsorted.get(middleIdx);
        }
        System.out.println(sum);
    }

    private boolean isCorrectOrder(List<Integer> l) {
        int tIdx = l.size()-1;
        int sIdx = l.size()-2;
        while(tIdx >= 0) {
            while(sIdx >= 0) {
                if(rulesTable.containsKey(l.get(tIdx))){
                    if(rulesTable.get(l.get(tIdx)).contains(l.get(sIdx))) {
                        incorrectRecords.add(l);
                        return false;
                    }
                }
                sIdx--;
            }
            tIdx--;
            sIdx = tIdx -1;
        }
        return true;
    }


}
