package org.aoc2024;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.lang.Math.abs;

@NoArgsConstructor
public class Day2 extends Day{

    private final List<List<Integer>> reportsGood = new ArrayList<>();
    private final List<List<Integer>> reportsBad = new ArrayList<>();

    @Override
    public void part1() {
        var input = this.readFile("day2.txt");
        var lines = input.split("\n");

        for (String line : lines) {
            String[] cols = line.split(" ");

            List<Integer> report = new ArrayList<>();
            int prev = -1;
            boolean out = false;
            for(String element: cols) {
                report.add(Integer.valueOf(element));
                int abs = abs(prev - Integer.parseInt(element));
                if(prev >= 0 ) {
                    if(abs > 3 || abs == 0) {
                        out = true;
                    }
                }
                prev = Integer.parseInt(element);
            }
            if(!out) {
                List<Integer> sortedIncreasing = new ArrayList<>(report);
                List<Integer> sortedDecreasing = new ArrayList<>(report);
                sortedIncreasing.sort(Integer::compareTo);
                sortedDecreasing.sort(Collections.reverseOrder());

                if(report.equals(sortedIncreasing) || report.equals(sortedDecreasing)) {
                    reportsGood.add(report);
                } else { reportsBad.add(report); }
            } else {
                reportsBad.add(report);
            }
        }
        System.out.println(reportsGood.size());
    }

    @Override
    public void part2() {
        int j = 0;
        while(j < reportsBad.size()) {
            boolean validated = false;
            for (int i = 0; i < reportsBad.get(j).size(); i++) {
                List<Integer> report = new ArrayList<>(reportsBad.get(j));
                report.remove(i);
                if (isValidReport(report)) {
                    reportsBad.remove(j);
                    validated = true;
                    break;
                } else {
                    System.out.println("report " + report + " is still unsafe");
                }
            }
            if(!validated) {
                j++;
            }
        }

        System.out.println(reportsGood.size());
    }

    boolean isValidReport(List<Integer> report) {
        int prev = -1;
        boolean out = false;

        for(Integer element: report) {
            int abs = abs(prev - element);
            if(prev >= 0 ) {
                if(abs > 3 || abs == 0) {
                    out = true;
                }
            }
            prev = element;
        }
        if(!out) {
            List<Integer> sortedIncreasing = new ArrayList<>(report);
            List<Integer> sortedDecreasing = new ArrayList<>(report);
            sortedIncreasing.sort(Integer::compareTo);
            sortedDecreasing.sort(Collections.reverseOrder());

            if(report.equals(sortedIncreasing) || report.equals(sortedDecreasing)) {
                reportsGood.add(report);
                return true;
            }
        }
        return false;
    }

}
