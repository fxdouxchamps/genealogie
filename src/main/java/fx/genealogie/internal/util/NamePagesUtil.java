package fx.genealogie.internal.util;

import static fx.genealogie.internal.domain.model.Person.addFemale;
import static fx.genealogie.internal.domain.model.Person.addMale;
import static fx.genealogie.internal.util.ModelUtil.getFamily;
import static fx.genealogie.internal.util.ModelUtil.getPerson;
import static fx.genealogie.internal.util.Util.addEnd;
import static fx.genealogie.internal.util.Util.addStart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Person;

public class NamePagesUtil {

	public static  void createNamePages(Set<Person> persons, Set<Family> families, Integer numberOfPerson) {
		String alphabet = createAlphabet(persons);
		for (String letter : getAlphabet()) {
			Set<String> letters = new HashSet<String>();
			Set<String> names = new HashSet<String>();
			List<Person> limitedPersons = filterPersons(persons, letter);
			
			String twoLetters = "";
			if (!"".equals(letter))
				twoLetters = createTwoLetters(limitedPersons, letter, numberOfPerson);
			Integer numberOfPages = (limitedPersons.size() / numberOfPerson) + 1;
			for (int currentPage = 1; currentPage <= numberOfPages; currentPage++) {
				List<Person> pagePersons = limitedPersons.subList((currentPage - 1) * numberOfPerson, Math.min(
						currentPage * numberOfPerson, limitedPersons.size()));
				BufferedWriter writer = null;
				try {
					File page = new File(
							"d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\nom"
									+ letter.toLowerCase() + "n" + (currentPage - 1) + ".html");
					page.createNewFile();
					writer = new BufferedWriter(new FileWriter(page));
					addStart(writer);
					writer.append("<div class=\"section1\"><h2>Descendance d'Ignace Douxchamps : Index des noms</h2>");
					writer.append("<h4>" + alphabet + "</h4>");
					if (numberOfPages > 1) {
						if (currentPage == 1) {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + currentPage
									+ ".html\"><img src=\"next.jpg\" alt=\"Suivante\" /></a></h4>");
						} else if (currentPage == numberOfPages) {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + (currentPage - 2)
									+ ".html\"><img src=\"prev.jpg\" alt=\"Precedente\" /></a></h4>");
						} else {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + (currentPage - 2)
									+ ".html\"><img src=\"prev.jpg\" alt=\"Precedente\" /></a> <a href=\"nom"
									+ letter.toLowerCase() + "n" + currentPage
									+ ".html\"><img src=\"next.jpg\" alt=\"Suivante\" /></a></h4>");
						}
					} else {
						writer.append("<h4></h4>");
					}
					writer.append("<h4>" + twoLetters + "</h4>");
					for (Person person : pagePersons) {
						writer.append("<p class=\"section1\">");
						if(!letters.contains(person.getRealLastName().substring(1, 2).toUpperCase())){
							writer.append("<a name=\"" + person.getRealLastName().substring(1, 2).toUpperCase()
									+ "\">");
							letters.add(person.getRealLastName().substring(1, 2).toUpperCase());
						}
						if(!names.contains(person.getLastName().toLowerCase())){
							writer.append("<a name=\"" + person.getLastName().toLowerCase().replace(" ", "_")
									+ "\">");
							names.add(person.getLastName().toLowerCase());
						}
						String generationPage = person.getGenerationPages(families);
						if (generationPage != null) {
							writer.append("<a href=\"" + generationPage + "\">");
						}
						writer.append(person.getName());
						if (generationPage != null) {
							writer.append("</a>");
						}
						writer.append("&nbsp;");
						switch (person.getSexe()) {
						case F:
							writer.append(addFemale() + "&nbsp;");
							break;
						case M:
							writer.append(addMale() + "&nbsp;");
							break;
						}
						if (person.getBirthDate() != null || person.getBirthPlace() != null
								|| person.getDeathDate() != null || person.getDeathPlace() != null) {
							writer.append(person.getDates());
						}
						writer.append("&nbsp;<a href=\"genI" + person.getId()
								+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
						List<Family> list = getFamily(person.getFamilyId(), families);
						for (Family family : list) {
							Person partner = null;
							switch (person.getSexe()) {
							case F:
								if (family.getHusband() != null)
									partner = getPerson(family.getHusband(), persons);
								break;
							case M:
								if (family.getWife() != null)
									partner = getPerson(family.getWife(), persons);
								break;
							}

							if (partner != null) {
								if (family.getWeddingDate() != null || family.getWeddingPlace() != null) {
									writer.append("<p class=\"mariage\">&times;&nbsp;" + family.getWeddingDate()
											+ " <a name=\"i" + (partner.getId() - 1) + "\"></a>" + partner.getName()
											+ "&nbsp;");
									switch (partner.getSexe()) {
									case F:
										writer.append(addFemale() + "&nbsp;");
										break;
									case M:
										writer.append(addMale() + "&nbsp;");
										break;
									}

									if (partner.getBirthDate() != null || partner.getBirthPlace() != null
											|| partner.getDeathDate() != null || partner.getDeathPlace() != null) {
										writer.append(partner.getDates());
									}
									writer.append("&nbsp;<a href=\"genI" + partner.getId()
											+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
								}
							}
						}
					}
					if (numberOfPages > 1) {
						if (currentPage == 1) {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + currentPage
									+ ".html\"><img src=\"next.jpg\" alt=\"Suivante\" /></a></h4>");
						} else if (currentPage == numberOfPages) {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + (currentPage - 2)
									+ ".html\"><img src=\"prev.jpg\" alt=\"Precedente\" /></a></h4>");
						} else {
							writer.append("<h4><a href=\"nom" + letter.toLowerCase() + "n" + (currentPage - 2)
									+ ".html\"><img src=\"prev.jpg\" alt=\"Precedente\" /></a> <a href=\"nom"
									+ letter.toLowerCase() + "n" + currentPage
									+ ".html\"><img src=\"next.jpg\" alt=\"Suivante\" /></a></h4>");
						}
					} else {
						writer.append("<h4></h4>");
					}
					writer
							.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

					addEnd(writer);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null)
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		}
	}

	
	public static List<Person> filterPersons(Set<Person> persons, String letter) {
		List<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if (person.getRealLastName().startsWith(letter)
					|| person.getRealLastName().startsWith(letter.toLowerCase())) {
				result.add(person);
			}
		}
		Collections.sort(result, new Comparator<Person>() {
			public int compare(Person p1, Person p2) {
				int i = p1.getRealLastName().toUpperCase().compareTo(p2.getRealLastName().toUpperCase());
				if (i == 0) {
					if (p1.getRealFirstName() == null)
						return -1;
					else if (p2.getRealFirstName() == null)
						return 1;
					else
						return p1.getRealFirstName().compareTo(p2.getRealFirstName());
				}
				return i;
			}
		});
		return result;
	}

	private static String createAlphabet(Set<Person> persons) {
		StringBuilder string = new StringBuilder();
		boolean isFound = false;
		for (String letter : getAlphabet()) {
			isFound = false;
			for (Person p : persons) {
				if (" ".equals(letter)) {
					string.append("<a href=\"nom n0.html\">Autres</a>");
					isFound = true;
					break;
				} else if (p.getRealLastName().startsWith(letter.toUpperCase())) {
					string.append("<a href=\"nom" + letter.toLowerCase() + "n0.html\">" + letter.toUpperCase()
							+ "</a> ");
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				string.append(letter.toUpperCase() + " ");
			}
		}
		return string.toString();
	}

	public static String[] getAlphabet() {
		return new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", " " };
	}

	private static String createTwoLetters(List<Person> limitedPersons, String letter, Integer numberOfPerson) {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (int i = 0; i < limitedPersons.size(); i++) {
			Person person = limitedPersons.get(i);
			String string = person.getRealLastName().substring(0, 2).toUpperCase();
			if (map.get(string) == null)
				map.put(string, i);
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<h4>");
		for (String key : map.keySet()) {
			int index = (map.get(key) / numberOfPerson);
			builder.append("<a href=\"nom" + letter.toLowerCase() + "n" + index + ".html#"
					+ key.substring(1).toUpperCase() + "\">" + key.toUpperCase() + "</a>&nbsp;&nbsp;");
		}
		builder.append("</h4>");
		return builder.toString();
	}
}
