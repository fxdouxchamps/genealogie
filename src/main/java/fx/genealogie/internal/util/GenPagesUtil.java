package fx.genealogie.internal.util;

import static fx.genealogie.internal.domain.model.Person.addFemale;
import static fx.genealogie.internal.domain.model.Person.addMale;
import static fx.genealogie.internal.util.Util.addEnd;
import static fx.genealogie.internal.util.Util.addStart;
import static fx.genealogie.internal.util.ModelUtil.getFamily;
import static fx.genealogie.internal.util.ModelUtil.getNote;
import static fx.genealogie.internal.util.ModelUtil.getPerson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;

public class GenPagesUtil {

	public static void createFirstGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families,
			Set<Note> notes) {
		BufferedWriter writer = null;
		try {
			File firstGen = new File(
					"d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\firstgen.html");
			firstGen.createNewFile();
			writer = new BufferedWriter(new FileWriter(firstGen));
			for (Person husband : genPersons) {
				Family family = getFamily(husband.getFamilyId(), families).get(0);
				Person wife = getPerson(family.getWife(), persons);
				if (wife == null || husband == null || !husband.getId().equals(family.getHusband()))
					throw new RuntimeException();
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Descendance d'Ignace Douxchamps : Première Génération</h2>");
				writer.append("<a name=\"i" + (husband.getId() - 1) + "\"></a>");
				if (husband.getPhoto() != null) {
					husband.addPhoto(writer);
				}
				writer.append("<p class=\"section1\">" + husband.getName() + "&nbsp;" + addMale() + "&nbsp;");
				if (husband.getBirthDate() != null || husband.getBirthPlace() != null || husband.getDeathDate() != null
						|| husband.getDeathPlace() != null) {
					writer.append(husband.getDates());
				}
				writer.append("&nbsp;<a href=\"genI" + husband.getId()
						+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
				if (wife.getPhoto() != null) {
					wife.addPhoto(writer);
				}
				if (family.getWeddingDate() != null || family.getWeddingPlace() != null) {
					writer.append("<p class=\"mariage\">&times;&nbsp;" + family.getWeddingDate() + " <a name=\"i"
							+ (wife.getId() - 1) + "\"></a>" + wife.getName() + "&nbsp;" + addFemale() + "&nbsp;");
					if (wife.getBirthDate() != null || wife.getBirthPlace() != null || wife.getDeathDate() != null
							|| wife.getDeathPlace() != null) {
						writer.append(wife.getDates());
					}
					writer.append("&nbsp;<a href=\"genI" + wife.getId()
							+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
				}
				if (family.getChildren().size() > 1) {
					writer.append("<p class=\"union\">De cette union sont nés :</p>");
					for (Integer childId : family.getChildren()) {
						Person child = getPerson(childId, persons);
						writer.append("<a name=\"c" + (child.getId() - 1) + "\"></a>");
						if (child.getFamilyId().isEmpty()) {
							writer.append("<p class=\"enf\">" + child.getName() + "&nbsp;");
						} else {
							writer.append("<p class=\"enf\"><a href=\"secondgen.html#i" + (child.getId() - 1) + "\">"
									+ child.getName() + "</a>&nbsp;");
						}

						switch (child.getSexe()) {
						case F:
							writer.append(addFemale() + "&nbsp;");
							break;
						case M:
							writer.append(addMale() + "&nbsp;");
							break;
						}
						if (child.getBirthDate() != null || child.getBirthPlace() != null
								|| child.getDeathDate() != null || child.getDeathPlace() != null) {
							writer.append(child.getDates());
						}
						writer.append("&nbsp;<a href=\"genI" + child.getId()
								+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
						if (child.getNoteId() != null) {
							Note note = getNote(child.getNoteId(), notes);
							writer.append("<p class=\"note\">Note : " + note.getText() + "</p>");
						}
					}
				}
				writer
						.append("<hr /><div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				addEnd(writer);
			}
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

	public static void createNextGenPages(String s1, String s2, String s3, String s4, List<Person> genPersons, Set<Person> persons, Set<Family> families,
			Set<Note> notes) {
		BufferedWriter writer = null;
		try {
			File secondGen = new File(
					"d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\" + s3);
			secondGen.createNewFile();
			writer = new BufferedWriter(new FileWriter(secondGen));
			addStart(writer);
			writer.append("<div class=\"section1\">");
			writer.append("<h2>Descendance d'Ignace Douxchamps : "+s1+" Génération</h2>");
			for (Person person : genPersons) {
				if (person.getFamilyId().isEmpty())
					continue;
				writer.append("<a name=\"i" + (person.getId() - 1) + "\"></a>");
				if (person.getPhoto() != null) {
					person.addPhoto(writer);
				}
				writer.append("<p class=\"section1\"><a href=\"" + s2 +"#c" + (person.getId() - 1) + "\">" + person.getName() + "</a>&nbsp;");
				switch (person.getSexe()) {
				case F:
					writer.append(addFemale() + "&nbsp;");
					break;
				case M:
					writer.append(addMale() + "&nbsp;");
					break;
				}
				if (person.getBirthDate() != null || person.getBirthPlace() != null || person.getDeathDate() != null
						|| person.getDeathPlace() != null) {
					writer.append(person.getDates());
				}
				writer.append("&nbsp;<a href=\"genI" + person.getId()
						+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
				if (person.getNoteId() != null) {
					Note note = getNote(person.getNoteId(), notes);
					writer.append("<p class=\"note\">Note : " + note.getText() + "</p>");
				}
				List<Family> list = getFamily(person.getFamilyId(), families);
				for(Family family : list){
					Person partner = null;
					switch (person.getSexe()) {
					case F:
						if(family.getHusband() != null)
							partner = getPerson(family.getHusband(), persons);
						break;
					case M:
						if(family.getWife() != null)
							partner = getPerson(family.getWife(), persons);
						break;
					}
	
					
					if(partner != null){
						if (partner.getPhoto() != null) {
							partner.addPhoto(writer);
						}
						if (family.getWeddingDate() != null || family.getWeddingPlace() != null) {
							writer.append("<p class=\"mariage\">&times;&nbsp;" + family.getWeddingDate() + " <a name=\"i"
									+ (partner.getId() - 1) + "\"></a>" + partner.getName() + "&nbsp;");
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
							if (partner.getNoteId() != null) {
								Note note = getNote(partner.getNoteId(), notes);
								writer.append("<p class=\"note\">Note : " + note.getText() + "</p>");
							}
							
						}
					}
					if (family.getChildren().size() >= 1) {
						writer.append("<p class=\"union\">De cette union sont nés :</p>");
						for (Integer childId : family.getChildren()) {
							Person child = getPerson(childId, persons);
							writer.append("<a name=\"c" + (child.getId() - 1) + "\"></a>");
							if (child.getFamilyId().isEmpty()) {
								writer.append("<p class=\"enf\">" + child.getName() + "&nbsp;");
							} else {
								writer.append("<p class=\"enf\">");
								if(!child.getFamilyId().isEmpty())
									writer.append("<a href=\"" + s4 +"#i" + (child.getId() - 1) + "\">");
								writer.append(child.getName());
								if(!child.getFamilyId().isEmpty())
									writer.append("</a>");
								writer.append("&nbsp;");
							}
	
							switch (child.getSexe()) {
							case F:
								writer.append(addFemale() + "&nbsp;");
								break;
							case M:
								writer.append(addMale() + "&nbsp;");
								break;
							}
							if (child.getBirthDate() != null || child.getBirthPlace() != null
									|| child.getDeathDate() != null || child.getDeathPlace() != null) {
								writer.append(child.getDates());
							}
							writer.append("&nbsp;<a href=\"genI" + child.getId()
									+ ".html\"><img class=\"tree\" src=\"tree.png\" alt=\"Arbre\" /></a></p>");
							if (child.getNoteId() != null) {
								Note note = getNote(child.getNoteId(), notes);
								writer.append("<p class=\"note\">Note : " + note.getText() + "</p>");
							}
						}
					}
				}
				writer.append("<hr />");
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