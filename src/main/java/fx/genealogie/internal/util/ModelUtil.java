package fx.genealogie.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;
import fx.genealogie.internal.domain.model.SexeEnum;

public class ModelUtil {

	public static Integer findParent(Set<Family> families, Integer personId, SexeEnum sexe) {
		for (Family family : families) {
			for (Integer childId : family.getChildren()) {
				if (childId.equals(personId)) {
					switch (sexe) {
					case F:
						return family.getWife();
					case M:
						return family.getHusband();
					}
				}
			}
		}
		return null;
	}

	public static List<Family> getFamily(List<Integer> familyId, Set<Family> families) {
		List<Family> list = new ArrayList<Family>();
		for (Integer i : familyId) {
			for (Family family : families) {
				if (i.equals(family.getId()))
					list.add(family);
			}
		}
		return list;
	}

	public static Person getPerson(Integer personId, Set<Person> persons) {
		if(personId == null)
			return null;
		for (Person person : persons) {
			if (personId.equals(person.getId()))
				return person;
		}
		return null;
	}

	public static Note getNote(Integer noteId, Set<Note> notes) {
		for (Note note : notes) {
			if (noteId.equals(note.getId()))
				return note;
		}
		return null;
	}

	public static boolean hasFamily(Integer personId, Set<Family> families) {
		for (Family family : families) {
			if (personId.equals(family.getHusband()) || personId.equals(family.getWife())) {
				return true;
			}
		}
		return false;
	}
}
