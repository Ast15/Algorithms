package edu.asu.tienle.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * Problem from: http://oj.leetcode.com/problems/merge-intervals/

	Given a collection of intervals, merge all overlapping intervals.
	For example,
	Given [1,3],[2,6],[8,10],[15,18],
	return [1,6],[8,10],[15,18].
 * 
 * 
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class MergeIntervals {
    
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        ArrayList<Interval>result=new ArrayList<Interval>();
        Comparator<Interval>comp=new Comparator<Interval>(){
          @Override
          public int compare(Interval o1, Interval o2){
              if(o1.start<o2.start)return -1;
              else if(o1.start>o2.start) return 1;
              return 0;
          }
        };
        Collections.sort(intervals,comp);//intervals will be in ascending order
        
        int index=0;
        int n=intervals.size();
        while(index<n){
            Interval current=intervals.get(index);
            int pointer=index+1;
            
            while(pointer<n&&overlap(current,intervals.get(pointer))){
                current=merge(current,intervals.get(pointer));
                pointer++;
            }
            result.add(current);
            index=pointer;
        }
        return result;
   }
    boolean overlap(Interval o1, Interval o2){
        return (o1.start<=o2.end&&o2.start<=o1.end);
    }
    Interval merge(Interval o1, Interval o2){
        if(!overlap(o1,o2))return null;
        return new Interval(Math.min(o1.start,o2.start),Math.max(o1.end,o2.end));
        
    }
}