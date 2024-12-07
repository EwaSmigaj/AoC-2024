package org.aoc2024;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static java.lang.Math.abs;

@NoArgsConstructor
public class Day1 extends Day{

    private List<Integer> col1 = new ArrayList<>();
    private List<Integer> col2 = new ArrayList<>();

    @Override
    public void part1() {
        var input = this.readFile("day1.txt");
        var lines = input.split("\n");

        for (String line : lines) {
            String[] cols = line.split("   ");
            col1.add(Integer.valueOf(cols[0]));
            col2.add(Integer.valueOf(cols[1]));
        }

        col1.sort(Integer::compareTo);
        col2.sort(Integer::compareTo);

        int diffSum = 0;

        for(int i = 0; i < col1.size(); i++) {
            diffSum += abs(col1.get(i) - col2.get(i));
        }
        System.out.println(diffSum);
    }

    @Override
    public void part2() {
        Integer sum = 0;
        HashSet<Integer> appearance = new HashSet<>(col1);

        for(Integer j: col2) {
            if(appearance.contains(j)) {
                sum += j;
            }
        }
        System.out.println(sum);
    }
}
