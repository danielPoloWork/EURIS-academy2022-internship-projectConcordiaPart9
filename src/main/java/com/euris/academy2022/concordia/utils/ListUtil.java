package com.euris.academy2022.concordia.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil<DATATYPE> {

    public List<DATATYPE> findNotMatchingRecords(List<DATATYPE> listA, List<DATATYPE> listB) {
        //A concordia, B Trello
        List<DATATYPE> copyListA = new ArrayList<>(listA);

        copyListA.removeAll(listB);
        return copyListA;
    }

    public List<DATATYPE> findMissingRecords(List<DATATYPE> listA, List<DATATYPE> listB) {
        //A concordia, B Trello
        List<DATATYPE> copyListB = new ArrayList<>(listB);

        copyListB.removeAll(listA);

        return copyListB;
    }
}