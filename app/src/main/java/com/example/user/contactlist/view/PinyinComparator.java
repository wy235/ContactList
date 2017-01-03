package com.example.user.contactlist.view;

import com.example.user.contactlist.bean.PersonBook;
import java.util.Comparator;

public class PinyinComparator implements Comparator<PersonBook> {

	/*public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}*/

	@Override
	public int compare(PersonBook personBook, PersonBook t1) {
		if (personBook.getSortLetters().equals("@")
				|| t1.getSortLetters().equals("#")) {
			return -1;
		} else if (personBook.getSortLetters().equals("#")
				|| t1.getSortLetters().equals("@")) {
			return 1;
		} else {
			return personBook.getSortLetters().compareTo(t1.getSortLetters());
		}
	}
}
