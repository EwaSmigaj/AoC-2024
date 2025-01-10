package org.aoc2024;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class Day7 extends Day{
    MultiValuedMap<Long, List<Integer>> equations = new ArrayListValuedHashMap<>();
    MultiValuedMap<Long, List<Integer>> eqLeft = new ArrayListValuedHashMap<>();
    Long sum = 0L;

    @Override
    public void part1() {
        var input = this.readFile("day7.txt");
        var lines = input.split("\n");

        for(String l : lines) {
            String[] resSplit = l.split("[:]");
            String[] eqElements = resSplit[1].split(" ");
            List<Integer> equationElements = Arrays.stream(eqElements)
                    .filter(val -> val.matches("-?\\d+(\\.\\d+)?"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            equations.put(Long.parseLong(resSplit[0]), equationElements);
        }

        for(Long k : equations.keys()) {
            for(List<Integer> element : equations.get(k)) {
                if(isEquationPossible(k, element, false)) {
                    sum += k;
                } else {
                    eqLeft.put(k, element);
                }
            }
        }
        System.out.println(sum);
    }

    private boolean isEquationPossible(Long desiredVal, List<Integer> elements, boolean isSpecialOperator) {
            List<List<Long>> vals = new LinkedList<>();
            List<Long> eqResult = new LinkedList<>();

            eqResult.add(Long.parseLong(String.valueOf(elements.get(0))));
            vals.add(eqResult);

            for(int i = 1; i< elements.size(); i++) {
                List<Long> equationResult = new LinkedList<>();

                for(Long element : vals.get(0)) {
                    equationResult.add(element + elements.get(i));
                    equationResult.add(element * elements.get(i));
                    if(isSpecialOperator) {
                        equationResult.add(Long.parseLong(element + "" + elements.get(i)));
                    }
                }
                vals.remove(0);
                vals.add(equationResult);

            }
            for(Long result : vals.get(0)) {
                if(result.equals(desiredVal)) {
                    return true;
                }
            }

        return false;
    }

    @Override
    public void part2() {
        Long newSum = 0L;
        System.out.println(eqLeft);
        for(Long k : eqLeft.keys()) {
            for(List<Integer> element : eqLeft.get(k)) {
                if(isEquationPossible(k, element, true)) {
                    newSum += k;
                }
            }
        }
        System.out.println(newSum + sum);
    }
}
