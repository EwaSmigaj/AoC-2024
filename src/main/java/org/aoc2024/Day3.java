package org.aoc2024;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@NoArgsConstructor
public class Day3 extends Day{
    @Override
    public void part1() {
        var input = this.readFile("day3.txt");
        var lines = input.split("\n");
        int sum = 0;

        for (String line : lines) {
            Pattern pattern = Pattern.compile("mul\\((\\d*?)(,)(\\d*?)\\)");
            Matcher matcher = pattern.matcher(line);

            while(matcher.find())
            {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(3));
            }
        }
        System.out.println(sum);
    }

    @Override
    public void part2() {
        var input = this.readFile("day3.txt");
        var lines = input.split("don't\\(\\)");
        int sum = 0;
        boolean firstLine = true;

        for (String line : lines) {
            System.out.println(line);

            String[] splitByDo = line.split("do\\(\\)");
            int i = 0;
            for(String element: splitByDo) {
                if(i != 0 || firstLine) {
                    Pattern pattern = Pattern.compile("mul\\((\\d*?)(,)(\\d*?)\\)");
                    Matcher matcher = pattern.matcher(element);

                    while(matcher.find())
                    {
                        sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(3));
                    }
                }
                i++;
                firstLine = false;
            }
        }
        System.out.println(sum);
    }
}
