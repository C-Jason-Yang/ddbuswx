package com.evcas.ddbuswx.common.utils;

import com.evcas.ddbuswx.model.mongo.BusLine;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by noxn on 2019/5/29.
 */
public class BusLineListComparator implements Comparator<BusLine>, Serializable {

    private static final long serialVersionUID = -2101973113439526342L;

    @Override
    public int compare(BusLine busLine1, BusLine busLine2) {
        String busLine1SortLineName = busLine1.getSortLineName();
        String busLine2SortLineName = busLine2.getSortLineName();
        char[] arr1 = busLine1SortLineName.toCharArray();
        char[] arr2 = busLine2SortLineName.toCharArray();
        int i = 0, j =0;
        while( i < arr1.length && j < arr2.length)
        {
            if ( Character.isDigit( arr1[i]) && Character.isDigit(arr2[j])) {
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                while ( i < arr1.length && Character.isDigit( arr1[i]) ) {
                    sb1.append(arr1[i]);
                    i++;
                }
                while (j < arr2.length && Character.isDigit(arr2[j])) {
                    sb2.append(arr2[j]);
                    j++;
                }
                String s1 = sb1.toString();
                String s2 = sb1.toString();
                if (Integer.parseInt(s1) > Integer.parseInt(s2)) {
                    return 1;
                }
                if (Integer.parseInt(s1) < Integer.parseInt(s2)) {
                    return -1;
                }
            } else {
                if (arr1[i] > arr2[j]) {
                    return 1;
                }
                if (arr1[i] < arr2[j]) {
                    return -1;
                }
                i++;
                j++;
            }
        }
        if (arr1.length == arr2.length) {
            return 0;
        } else {
            return arr1.length > arr2.length? 1: -1;
        }
    }
}
