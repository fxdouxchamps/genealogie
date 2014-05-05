package fx.genealogie.internal.util;

import static fx.genealogie.internal.util.Util.isBlankOrNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;
import fx.genealogie.internal.domain.model.SexeEnum;

public class ParserUtil {

	private static final DateFormat formatEN = new SimpleDateFormat("dd MMMMMMMM yyyy", Locale.ENGLISH);
	private static final DateFormat formatFR = new SimpleDateFormat("d MMMMMMMMMMM yyyy", Locale.FRENCH);
	
	public static void parse(Set<Person> persons, Set<Note> notes, Set<Family> families, BufferedReader reader)
			throws IOException {
		String line = null;
		Person person = null;
		List<String> lines = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		ListIterator<String> iterator = lines.listIterator();
		while (iterator.hasNext()) {
			line = iterator.next();
			if (line.startsWith("0 @I")) {
				if (person != null)
					persons.add(person);
				person = new Person(Integer.valueOf(line.split("@")[1].substring(1)));
			} else if (line.startsWith("2") && line.indexOf("GIVN") != -1) {
				person.setFirstName(line.substring(6).trim());
			} else if (line.startsWith("2") && line.indexOf("SURN") != -1) {
				person.setLastName(line.substring(6).trim());
			} else if (line.startsWith("1") && line.indexOf("RELI") != -1) {
				person.setReligion(line.substring(6).trim());
			} else if (line.startsWith("1") && line.indexOf("SEX") != -1) {
				person.setSexe(SexeEnum.valueOf(SexeEnum.class, line.substring(5).trim()));
			} else if (line.startsWith("1") && line.indexOf("BIRT") != -1) {
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (nextLine.startsWith("2")) {
						if (nextLine.indexOf("DATE") != -1 && !isBlankOrNull(nextLine.substring(6))) {
							try {
								person.setBirthDate(formatFR.format(formatEN.parse(nextLine.substring(6).trim())));
							} catch (ParseException e) {
								person.setBirthDate(nextLine.substring(6).trim());
							}
						} else if (nextLine.indexOf("PLAC") != -1) {
							person.setBirthPlace(nextLine.substring(6).trim());
						}
						nextLine = iterator.next();
					}
					iterator.previous();
				}
			} else if (line.startsWith("1") && line.indexOf("DEAT") != -1) {
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (nextLine.startsWith("2")) {
						if (nextLine.indexOf("DATE") != -1 && !isBlankOrNull(nextLine.substring(6))) {
							try {
								person.setDeathDate(formatFR.format(formatEN.parse(nextLine.substring(6).trim())));
							} catch (ParseException e) {
								person.setDeathDate(nextLine.substring(6).trim());
							}
						} else if (nextLine.indexOf("PLAC") != -1) {
							person.setDeathPlace(nextLine.substring(6).trim());
						}
						nextLine = iterator.next();
					}
					iterator.previous();
				}
			} else if (line.startsWith("1") && line.indexOf("CHAN") != -1) {
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (!nextLine.startsWith("1")) {
						nextLine = iterator.next();
					}
					iterator.previous();
				}
			} else if (line.startsWith("1") && line.indexOf("OBJE") != -1) {
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (!nextLine.startsWith("1")) {
						if (nextLine.startsWith("2") && nextLine.indexOf("FILE") != -1) {
							person.setPhoto(nextLine.split("\\\\")[nextLine.split("\\\\").length - 2] + "\\"
									+ nextLine.split("\\\\")[nextLine.split("\\\\").length - 1]);
						}
						nextLine = iterator.next();
					}
					iterator.previous();
				}
			} else if (line.startsWith("1") && line.indexOf("ADDR") != -1) {
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (!nextLine.startsWith("1")) {
						nextLine = iterator.next();
					}
					iterator.previous();
				}
			} else if (line.startsWith("1") && line.indexOf("FAMS") != -1) {
				person.getFamilyId().add(Integer.valueOf(line.split("@")[1].substring(1)));
			} else if (line.startsWith("1") && line.indexOf("FAMC") != -1) {
			} else if (line.startsWith("1") && line.indexOf("PHON") != -1) {
			} else if (line.startsWith("1") && line.indexOf("_STAT Never Married") != -1) {
			} else if (line.startsWith("1") && line.indexOf("NOTE") != -1) {
				person.setNoteId(Integer.valueOf(line.split("@")[1].substring(2)));
			} else if (line.indexOf("NAME") != -1 || line.indexOf("_UID") != -1
					|| line.indexOf("99999999999999999999") != -1 || line.indexOf("99999999999999999999") != -1
					|| line.indexOf("99999999999999999999") != -1 || line.indexOf("99999999999999999999") != -1) {

			} else if (line.startsWith("0 @NI")) {
				Note note = new Note(Integer.valueOf(line.split("@")[1].substring(2)));
				StringBuilder text = new StringBuilder();
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (nextLine.startsWith("1")) {
						if (nextLine.indexOf("CONC") != -1) {
							text.append(nextLine.substring(6).trim());
						}
						nextLine = iterator.next();
					}
					iterator.previous();
				}
				note.setText(text.toString());
				notes.add(note);
			} else if (line.startsWith("0") && line.indexOf("FAM") != -1) {
				Family family = new Family(Integer.valueOf(line.split("@")[1].substring(1)));
				if (iterator.hasNext()) {
					String nextLine = iterator.next();
					while (!nextLine.startsWith("0")) {
						if (nextLine.indexOf("HUSB") != -1) {
							family.setHusband(Integer.valueOf(nextLine.split("@")[1].substring(1)));
						} else if (nextLine.indexOf("WIFE") != -1) {
							family.setWife(Integer.valueOf(nextLine.split("@")[1].substring(1)));
						} else if (nextLine.startsWith("1") && nextLine.indexOf("MARR") != -1) {
							if (iterator.hasNext()) {
								String nextLine2 = iterator.next();
								while (nextLine2.startsWith("2")) {
									if (nextLine2.indexOf("DATE") != -1 && !isBlankOrNull(nextLine2.substring(6))) {
										try {
											family.setWeddingDate(formatFR.format(formatEN.parse(nextLine2.substring(6)
													.trim())));
										} catch (ParseException e) {
											family.setWeddingDate(nextLine2.substring(6).trim());
										}
									} else if (nextLine2.indexOf("PLAC") != -1) {
										family.setWeddingPlace(nextLine2.substring(6).trim());
									}
									nextLine2 = iterator.next();
								}
								iterator.previous();
							}
						} else if (nextLine.indexOf("CHIL") != -1) {
							family.getChildren().add(Integer.valueOf(nextLine.split("@")[1].substring(1)));
						}
						nextLine = iterator.next();
					}
					iterator.previous();
				}
				families.add(family);
			} else {
//				System.err.println(line);
			}
			persons.add(person);
		}
	}
}
