package br.com.gatto.game.backend.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is a utility class that helps to sort a map by its value.
 */

public class SortHelper {
	
	 /**
     * Method that sort a map descending, since map has not a good and easy way to sort values, i.e. if we try to use a comparator
     * as usual in any other collection structure, the sort is going to work, but there is a problem when it comes to duplicated 
     * values in map. That is why we need to put this map in a list sort it using a comparator and then put it back to map.
     */
	public static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap) {
		List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Entry<Integer, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
}
