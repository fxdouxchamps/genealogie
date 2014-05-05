package fx.genealogie.internal.util;

import static fx.genealogie.internal.domain.model.Person.addFemale;
import static fx.genealogie.internal.domain.model.Person.addMale;
import static fx.genealogie.internal.util.ModelUtil.findParent;
import static fx.genealogie.internal.util.ModelUtil.getFamily;
import static fx.genealogie.internal.util.ModelUtil.getPerson;
import static fx.genealogie.internal.util.Util.addEnd;
import static fx.genealogie.internal.util.Util.addStart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Person;
import fx.genealogie.internal.domain.model.SexeEnum;

public class TreeUtil {

	public static void createFirstGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table><tr><td colspan=\"1\">");
				writer.append("<a href=\"firstgen.html#i" + (person.getId() - 1) + "\" >" + person.getName()
						+ "</a>&nbsp;");
				switch (person.getSexe()) {
				case F:
					writer.append(addFemale() + "<br />");
					break;
				case M:
					writer.append(addMale() + "<br />");
					break;
				}

				if (person.getBirthDate() != null || person.getBirthPlace() != null || person.getDeathDate() != null
						|| person.getDeathPlace() != null) {
					writer.append("<span>" + person.getDates() + "</span>");
				}
				writer.append("</td></tr></table>");
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "firstgen.html", "secondgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createSecondGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (father != null || mother != null) {
					writer.append("	<tr align=\"center\"><td colspan=\"2\">");
					if (father != null) {
						addPersonDetail(writer, father, "", "firstgen.html");
					} else {
						writer.append("&nbsp;");
					}
					writer.append("</td><td colspan=\"2\">");
					if (mother != null) {
						addPersonDetail(writer, mother, "", "firstgen.html");
					} else {
						writer.append("&nbsp;");
					}
					writer.append("</td></tr>");

					writer.append("<tr><td colspan=\"1\" >&nbsp;</td><td colspan=\"1\" class=\"bottom_left\" >&nbsp;</td><td colspan=\"1\" class=\"bottom_right\" >&nbsp;</td><td colspan=\"1\" >&nbsp;</td></tr>");
					writer.append("<tr><td colspan=\"2\" >&nbsp;</td><td colspan=\"2\" class=\"left\" >&nbsp;</td></tr>");
				}
				writer.append("<tr align=\"center\"><td colspan=\"4\">");
				addPersonDetail(writer, person, "firstgen.html", "secondgen.html");
				writer.append("</td></tr></table>");
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "secondgen.html", "thirdgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createThirdGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
			}
			if (mother != null) {
				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
			}
			boolean isFatherGeneration = fatherfather != null && motherfather != null;
			boolean isMotherGeneration = fathermother != null && mothermother != null;
			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherGeneration || isMotherGeneration) {
					if (isFatherGeneration) {
						writer.append("<tr align=\"center\"><td colspan=\"2\">");
						addPersonDetail(writer, fatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">");
						addPersonDetail(writer, motherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr><td>&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"2\">&nbsp;</td><td colspan=\"2\" class=\"left\">&nbsp;</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"2\">");
						addPersonDetail(writer, father, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td><td colspan=\"2\">");
						addPersonDetail(writer, mother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");
						writer.append("<tr><td colspan=\"2\">&nbsp;</td><td colspan=\"2\" class=\"bottom_left\">&nbsp;</td><td class=\"bottom_right\">&nbsp;</td><td>&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td colspan=\"3\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"4\">");
						addPersonDetail(writer, person, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					} else {
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"2\">");
						addPersonDetail(writer, fathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">");
						addPersonDetail(writer, mothermother, "", "firstgen.html");
						writer.append("</td></tr>");
						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td class=\"bottom_right\">&nbsp;</td><td>&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"2\">&nbsp;</td><td colspan=\"2\">&nbsp;</td><td colspan=\"2\" class=\"left\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"2\">");
						addPersonDetail(writer, father, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td><td colspan=\"2\">");
						addPersonDetail(writer, mother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");
						writer.append("<tr><td>&nbsp;</td><td colspan=\"2\" class=\"bottom_left\">&nbsp;</td><td class=\"bottom_right\">&nbsp;</td><td colspan=\"2\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td colspan=\"3\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, person, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr></table>");
					}
				}else{
					writer.append("<tr align=\"center\"><td colspan=\"6\">");
					addPersonDetail(writer, person, "secondgen.html", "thirdgen.html");
					writer.append("</td></tr></table>");
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "thirdgen.html", "fourthgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createFourthGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			Person fatherfatherfather = null;
			Person motherfatherfather = null;
			Person fathermotherfather = null;
			Person mothermotherfather = null;
			Person fatherfathermother = null;
			Person motherfathermother = null;
			Person fathermothermother = null;
			Person mothermothermother = null;
			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				if (fatherfather != null) {
					fatherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.M), persons);
					motherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.F), persons);
				}
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
				if (motherfather != null) {
					fathermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.M), persons);
					mothermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.F), persons);
				}
			}
			if (mother != null) {
				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				if (fathermother != null) {
					fatherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.M), persons);
					motherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.F), persons);
				}
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
				if (mothermother != null) {
					fathermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.M), persons);
					mothermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.F), persons);
				}
			}
			boolean isFatherFatherGeneration = fatherfatherfather != null && motherfatherfather != null;
			boolean isMotherFatherGeneration = fathermotherfather != null && mothermotherfather != null;
			boolean isFatherMotherGeneration = fatherfathermother != null && motherfathermother != null;
			boolean isMotherMotherGeneration = fathermothermother != null && mothermothermother != null;
			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherFatherGeneration || isMotherFatherGeneration || isFatherMotherGeneration
						|| isMotherMotherGeneration) {
					if (isFatherFatherGeneration) { // 117
						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"12\">");
						addPersonDetail(writer, person, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr></table>");
					}
					if (isMotherFatherGeneration) { // 93 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherGeneration) { // 275 Done
						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherGeneration) { // 104 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"6\" class=\"bottom_right\">&nbsp;</td><td colspan=\"2\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"12\">");
						addPersonDetail(writer, person, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr></table>");
					}
				} else { // 114
					writer.append("<tr align=\"center\"><td colspan=\"14\">");
					addPersonDetail(writer, person, "thirdgen.html", "fourthgen.html");
					writer.append("</td></tr></table>");
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "fourthgen.html", "fifthgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createFifthGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			Person fatherfatherfather = null;
			Person motherfatherfather = null;
			Person fathermotherfather = null;
			Person mothermotherfather = null;
			Person fatherfathermother = null;
			Person motherfathermother = null;
			Person fathermothermother = null;
			Person mothermothermother = null;
			Person fatherfatherfatherfather = null;
			Person fathermotherfatherfather = null;
			Person fatherfathermotherfather = null;
			Person fathermothermotherfather = null;
			Person fatherfatherfathermother = null;
			Person fathermotherfathermother = null;
			Person fatherfathermothermother = null;
			Person fathermothermothermother = null;
			Person motherfatherfatherfather = null;
			Person mothermotherfatherfather = null;
			Person motherfathermotherfather = null;
			Person mothermothermotherfather = null;
			Person motherfatherfathermother = null;
			Person mothermotherfathermother = null;
			Person motherfathermothermother = null;
			Person mothermothermothermother = null;
			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				if (fatherfather != null) {
					fatherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.M), persons);
					if (fatherfatherfather != null) {
						fatherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.M), persons);
						motherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.F), persons);
					}
					motherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.F), persons);
					if (motherfatherfather != null) {
						fathermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.M), persons);
						mothermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.F), persons);
					}
				}
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
				if (motherfather != null) {
					fathermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.M), persons);
					if (fathermotherfather != null) {
						fatherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.M), persons);
						motherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.F), persons);
					}
					mothermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.F), persons);
					if (mothermotherfather != null) {
						fathermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.M), persons);
						mothermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.F), persons);
					}
				}
			}
			if (mother != null) {
				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				if (fathermother != null) {
					fatherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.M), persons);
					if (fatherfathermother != null) {
						fatherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.M), persons);
						motherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.F), persons);
					}
					motherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.F), persons);
					if (motherfathermother != null) {
						fathermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.M), persons);
						mothermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.F), persons);
					}
				}
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
				if (mothermother != null) {
					fathermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.M), persons);
					if (fathermothermother != null) {
						fatherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.M), persons);
						motherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.F), persons);
					}
					mothermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.F), persons);
					if (mothermothermother != null) {
						fathermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.M), persons);
						mothermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.F), persons);
					}
				}
			}
			boolean isFatherFatherFatherGeneration = fatherfatherfatherfather != null
					&& motherfatherfatherfather != null;
			boolean isFatherMotherFatherGeneration = fatherfathermotherfather != null
					&& motherfathermotherfather != null;
			boolean isFatherFatherMotherGeneration = fatherfatherfathermother != null
					&& motherfatherfathermother != null;
			boolean isFatherMotherMotherGeneration = fatherfathermothermother != null
					&& motherfathermothermother != null;
			boolean isMotherFatherFatherGeneration = fathermotherfatherfather != null
					&& mothermotherfatherfather != null;
			boolean isMotherMotherFatherGeneration = fathermothermotherfather != null
					&& mothermothermotherfather != null;
			boolean isMotherFatherMotherGeneration = fathermotherfathermother != null
					&& mothermotherfathermother != null;
			boolean isMotherMotherMotherGeneration = fathermothermothermother != null
					&& mothermothermothermother != null;
			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherFatherFatherGeneration || isFatherMotherFatherGeneration || isFatherFatherMotherGeneration
						|| isFatherMotherMotherGeneration || isMotherFatherFatherGeneration
						|| isMotherMotherFatherGeneration || isMotherFatherMotherGeneration
						|| isMotherMotherMotherGeneration) {
					if (isFatherFatherFatherGeneration) { // 764 Done
						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					}
					if (isFatherMotherFatherGeneration) { // 721 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"2\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					}
					if (isFatherFatherMotherGeneration) { // 759 Done
						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherMotherGeneration) { // 666 Done
						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherFatherGeneration) { // 146 Done
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					}
					if (isMotherMotherFatherGeneration) { // 132 Done
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"14\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					}
					if (isMotherFatherMotherGeneration) { // 346 Done
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"13\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherMotherGeneration) { // 139 Done
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"12\">");
						addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr></table>");
					}
				}else{
					writer.append("<tr align=\"center\"><td colspan=\"14\">");
					addPersonDetail(writer, person, "fourthgen.html", "fifthgen.html");
					writer.append("</td></tr></table>");
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "fifthgen.html", "sixthgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createSixthGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			Person fatherfatherfather = null;
			Person motherfatherfather = null;
			Person fathermotherfather = null;
			Person mothermotherfather = null;
			Person fatherfathermother = null;
			Person motherfathermother = null;
			Person fathermothermother = null;
			Person mothermothermother = null;
			Person fatherfatherfatherfather = null;
			Person fathermotherfatherfather = null;
			Person fatherfathermotherfather = null;
			Person fathermothermotherfather = null;
			Person fatherfatherfathermother = null;
			Person fathermotherfathermother = null;
			Person fatherfathermothermother = null;
			Person fathermothermothermother = null;
			Person motherfatherfatherfather = null;
			Person mothermotherfatherfather = null;
			Person motherfathermotherfather = null;
			Person mothermothermotherfather = null;
			Person motherfatherfathermother = null;
			Person mothermotherfathermother = null;
			Person motherfathermothermother = null;
			Person mothermothermothermother = null;
			Person fatherfatherfatherfatherfather = null;
			Person fatherfathermotherfatherfather = null;
			Person fatherfatherfathermotherfather = null;
			Person fatherfathermothermotherfather = null;
			Person fatherfatherfatherfathermother = null;
			Person fatherfathermotherfathermother = null;
			Person fatherfatherfathermothermother = null;
			Person fatherfathermothermothermother = null;
			Person fathermotherfatherfatherfather = null;
			Person fathermothermotherfatherfather = null;
			Person fathermotherfathermotherfather = null;
			Person fathermothermothermotherfather = null;
			Person fathermotherfatherfathermother = null;
			Person fathermothermotherfathermother = null;
			Person fathermotherfathermothermother = null;
			Person fathermothermothermothermother = null;
			Person motherfatherfatherfatherfather = null;
			Person motherfathermotherfatherfather = null;
			Person motherfatherfathermotherfather = null;
			Person motherfathermothermotherfather = null;
			Person motherfatherfatherfathermother = null;
			Person motherfathermotherfathermother = null;
			Person motherfatherfathermothermother = null;
			Person motherfathermothermothermother = null;
			Person mothermotherfatherfatherfather = null;
			Person mothermothermotherfatherfather = null;
			Person mothermotherfathermotherfather = null;
			Person mothermothermothermotherfather = null;
			Person mothermotherfatherfathermother = null;
			Person mothermothermotherfathermother = null;
			Person mothermotherfathermothermother = null;
			Person mothermothermothermothermother = null;

			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				if (fatherfather != null) {
					fatherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.M), persons);
					if (fatherfatherfather != null) {
						fatherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.M), persons);
						if (fatherfatherfatherfather != null) {
							fatherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.M), persons);
							motherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.F), persons);
						}
						motherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.F), persons);
						if (motherfatherfatherfather != null) {
							fathermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.M), persons);
							mothermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.F), persons);
						}
					}
					motherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.F), persons);
					if (motherfatherfather != null) {
						fathermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.M), persons);
						if (fathermotherfatherfather != null) {
							fatherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.M), persons);
							motherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.F), persons);
						}
						mothermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.F), persons);
						if (mothermotherfatherfather != null) {
							fathermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.M), persons);
							mothermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.F), persons);
						}
					}
				}
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
				if (motherfather != null) {
					fathermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.M), persons);
					if (fathermotherfather != null) {
						fatherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.M), persons);
						if (fatherfathermotherfather != null) {
							fatherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.M), persons);
							motherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.F), persons);
						}
						motherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.F), persons);
						if (motherfathermotherfather != null) {
							fathermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.M), persons);
							mothermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.F), persons);
						}
					}
					mothermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.F), persons);
					if (mothermotherfather != null) {
						fathermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.M), persons);
						if (fathermothermotherfather != null) {
							fatherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.M), persons);
							motherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.F), persons);
						}
						mothermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.F), persons);
						if (mothermothermotherfather != null) {
							fathermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.M), persons);
							mothermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.F), persons);
						}
					}
				}
			}
			if (mother != null) {
				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				if (fathermother != null) {
					fatherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.M), persons);
					if (fatherfathermother != null) {
						fatherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.M), persons);
						if (fatherfatherfathermother != null) {
							fatherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.M), persons);
							motherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.F), persons);
						}
						motherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.F), persons);
						if (motherfatherfathermother != null) {
							fathermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.M), persons);
							mothermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.F), persons);
						}
					}
					motherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.F), persons);
					if (motherfathermother != null) {
						fathermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.M), persons);
						if (fathermotherfathermother != null) {
							fatherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.M), persons);
							motherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.F), persons);
						}
						mothermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.F), persons);
						if (mothermotherfathermother != null) {
							fathermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.M), persons);
							mothermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.F), persons);
						}
					}
				}
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
				if (mothermother != null) {
					fathermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.M), persons);
					if (fathermothermother != null) {
						fatherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.M), persons);
						if (fatherfathermothermother != null) {
							fatherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.M), persons);
							motherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.F), persons);
						}
						motherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.F), persons);
						if (motherfathermothermother != null) {
							fathermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.M), persons);
							mothermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.F), persons);
						}
					}
					mothermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.F), persons);
					if (mothermothermother != null) {
						fathermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.M), persons);
						if (fathermothermothermother != null) {
							fatherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.M), persons);
							motherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.F), persons);
						}
						mothermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.F), persons);
						if (mothermothermothermother != null) {
							fathermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.M), persons);
							mothermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.F), persons);
						}
					}
				}
			}
			boolean isFatherFatherFatherFatherGeneration = fatherfatherfatherfatherfather != null
					|| motherfatherfatherfatherfather != null;
			boolean isFatherFatherMotherFatherGeneration = fatherfatherfathermotherfather != null
					|| motherfatherfathermotherfather != null;
			boolean isFatherFatherFatherMotherGeneration = fatherfatherfatherfathermother != null
					|| motherfatherfatherfathermother != null;
			boolean isFatherFatherMotherMotherGeneration = fatherfatherfathermothermother != null
					|| motherfatherfathermothermother != null;
			boolean isFatherMotherFatherFatherGeneration = fatherfathermotherfatherfather != null
					|| motherfathermotherfatherfather != null;
			boolean isFatherMotherMotherFatherGeneration = fatherfathermothermotherfather != null
					|| motherfathermothermotherfather != null;
			boolean isFatherMotherFatherMotherGeneration = fatherfathermotherfathermother != null
					|| motherfathermotherfathermother != null;
			boolean isFatherMotherMotherMotherGeneration = fatherfathermothermothermother != null
					|| motherfathermothermothermother != null;

			boolean isMotherFatherFatherFatherGeneration = fathermotherfatherfatherfather != null
					|| mothermotherfatherfatherfather != null;
			boolean isMotherFatherMotherFatherGeneration = fathermotherfathermotherfather != null
					|| mothermotherfathermotherfather != null;
			boolean isMotherFatherFatherMotherGeneration = fathermotherfatherfathermother != null
					|| mothermotherfatherfathermother != null;
			boolean isMotherFatherMotherMotherGeneration = fathermotherfathermothermother != null
					|| mothermotherfathermothermother != null;
			boolean isMotherMotherFatherFatherGeneration = fathermothermotherfatherfather != null
					|| mothermothermotherfatherfather != null;
			boolean isMotherMotherMotherFatherGeneration = fathermothermothermotherfather != null
					|| mothermothermothermotherfather != null;
			boolean isMotherMotherFatherMotherGeneration = fathermothermotherfathermother != null
					|| mothermothermotherfathermother != null;
			boolean isMotherMotherMotherMotherGeneration = fathermothermothermothermother != null
					|| mothermothermothermothermother != null;

			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherFatherFatherFatherGeneration || isFatherFatherMotherFatherGeneration
						|| isFatherFatherFatherMotherGeneration || isFatherFatherMotherMotherGeneration
						|| isFatherMotherFatherFatherGeneration || isFatherMotherMotherFatherGeneration
						|| isFatherMotherFatherMotherGeneration || isFatherMotherMotherMotherGeneration
						|| isMotherFatherFatherFatherGeneration || isMotherFatherMotherFatherGeneration
						|| isMotherFatherFatherMotherGeneration || isMotherFatherMotherMotherGeneration
						|| isMotherMotherFatherFatherGeneration || isMotherMotherMotherFatherGeneration
						|| isMotherMotherFatherMotherGeneration || isMotherMotherMotherMotherGeneration) {
					if (isFatherFatherFatherFatherGeneration) { // 1114 Done
						System.err.println(person.getId());
						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherMotherFatherGeneration) { // 1041 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"1\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherFatherMotherGeneration) { // 769 Done
						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherMotherMotherGeneration) { // 776 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherFatherFatherGeneration) { // 492
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"12\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr></table>");
					}
					if (isFatherMotherMotherFatherGeneration) { // 695
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermotherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					} 
					if (isFatherMotherFatherMotherGeneration) { // 886
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfathermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherMotherMotherGeneration) { // 660
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermothermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherFatherFatherGeneration) { // 343 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfatherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"14\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td>&nbsp;</td></tr></table>");
					}
					if (isMotherFatherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherFatherFatherMotherGeneration) { // 338 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfathermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherMotherMotherGeneration) { // 350 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherMotherMotherFatherGeneration) { // 361 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermotherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherFatherMotherGeneration) { // 779 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfathermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherMotherMotherGeneration) { // 359 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"12\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr></table>");
					}
				}else{
					writer.append("<tr align=\"center\"><td colspan=\"16\">");
					addPersonDetail(writer, person, "fifthgen.html", "sixthgen.html");
					writer.append("</td></tr></table>");
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "sixthgen.html", "seventhgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createSeventhGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			Person fatherfatherfather = null;
			Person motherfatherfather = null;
			Person fathermotherfather = null;
			Person mothermotherfather = null;
			Person fatherfathermother = null;
			Person motherfathermother = null;
			Person fathermothermother = null;
			Person mothermothermother = null;
			Person fatherfatherfatherfather = null;
			Person fathermotherfatherfather = null;
			Person fatherfathermotherfather = null;
			Person fathermothermotherfather = null;
			Person fatherfatherfathermother = null;
			Person fathermotherfathermother = null;
			Person fatherfathermothermother = null;
			Person fathermothermothermother = null;
			Person motherfatherfatherfather = null;
			Person mothermotherfatherfather = null;
			Person motherfathermotherfather = null;
			Person mothermothermotherfather = null;
			Person motherfatherfathermother = null;
			Person mothermotherfathermother = null;
			Person motherfathermothermother = null;
			Person mothermothermothermother = null;
			Person fatherfatherfatherfatherfather = null;
			Person fatherfathermotherfatherfather = null;
			Person fatherfatherfathermotherfather = null;
			Person fatherfathermothermotherfather = null;
			Person fatherfatherfatherfathermother = null;
			Person fatherfathermotherfathermother = null;
			Person fatherfatherfathermothermother = null;
			Person fatherfathermothermothermother = null;
			Person fathermotherfatherfatherfather = null;
			Person fathermothermotherfatherfather = null;
			Person fathermotherfathermotherfather = null;
			Person fathermothermothermotherfather = null;
			Person fathermotherfatherfathermother = null;
			Person fathermothermotherfathermother = null;
			Person fathermotherfathermothermother = null;
			Person fathermothermothermothermother = null;
			Person motherfatherfatherfatherfather = null;
			Person motherfathermotherfatherfather = null;
			Person motherfatherfathermotherfather = null;
			Person motherfathermothermotherfather = null;
			Person motherfatherfatherfathermother = null;
			Person motherfathermotherfathermother = null;
			Person motherfatherfathermothermother = null;
			Person motherfathermothermothermother = null;
			Person mothermotherfatherfatherfather = null;
			Person mothermothermotherfatherfather = null;
			Person mothermotherfathermotherfather = null;
			Person mothermothermothermotherfather = null;
			Person mothermotherfatherfathermother = null;
			Person mothermothermotherfathermother = null;
			Person mothermotherfathermothermother = null;
			Person mothermothermothermothermother = null;
			Person fatherfatherfatherfatherfatherfather = null;
			Person fatherfatherfathermotherfatherfather = null;
			Person fatherfatherfatherfathermotherfather = null;
			Person fatherfatherfathermothermotherfather = null;
			Person fatherfatherfatherfatherfathermother = null;
			Person fatherfatherfathermotherfathermother = null;
			Person fatherfatherfatherfathermothermother = null;
			Person fatherfatherfathermothermothermother = null;
			Person fatherfathermotherfatherfatherfather = null;
			Person fatherfathermothermotherfatherfather = null;
			Person fatherfathermotherfathermotherfather = null;
			Person fatherfathermothermothermotherfather = null;
			Person fatherfathermotherfatherfathermother = null;
			Person fatherfathermothermotherfathermother = null;
			Person fatherfathermotherfathermothermother = null;
			Person fatherfathermothermothermothermother = null;
			Person fathermotherfatherfatherfatherfather = null;
			Person fathermotherfathermotherfatherfather = null;
			Person fathermotherfatherfathermotherfather = null;
			Person fathermotherfathermothermotherfather = null;
			Person fathermotherfatherfatherfathermother = null;
			Person fathermotherfathermotherfathermother = null;
			Person fathermotherfatherfathermothermother = null;
			Person fathermotherfathermothermothermother = null;
			Person fathermothermotherfatherfatherfather = null;
			Person fathermothermothermotherfatherfather = null;
			Person fathermothermotherfathermotherfather = null;
			Person fathermothermothermothermotherfather = null;
			Person fathermothermotherfatherfathermother = null;
			Person fathermothermothermotherfathermother = null;
			Person fathermothermotherfathermothermother = null;
			Person fathermothermothermothermothermother = null;
			Person motherfatherfatherfatherfatherfather = null;
			Person motherfatherfathermotherfatherfather = null;
			Person motherfatherfatherfathermotherfather = null;
			Person motherfatherfathermothermotherfather = null;
			Person motherfatherfatherfatherfathermother = null;
			Person motherfatherfathermotherfathermother = null;
			Person motherfatherfatherfathermothermother = null;
			Person motherfatherfathermothermothermother = null;
			Person motherfathermotherfatherfatherfather = null;
			Person motherfathermothermotherfatherfather = null;
			Person motherfathermotherfathermotherfather = null;
			Person motherfathermothermothermotherfather = null;
			Person motherfathermotherfatherfathermother = null;
			Person motherfathermothermotherfathermother = null;
			Person motherfathermotherfathermothermother = null;
			Person motherfathermothermothermothermother = null;
			Person mothermotherfatherfatherfatherfather = null;
			Person mothermotherfathermotherfatherfather = null;
			Person mothermotherfatherfathermotherfather = null;
			Person mothermotherfathermothermotherfather = null;
			Person mothermotherfatherfatherfathermother = null;
			Person mothermotherfathermotherfathermother = null;
			Person mothermotherfatherfathermothermother = null;
			Person mothermotherfathermothermothermother = null;
			Person mothermothermotherfatherfatherfather = null;
			Person mothermothermothermotherfatherfather = null;
			Person mothermothermotherfathermotherfather = null;
			Person mothermothermothermothermotherfather = null;
			Person mothermothermotherfatherfathermother = null;
			Person mothermothermothermotherfathermother = null;
			Person mothermothermotherfathermothermother = null;
			Person mothermothermothermothermothermother = null;

			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				if (fatherfather != null) {
					fatherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.M), persons);
					if (fatherfatherfather != null) {
						fatherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.M), persons);
						if (fatherfatherfatherfather != null) {
							fatherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.M), persons);
							if (fatherfatherfatherfatherfather != null) {
								fatherfatherfatherfatherfatherfather = getPerson(
										findParent(families, fatherfatherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								motherfatherfatherfatherfatherfather = getPerson(
										findParent(families, fatherfatherfatherfatherfather.getId(), SexeEnum.F),
										persons);
							}
							motherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.F), persons);
							if (motherfatherfatherfatherfather != null) {
								fathermotherfatherfatherfatherfather = getPerson(
										findParent(families, motherfatherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								mothermotherfatherfatherfatherfather = getPerson(
										findParent(families, motherfatherfatherfatherfather.getId(), SexeEnum.F),
										persons);
							}
						}
						motherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.F), persons);
						if (motherfatherfatherfather != null) {
							fathermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.M), persons);
							if (fathermotherfatherfatherfather != null) {
								fatherfathermotherfatherfatherfather = getPerson(
										findParent(families, fathermotherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								motherfathermotherfatherfatherfather = getPerson(
										findParent(families, fathermotherfatherfatherfather.getId(), SexeEnum.F),
										persons);
							}
							mothermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.F), persons);
							if (mothermotherfatherfatherfather != null) {
								fathermothermotherfatherfatherfather = getPerson(
										findParent(families, mothermotherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								mothermothermotherfatherfatherfather = getPerson(
										findParent(families, mothermotherfatherfatherfather.getId(), SexeEnum.F),
										persons);
							}
						}
					}
					motherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.F), persons);
					if (motherfatherfather != null) {
						fathermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.M), persons);
						if (fathermotherfatherfather != null) {
							fatherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.M), persons);
							if (fatherfathermotherfatherfather != null) {
								fatherfatherfathermotherfatherfather = getPerson(
										findParent(families, fatherfathermotherfatherfather.getId(), SexeEnum.M),
										persons);
								motherfatherfathermotherfatherfather = getPerson(
										findParent(families, fatherfathermotherfatherfather.getId(), SexeEnum.F),
										persons);
							}
							motherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.F), persons);
							if (motherfathermotherfatherfather != null) {
								fathermotherfathermotherfatherfather = getPerson(
										findParent(families, motherfathermotherfatherfather.getId(), SexeEnum.M),
										persons);
								mothermotherfathermotherfatherfather = getPerson(
										findParent(families, motherfathermotherfatherfather.getId(), SexeEnum.F),
										persons);
							}
						}
						mothermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.F), persons);
						if (mothermotherfatherfather != null) {
							fathermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.M), persons);
							if (fathermothermotherfatherfather != null) {
								fatherfathermothermotherfatherfather = getPerson(
										findParent(families, fathermothermotherfatherfather.getId(), SexeEnum.M),
										persons);
								motherfathermothermotherfatherfather = getPerson(
										findParent(families, fathermothermotherfatherfather.getId(), SexeEnum.F),
										persons);
							}
							mothermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.F), persons);
							if (mothermothermotherfatherfather != null) {
								fathermothermothermotherfatherfather = getPerson(
										findParent(families, mothermothermotherfatherfather.getId(), SexeEnum.M),
										persons);
								mothermothermothermotherfatherfather = getPerson(
										findParent(families, mothermothermotherfatherfather.getId(), SexeEnum.F),
										persons);
							}
						}
					}
				}
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
				if (motherfather != null) {
					fathermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.M), persons);
					if (fathermotherfather != null) {
						fatherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.M), persons);
						if (fatherfathermotherfather != null) {
							fatherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.M), persons);
							if (fatherfatherfathermotherfather != null) {
								fatherfatherfatherfathermotherfather = getPerson(
										findParent(families, fatherfatherfathermotherfather.getId(), SexeEnum.M),
										persons);
								motherfatherfatherfathermotherfather = getPerson(
										findParent(families, fatherfatherfathermotherfather.getId(), SexeEnum.F),
										persons);
							}
							motherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.F), persons);
							if (motherfatherfathermotherfather != null) {
								fathermotherfatherfathermotherfather = getPerson(
										findParent(families, motherfatherfathermotherfather.getId(), SexeEnum.M),
										persons);
								mothermotherfatherfathermotherfather = getPerson(
										findParent(families, motherfatherfathermotherfather.getId(), SexeEnum.F),
										persons);
							}
						}
						motherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.F), persons);
						if (motherfathermotherfather != null) {
							fathermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.M), persons);
							if (fathermotherfathermotherfather != null) {
								fatherfathermotherfathermotherfather = getPerson(
										findParent(families, fathermotherfathermotherfather.getId(), SexeEnum.M),
										persons);
								motherfathermotherfathermotherfather = getPerson(
										findParent(families, fathermotherfathermotherfather.getId(), SexeEnum.F),
										persons);
							}
							mothermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.F), persons);
							if (mothermotherfathermotherfather != null) {
								fathermothermotherfathermotherfather = getPerson(
										findParent(families, mothermotherfathermotherfather.getId(), SexeEnum.M),
										persons);
								mothermothermotherfathermotherfather = getPerson(
										findParent(families, mothermotherfathermotherfather.getId(), SexeEnum.F),
										persons);
							}
						}
					}
					mothermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.F), persons);
					if (mothermotherfather != null) {
						fathermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.M), persons);
						if (fathermothermotherfather != null) {
							fatherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.M), persons);
							if (fatherfathermothermotherfather != null) {
								fatherfatherfathermothermotherfather = getPerson(
										findParent(families, fatherfathermothermotherfather.getId(), SexeEnum.M),
										persons);
								motherfatherfathermothermotherfather = getPerson(
										findParent(families, fatherfathermothermotherfather.getId(), SexeEnum.F),
										persons);
							}
							motherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.F), persons);
							if (motherfathermothermotherfather != null) {
								fathermotherfathermothermotherfather = getPerson(
										findParent(families, motherfathermothermotherfather.getId(), SexeEnum.M),
										persons);
								mothermotherfathermothermotherfather = getPerson(
										findParent(families, motherfathermothermotherfather.getId(), SexeEnum.F),
										persons);
							}
						}
						mothermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.F), persons);
						if (mothermothermotherfather != null) {
							fathermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.M), persons);
							if (fathermothermothermotherfather != null) {
								fatherfathermothermothermotherfather = getPerson(
										findParent(families, fathermothermothermotherfather.getId(), SexeEnum.M),
										persons);
								motherfathermothermothermotherfather = getPerson(
										findParent(families, fathermothermothermotherfather.getId(), SexeEnum.F),
										persons);
							}
							mothermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.F), persons);
							if (mothermothermothermotherfather != null) {
								fathermothermothermothermotherfather = getPerson(
										findParent(families, mothermothermothermotherfather.getId(), SexeEnum.M),
										persons);
								mothermothermothermothermotherfather = getPerson(
										findParent(families, mothermothermothermotherfather.getId(), SexeEnum.F),
										persons);
							}
						}
					}
				}
			}
			if (mother != null) {

				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				if (fathermother != null) {
					fatherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.M), persons);
					if (fatherfathermother != null) {
						fatherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.M), persons);
						if (fatherfatherfathermother != null) {
							fatherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.M), persons);
							if (fatherfatherfatherfathermother != null) {
								fatherfatherfatherfatherfathermother = getPerson(
										findParent(families, fatherfatherfatherfathermother.getId(), SexeEnum.M),
										persons);
								motherfatherfatherfatherfathermother = getPerson(
										findParent(families, fatherfatherfatherfathermother.getId(), SexeEnum.F),
										persons);
							}
							motherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.F), persons);
							if (motherfatherfatherfathermother != null) {
								fathermotherfatherfatherfathermother = getPerson(
										findParent(families, motherfatherfatherfathermother.getId(), SexeEnum.M),
										persons);
								mothermotherfatherfatherfathermother = getPerson(
										findParent(families, motherfatherfatherfathermother.getId(), SexeEnum.F),
										persons);
							}
						}
						motherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.F), persons);
						if (motherfatherfathermother != null) {
							fathermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.M), persons);
							if (fathermotherfatherfathermother != null) {
								fatherfathermotherfatherfathermother = getPerson(
										findParent(families, fathermotherfatherfathermother.getId(), SexeEnum.M),
										persons);
								motherfathermotherfatherfathermother = getPerson(
										findParent(families, fathermotherfatherfathermother.getId(), SexeEnum.F),
										persons);
							}
							mothermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.F), persons);
							if (mothermotherfatherfathermother != null) {
								fathermothermotherfatherfathermother = getPerson(
										findParent(families, mothermotherfatherfathermother.getId(), SexeEnum.M),
										persons);
								mothermothermotherfatherfathermother = getPerson(
										findParent(families, mothermotherfatherfathermother.getId(), SexeEnum.F),
										persons);
							}
						}
					}
					motherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.F), persons);
					if (motherfathermother != null) {
						fathermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.M), persons);
						if (fathermotherfathermother != null) {
							fatherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.M), persons);
							if (fatherfathermotherfathermother != null) {
								fatherfatherfathermotherfathermother = getPerson(
										findParent(families, fatherfathermotherfathermother.getId(), SexeEnum.M),
										persons);
								motherfatherfathermotherfathermother = getPerson(
										findParent(families, fatherfathermotherfathermother.getId(), SexeEnum.F),
										persons);
							}
							motherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.F), persons);
							if (motherfathermotherfathermother != null) {
								fathermotherfathermotherfathermother = getPerson(
										findParent(families, motherfathermotherfathermother.getId(), SexeEnum.M),
										persons);
								mothermotherfathermotherfathermother = getPerson(
										findParent(families, motherfathermotherfathermother.getId(), SexeEnum.F),
										persons);
							}
						}
						mothermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.F), persons);
						if (mothermotherfathermother != null) {
							fathermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.M), persons);
							if (fathermothermotherfathermother != null) {
								fatherfathermothermotherfathermother = getPerson(
										findParent(families, fathermothermotherfathermother.getId(), SexeEnum.M),
										persons);
								motherfathermothermotherfathermother = getPerson(
										findParent(families, fathermothermotherfathermother.getId(), SexeEnum.F),
										persons);
							}
							mothermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.F), persons);
							if (mothermothermotherfathermother != null) {
								fathermothermothermotherfathermother = getPerson(
										findParent(families, mothermothermotherfathermother.getId(), SexeEnum.M),
										persons);
								mothermothermothermotherfathermother = getPerson(
										findParent(families, mothermothermotherfathermother.getId(), SexeEnum.F),
										persons);
							}
						}
					}
				}
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
				if (mothermother != null) {
					fathermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.M), persons);
					if (fathermothermother != null) {
						fatherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.M), persons);
						if (fatherfathermothermother != null) {
							fatherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.M), persons);
							if (fatherfatherfathermothermother != null) {
								fatherfatherfatherfathermothermother = getPerson(
										findParent(families, fatherfatherfathermothermother.getId(), SexeEnum.M),
										persons);
								motherfatherfatherfathermothermother = getPerson(
										findParent(families, fatherfatherfathermothermother.getId(), SexeEnum.F),
										persons);
							}
							motherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.F), persons);
							if (motherfatherfathermothermother != null) {
								fathermotherfatherfathermothermother = getPerson(
										findParent(families, motherfatherfathermothermother.getId(), SexeEnum.M),
										persons);
								mothermotherfatherfathermothermother = getPerson(
										findParent(families, motherfatherfathermothermother.getId(), SexeEnum.F),
										persons);
							}
						}
						motherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.F), persons);
						if (motherfathermothermother != null) {
							fathermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.M), persons);
							if (fathermotherfathermothermother != null) {
								fatherfathermotherfathermothermother = getPerson(
										findParent(families, fathermotherfathermothermother.getId(), SexeEnum.M),
										persons);
								motherfathermotherfathermothermother = getPerson(
										findParent(families, fathermotherfathermothermother.getId(), SexeEnum.F),
										persons);
							}
							mothermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.F), persons);
							if (mothermotherfathermothermother != null) {
								fathermothermotherfathermothermother = getPerson(
										findParent(families, mothermotherfathermothermother.getId(), SexeEnum.M),
										persons);
								mothermothermotherfathermothermother = getPerson(
										findParent(families, mothermotherfathermothermother.getId(), SexeEnum.F),
										persons);
							}
						}
					}
					mothermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.F), persons);
					if (mothermothermother != null) {
						fathermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.M), persons);
						if (fathermothermothermother != null) {
							fatherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.M), persons);
							if (fatherfathermothermothermother != null) {
								fatherfatherfathermothermothermother = getPerson(
										findParent(families, fatherfathermothermothermother.getId(), SexeEnum.M),
										persons);
								motherfatherfathermothermothermother = getPerson(
										findParent(families, fatherfathermothermothermother.getId(), SexeEnum.F),
										persons);
							}
							motherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.F), persons);
							if (motherfathermothermothermother != null) {
								fathermotherfathermothermothermother = getPerson(
										findParent(families, motherfathermothermothermother.getId(), SexeEnum.M),
										persons);
								mothermotherfathermothermothermother = getPerson(
										findParent(families, motherfathermothermothermother.getId(), SexeEnum.F),
										persons);
							}
						}
						mothermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.F), persons);
						if (mothermothermothermother != null) {
							fathermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.M), persons);
							if (fathermothermothermothermother != null) {
								fatherfathermothermothermothermother = getPerson(
										findParent(families, fathermothermothermothermother.getId(), SexeEnum.M),
										persons);
								motherfathermothermothermothermother = getPerson(
										findParent(families, fathermothermothermothermother.getId(), SexeEnum.F),
										persons);
							}
							mothermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.F), persons);
							if (mothermothermothermothermother != null) {
								fathermothermothermothermothermother = getPerson(
										findParent(families, mothermothermothermothermother.getId(), SexeEnum.M),
										persons);
								mothermothermothermothermothermother = getPerson(
										findParent(families, mothermothermothermothermother.getId(), SexeEnum.F),
										persons);
							}
						}
					}
				}
			}
			boolean isFatherFatherFatherFatherFatherGeneration = fatherfatherfatherfatherfatherfather != null
					|| motherfatherfatherfatherfatherfather != null;
			boolean isFatherFatherFatherMotherFatherGeneration = fatherfatherfatherfathermotherfather != null
					|| motherfatherfatherfathermotherfather != null;
			boolean isFatherFatherFatherFatherMotherGeneration = fatherfatherfatherfatherfathermother != null
					|| motherfatherfatherfatherfathermother != null;
			boolean isFatherFatherFatherMotherMotherGeneration = fatherfatherfatherfathermothermother != null
					|| motherfatherfatherfathermothermother != null;
			boolean isFatherFatherMotherFatherFatherGeneration = fatherfatherfathermotherfatherfather != null
					|| motherfatherfathermotherfatherfather != null;
			boolean isFatherFatherMotherMotherFatherGeneration = fatherfatherfathermothermotherfather != null
					|| motherfatherfathermothermotherfather != null;
			boolean isFatherFatherMotherFatherMotherGeneration = fatherfatherfathermotherfathermother != null
					|| motherfatherfathermotherfathermother != null;
			boolean isFatherFatherMotherMotherMotherGeneration = fatherfatherfathermothermothermother != null
					|| motherfatherfathermothermothermother != null;
			boolean isFatherMotherFatherFatherFatherGeneration = fatherfathermotherfatherfatherfather != null
					|| motherfathermotherfatherfatherfather != null;
			boolean isFatherMotherFatherMotherFatherGeneration = fatherfathermotherfathermotherfather != null
					|| motherfathermotherfathermotherfather != null;
			boolean isFatherMotherFatherFatherMotherGeneration = fatherfathermotherfatherfathermother != null
					|| motherfathermotherfatherfathermother != null;
			boolean isFatherMotherFatherMotherMotherGeneration = fatherfathermotherfathermothermother != null
					|| motherfathermotherfathermothermother != null;
			boolean isFatherMotherMotherFatherFatherGeneration = fatherfathermothermotherfatherfather != null
					|| motherfathermothermotherfatherfather != null;
			boolean isFatherMotherMotherMotherFatherGeneration = fatherfathermothermothermotherfather != null
					|| motherfathermothermothermotherfather != null;
			boolean isFatherMotherMotherFatherMotherGeneration = fatherfathermothermotherfathermother != null
					|| motherfathermothermotherfathermother != null;
			boolean isFatherMotherMotherMotherMotherGeneration = fatherfathermothermothermothermother != null
					|| motherfathermothermothermothermother != null;

			boolean isMotherFatherFatherFatherFatherGeneration = fathermotherfatherfatherfatherfather != null
					|| mothermotherfatherfatherfatherfather != null;
			boolean isMotherFatherFatherMotherFatherGeneration = fathermotherfatherfathermotherfather != null
					|| mothermotherfatherfathermotherfather != null;
			boolean isMotherFatherFatherFatherMotherGeneration = fathermotherfatherfatherfathermother != null
					|| mothermotherfatherfatherfathermother != null;
			boolean isMotherFatherFatherMotherMotherGeneration = fathermotherfatherfathermothermother != null
					|| mothermotherfatherfathermothermother != null;
			boolean isMotherFatherMotherFatherFatherGeneration = fathermotherfathermotherfatherfather != null
					|| mothermotherfathermotherfatherfather != null;
			boolean isMotherFatherMotherMotherFatherGeneration = fathermotherfathermothermotherfather != null
					|| mothermotherfathermothermotherfather != null;
			boolean isMotherFatherMotherFatherMotherGeneration = fathermotherfathermotherfathermother != null
					|| mothermotherfathermotherfathermother != null;
			boolean isMotherFatherMotherMotherMotherGeneration = fathermotherfathermothermothermother != null
					|| mothermotherfathermothermothermother != null;
			boolean isMotherMotherFatherFatherFatherGeneration = fathermothermotherfatherfatherfather != null
					|| mothermothermotherfatherfatherfather != null;
			boolean isMotherMotherFatherMotherFatherGeneration = fathermothermotherfathermotherfather != null
					|| mothermothermotherfathermotherfather != null;
			boolean isMotherMotherFatherFatherMotherGeneration = fathermothermotherfatherfathermother != null
					|| mothermothermotherfatherfathermother != null;
			boolean isMotherMotherFatherMotherMotherGeneration = fathermothermotherfathermothermother != null
					|| mothermothermotherfathermothermother != null;
			boolean isMotherMotherMotherFatherFatherGeneration = fathermothermothermotherfatherfather != null
					|| mothermothermothermotherfatherfather != null;
			boolean isMotherMotherMotherMotherFatherGeneration = fathermothermothermothermotherfather != null
					|| mothermothermothermothermotherfather != null;
			boolean isMotherMotherMotherFatherMotherGeneration = fathermothermothermotherfathermother != null
					|| mothermothermothermotherfathermother != null;
			boolean isMotherMotherMotherMotherMotherGeneration = fathermothermothermothermothermother != null
					|| mothermothermothermothermothermother != null;

			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherFatherFatherFatherFatherGeneration || isFatherFatherFatherMotherFatherGeneration
						|| isFatherFatherFatherFatherMotherGeneration || isFatherFatherFatherMotherMotherGeneration
						|| isFatherFatherMotherFatherFatherGeneration || isFatherFatherMotherMotherFatherGeneration
						|| isFatherFatherMotherFatherMotherGeneration || isFatherFatherMotherMotherMotherGeneration
						|| isFatherMotherFatherFatherFatherGeneration || isFatherMotherFatherMotherFatherGeneration
						|| isFatherMotherFatherFatherMotherGeneration || isFatherMotherFatherMotherMotherGeneration
						|| isFatherMotherMotherFatherFatherGeneration || isFatherMotherMotherMotherFatherGeneration
						|| isFatherMotherMotherFatherMotherGeneration || isFatherMotherMotherMotherMotherGeneration
						|| isMotherFatherFatherFatherFatherGeneration || isMotherFatherFatherMotherFatherGeneration
						|| isMotherFatherFatherFatherMotherGeneration || isMotherFatherFatherMotherMotherGeneration
						|| isMotherFatherMotherFatherFatherGeneration || isMotherFatherMotherMotherFatherGeneration
						|| isMotherFatherMotherFatherMotherGeneration || isMotherFatherMotherMotherMotherGeneration
						|| isMotherMotherFatherFatherFatherGeneration || isMotherMotherFatherMotherFatherGeneration
						|| isMotherMotherFatherFatherMotherGeneration || isMotherMotherFatherMotherMotherGeneration
						|| isMotherMotherMotherFatherFatherGeneration || isMotherMotherMotherMotherFatherGeneration
						|| isMotherMotherMotherFatherMotherGeneration || isMotherMotherMotherMotherMotherGeneration) {
					if (isFatherFatherFatherFatherFatherGeneration) { // 420 Done
						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"5\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherFatherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					} 
					if (isFatherFatherFatherFatherMotherGeneration) { // 824 Done
						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"7\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"6\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"14\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherFatherMotherMotherGeneration) { // 427 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"6\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"6\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherMotherFatherFatherGeneration) { // 1098 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"5\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"8\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10+\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"8\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherMotherMotherFatherGeneration) { // 1021 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");

					}
					if (isFatherFatherMotherFatherMotherGeneration) { // 901 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherFatherMotherMotherMotherGeneration) { // 1003 Done
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						if (father != null) {
							addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						} else {
							writer.append("?");
						}
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherFatherFatherFatherGeneration) { // 844
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherFatherMotherFatherGeneration) { // 1104
						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherFatherFatherMotherGeneration) { // 858
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isFatherMotherFatherMotherMotherGeneration) { // 1075
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfathermothermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");		
					}
					if (isFatherMotherMotherFatherFatherGeneration) { // 616
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");		
					}
					if (isFatherMotherMotherMotherFatherGeneration) { // 659
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermothermotherfather, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");					}
					if (isFatherMotherMotherFatherMotherGeneration) { // 613
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermotherfathermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");					}
					if (isFatherMotherMotherMotherMotherGeneration) { // 1056
						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermothermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermothermothermother, "", "firstgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"12\">&nbsp;</td><td colspan=\"4\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");						}
					if (isMotherFatherFatherFatherFatherGeneration) { // 835 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfatherfatherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"4\">&nbsp;</td><td colspan=\"12\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"5\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherFatherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherFatherFatherFatherMotherGeneration) { // 1079 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfatherfathermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfatherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"5\">&nbsp;</td><td colspan=\"11\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherFatherMotherMotherGeneration) { // 337 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfathermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherFatherMotherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherFatherMotherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherFatherMotherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherFatherMotherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherMotherFatherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherMotherFatherMotherFatherGeneration) { // 1126 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfathermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfathermotherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"1\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherFatherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isMotherMotherFatherMotherMotherGeneration) { // 324 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfathermothermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfathermothermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"1\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermothermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermothermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");					}
					if (isMotherMotherMotherFatherFatherGeneration) { // 951 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermotherfatherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermotherfatherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfatherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfatherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfatherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"6\">&nbsp;</td><td colspan=\"10\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td>&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherMotherMotherFatherGeneration) { // 939 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermothermotherfather, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermothermotherfather, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermotherfather, "firstgen.html", "secondgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"4\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfather, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfather, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfather, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherMotherFatherMotherGeneration) { // 947 Done
						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermothermotherfathermother, "", "firstgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermothermotherfathermother, "", "firstgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr align=\"center\"><td colspan=\"11\">&nbsp;</td><td colspan=\"5\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"2\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermothermotherfathermother, "firstgen.html", "secondgen.html");
						writer.append("</td><td colspan=\"2\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"5\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"5\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"10\">&nbsp;</td><td colspan=\"6\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td>&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermotherfathermother, "secondgen.html", "thirdgen.html");
						writer.append("</td><td colspan=\"3\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"6\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, fatherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, motherfathermother, "thirdgen.html", "fourthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"7\">&nbsp;</td><td colspan=\"9\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"4\">&nbsp;</td><td colspan=\"6\">");
						addPersonDetail(writer, fathermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mothermother, "fourthgen.html", "fifthgen.html");
						writer.append("</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"7\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"3\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"9\">&nbsp;</td><td colspan=\"7\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"6\">");
						addPersonDetail(writer, father, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"6\">");
						addPersonDetail(writer, mother, "fifthgen.html", "sixthgen.html");
						writer.append("</td><td colspan=\"4\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"3\">&nbsp;</td><td class=\"bottom_left\">&nbsp;</td><td colspan=\"5\" class=\"bottom_right\">&nbsp;</td><td colspan=\"7\">&nbsp;</td></tr>");
						writer.append("<tr><td colspan=\"8\">&nbsp;</td><td colspan=\"8\" class=\"left\">&nbsp;</td></tr>");

						writer.append("<tr align=\"center\"><td colspan=\"16\">");
						addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
						writer.append("</td></tr></table>");
					}
					if (isMotherMotherMotherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
				}else{
					writer.append("<tr align=\"center\"><td colspan=\"16\">");
					addPersonDetail(writer, person, "sixthgen.html", "seventhgen.html");
					writer.append("</td></tr></table>");
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "seventhgen.html", "eigthgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	public static void createEigthGenPages(List<Person> genPersons, Set<Person> persons, Set<Family> families) {
		BufferedWriter writer = null;
		for (Person person : genPersons) {
			Person father = getPerson(findParent(families, person.getId(), SexeEnum.M), persons);
			Person mother = getPerson(findParent(families, person.getId(), SexeEnum.F), persons);
			Person fatherfather = null;
			Person motherfather = null;
			Person fathermother = null;
			Person mothermother = null;
			Person fatherfatherfather = null;
			Person motherfatherfather = null;
			Person fathermotherfather = null;
			Person mothermotherfather = null;
			Person fatherfathermother = null;
			Person motherfathermother = null;
			Person fathermothermother = null;
			Person mothermothermother = null;
			Person fatherfatherfatherfather = null;
			Person fathermotherfatherfather = null;
			Person fatherfathermotherfather = null;
			Person fathermothermotherfather = null;
			Person fatherfatherfathermother = null;
			Person fathermotherfathermother = null;
			Person fatherfathermothermother = null;
			Person fathermothermothermother = null;
			Person motherfatherfatherfather = null;
			Person mothermotherfatherfather = null;
			Person motherfathermotherfather = null;
			Person mothermothermotherfather = null;
			Person motherfatherfathermother = null;
			Person mothermotherfathermother = null;
			Person motherfathermothermother = null;
			Person mothermothermothermother = null;
			Person fatherfatherfatherfatherfather = null;
			Person fatherfathermotherfatherfather = null;
			Person fatherfatherfathermotherfather = null;
			Person fatherfathermothermotherfather = null;
			Person fatherfatherfatherfathermother = null;
			Person fatherfathermotherfathermother = null;
			Person fatherfatherfathermothermother = null;
			Person fatherfathermothermothermother = null;
			Person fathermotherfatherfatherfather = null;
			Person fathermothermotherfatherfather = null;
			Person fathermotherfathermotherfather = null;
			Person fathermothermothermotherfather = null;
			Person fathermotherfatherfathermother = null;
			Person fathermothermotherfathermother = null;
			Person fathermotherfathermothermother = null;
			Person fathermothermothermothermother = null;
			Person motherfatherfatherfatherfather = null;
			Person motherfathermotherfatherfather = null;
			Person motherfatherfathermotherfather = null;
			Person motherfathermothermotherfather = null;
			Person motherfatherfatherfathermother = null;
			Person motherfathermotherfathermother = null;
			Person motherfatherfathermothermother = null;
			Person motherfathermothermothermother = null;
			Person mothermotherfatherfatherfather = null;
			Person mothermothermotherfatherfather = null;
			Person mothermotherfathermotherfather = null;
			Person mothermothermothermotherfather = null;
			Person mothermotherfatherfathermother = null;
			Person mothermothermotherfathermother = null;
			Person mothermotherfathermothermother = null;
			Person mothermothermothermothermother = null;
			Person fatherfatherfatherfatherfatherfather = null;
			Person fatherfatherfathermotherfatherfather = null;
			Person fatherfatherfatherfathermotherfather = null;
			Person fatherfatherfathermothermotherfather = null;
			Person fatherfatherfatherfatherfathermother = null;
			Person fatherfatherfathermotherfathermother = null;
			Person fatherfatherfatherfathermothermother = null;
			Person fatherfatherfathermothermothermother = null;
			Person fatherfathermotherfatherfatherfather = null;
			Person fatherfathermothermotherfatherfather = null;
			Person fatherfathermotherfathermotherfather = null;
			Person fatherfathermothermothermotherfather = null;
			Person fatherfathermotherfatherfathermother = null;
			Person fatherfathermothermotherfathermother = null;
			Person fatherfathermotherfathermothermother = null;
			Person fatherfathermothermothermothermother = null;
			Person fathermotherfatherfatherfatherfather = null;
			Person fathermotherfathermotherfatherfather = null;
			Person fathermotherfatherfathermotherfather = null;
			Person fathermotherfathermothermotherfather = null;
			Person fathermotherfatherfatherfathermother = null;
			Person fathermotherfathermotherfathermother = null;
			Person fathermotherfatherfathermothermother = null;
			Person fathermotherfathermothermothermother = null;
			Person fathermothermotherfatherfatherfather = null;
			Person fathermothermothermotherfatherfather = null;
			Person fathermothermotherfathermotherfather = null;
			Person fathermothermothermothermotherfather = null;
			Person fathermothermotherfatherfathermother = null;
			Person fathermothermothermotherfathermother = null;
			Person fathermothermotherfathermothermother = null;
			Person fathermothermothermothermothermother = null;
			Person motherfatherfatherfatherfatherfather = null;
			Person motherfatherfathermotherfatherfather = null;
			Person motherfatherfatherfathermotherfather = null;
			Person motherfatherfathermothermotherfather = null;
			Person motherfatherfatherfatherfathermother = null;
			Person motherfatherfathermotherfathermother = null;
			Person motherfatherfatherfathermothermother = null;
			Person motherfatherfathermothermothermother = null;
			Person motherfathermotherfatherfatherfather = null;
			Person motherfathermothermotherfatherfather = null;
			Person motherfathermotherfathermotherfather = null;
			Person motherfathermothermothermotherfather = null;
			Person motherfathermotherfatherfathermother = null;
			Person motherfathermothermotherfathermother = null;
			Person motherfathermotherfathermothermother = null;
			Person motherfathermothermothermothermother = null;
			Person mothermotherfatherfatherfatherfather = null;
			Person mothermotherfathermotherfatherfather = null;
			Person mothermotherfatherfathermotherfather = null;
			Person mothermotherfathermothermotherfather = null;
			Person mothermotherfatherfatherfathermother = null;
			Person mothermotherfathermotherfathermother = null;
			Person mothermotherfatherfathermothermother = null;
			Person mothermotherfathermothermothermother = null;
			Person mothermothermotherfatherfatherfather = null;
			Person mothermothermothermotherfatherfather = null;
			Person mothermothermotherfathermotherfather = null;
			Person mothermothermothermothermotherfather = null;
			Person mothermothermotherfatherfathermother = null;
			Person mothermothermothermotherfathermother = null;
			Person mothermothermotherfathermothermother = null;
			Person mothermothermothermothermothermother = null;
			Person fatherfatherfatherfatherfatherfatherfather = null;
			Person fatherfatherfatherfathermotherfatherfather = null;
			Person fatherfatherfatherfatherfathermotherfather = null;
			Person fatherfatherfatherfathermothermotherfather = null;
			Person fatherfatherfatherfatherfatherfathermother = null;
			Person fatherfatherfatherfathermotherfathermother = null;
			Person fatherfatherfatherfatherfathermothermother = null;
			Person fatherfatherfatherfathermothermothermother = null;
			Person fatherfatherfathermotherfatherfatherfather = null;
			Person fatherfatherfathermothermotherfatherfather = null;
			Person fatherfatherfathermotherfathermotherfather = null;
			Person fatherfatherfathermothermothermotherfather = null;
			Person fatherfatherfathermotherfatherfathermother = null;
			Person fatherfatherfathermothermotherfathermother = null;
			Person fatherfatherfathermotherfathermothermother = null;
			Person fatherfatherfathermothermothermothermother = null;
			Person fatherfathermotherfatherfatherfatherfather = null;
			Person fatherfathermotherfathermotherfatherfather = null;
			Person fatherfathermotherfatherfathermotherfather = null;
			Person fatherfathermotherfathermothermotherfather = null;
			Person fatherfathermotherfatherfatherfathermother = null;
			Person fatherfathermotherfathermotherfathermother = null;
			Person fatherfathermotherfatherfathermothermother = null;
			Person fatherfathermotherfathermothermothermother = null;
			Person fatherfathermothermotherfatherfatherfather = null;
			Person fatherfathermothermothermotherfatherfather = null;
			Person fatherfathermothermotherfathermotherfather = null;
			Person fatherfathermothermothermothermotherfather = null;
			Person fatherfathermothermotherfatherfathermother = null;
			Person fatherfathermothermothermotherfathermother = null;
			Person fatherfathermothermotherfathermothermother = null;
			Person fatherfathermothermothermothermothermother = null;
			Person fathermotherfatherfatherfatherfatherfather = null;
			Person fathermotherfatherfathermotherfatherfather = null;
			Person fathermotherfatherfatherfathermotherfather = null;
			Person fathermotherfatherfathermothermotherfather = null;
			Person fathermotherfatherfatherfatherfathermother = null;
			Person fathermotherfatherfathermotherfathermother = null;
			Person fathermotherfatherfatherfathermothermother = null;
			Person fathermotherfatherfathermothermothermother = null;
			Person fathermotherfathermotherfatherfatherfather = null;
			Person fathermotherfathermothermotherfatherfather = null;
			Person fathermotherfathermotherfathermotherfather = null;
			Person fathermotherfathermothermothermotherfather = null;
			Person fathermotherfathermotherfatherfathermother = null;
			Person fathermotherfathermothermotherfathermother = null;
			Person fathermotherfathermotherfathermothermother = null;
			Person fathermotherfathermothermothermothermother = null;
			Person fathermothermotherfatherfatherfatherfather = null;
			Person fathermothermotherfathermotherfatherfather = null;
			Person fathermothermotherfatherfathermotherfather = null;
			Person fathermothermotherfathermothermotherfather = null;
			Person fathermothermotherfatherfatherfathermother = null;
			Person fathermothermotherfathermotherfathermother = null;
			Person fathermothermotherfatherfathermothermother = null;
			Person fathermothermotherfathermothermothermother = null;
			Person fathermothermothermotherfatherfatherfather = null;
			Person fathermothermothermothermotherfatherfather = null;
			Person fathermothermothermotherfathermotherfather = null;
			Person fathermothermothermothermothermotherfather = null;
			Person fathermothermothermotherfatherfathermother = null;
			Person fathermothermothermothermotherfathermother = null;
			Person fathermothermothermotherfathermothermother = null;
			Person fathermothermothermothermothermothermother = null;
			Person motherfatherfatherfatherfatherfatherfather = null;
			Person motherfatherfatherfathermotherfatherfather = null;
			Person motherfatherfatherfatherfathermotherfather = null;
			Person motherfatherfatherfathermothermotherfather = null;
			Person motherfatherfatherfatherfatherfathermother = null;
			Person motherfatherfatherfathermotherfathermother = null;
			Person motherfatherfatherfatherfathermothermother = null;
			Person motherfatherfatherfathermothermothermother = null;
			Person motherfatherfathermotherfatherfatherfather = null;
			Person motherfatherfathermothermotherfatherfather = null;
			Person motherfatherfathermotherfathermotherfather = null;
			Person motherfatherfathermothermothermotherfather = null;
			Person motherfatherfathermotherfatherfathermother = null;
			Person motherfatherfathermothermotherfathermother = null;
			Person motherfatherfathermotherfathermothermother = null;
			Person motherfatherfathermothermothermothermother = null;
			Person motherfathermotherfatherfatherfatherfather = null;
			Person motherfathermotherfathermotherfatherfather = null;
			Person motherfathermotherfatherfathermotherfather = null;
			Person motherfathermotherfathermothermotherfather = null;
			Person motherfathermotherfatherfatherfathermother = null;
			Person motherfathermotherfathermotherfathermother = null;
			Person motherfathermotherfatherfathermothermother = null;
			Person motherfathermotherfathermothermothermother = null;
			Person motherfathermothermotherfatherfatherfather = null;
			Person motherfathermothermothermotherfatherfather = null;
			Person motherfathermothermotherfathermotherfather = null;
			Person motherfathermothermothermothermotherfather = null;
			Person motherfathermothermotherfatherfathermother = null;
			Person motherfathermothermothermotherfathermother = null;
			Person motherfathermothermotherfathermothermother = null;
			Person motherfathermothermothermothermothermother = null;
			Person mothermotherfatherfatherfatherfatherfather = null;
			Person mothermotherfatherfathermotherfatherfather = null;
			Person mothermotherfatherfatherfathermotherfather = null;
			Person mothermotherfatherfathermothermotherfather = null;
			Person mothermotherfatherfatherfatherfathermother = null;
			Person mothermotherfatherfathermotherfathermother = null;
			Person mothermotherfatherfatherfathermothermother = null;
			Person mothermotherfatherfathermothermothermother = null;
			Person mothermotherfathermotherfatherfatherfather = null;
			Person mothermotherfathermothermotherfatherfather = null;
			Person mothermotherfathermotherfathermotherfather = null;
			Person mothermotherfathermothermothermotherfather = null;
			Person mothermotherfathermotherfatherfathermother = null;
			Person mothermotherfathermothermotherfathermother = null;
			Person mothermotherfathermotherfathermothermother = null;
			Person mothermotherfathermothermothermothermother = null;
			Person mothermothermotherfatherfatherfatherfather = null;
			Person mothermothermotherfathermotherfatherfather = null;
			Person mothermothermotherfatherfathermotherfather = null;
			Person mothermothermotherfathermothermotherfather = null;
			Person mothermothermotherfatherfatherfathermother = null;
			Person mothermothermotherfathermotherfathermother = null;
			Person mothermothermotherfatherfathermothermother = null;
			Person mothermothermotherfathermothermothermother = null;
			Person mothermothermothermotherfatherfatherfather = null;
			Person mothermothermothermothermotherfatherfather = null;
			Person mothermothermothermotherfathermotherfather = null;
			Person mothermothermothermothermothermotherfather = null;
			Person mothermothermothermotherfatherfathermother = null;
			Person mothermothermothermothermotherfathermother = null;
			Person mothermothermothermotherfathermothermother = null;
			Person mothermothermothermothermothermothermother = null;

			if (father != null) {
				fatherfather = getPerson(findParent(families, father.getId(), SexeEnum.M), persons);
				if (fatherfather != null) {
					fatherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.M), persons);
					if (fatherfatherfather != null) {
						fatherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.M), persons);
						if (fatherfatherfatherfather != null) {
							fatherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.M), persons);
							if (fatherfatherfatherfatherfather != null) {
								fatherfatherfatherfatherfatherfather = getPerson(
										findParent(families, fatherfatherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfatherfatherfatherfather != null) {
									fatherfatherfatherfatherfatherfatherfather = getPerson(
											findParent(families, fatherfatherfatherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfatherfatherfatherfather = getPerson(
											findParent(families, fatherfatherfatherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfatherfatherfatherfather = getPerson(
										findParent(families, fatherfatherfatherfatherfather.getId(), SexeEnum.F),
										persons);
								if (motherfatherfatherfatherfatherfather != null) {
									fathermotherfatherfatherfatherfatherfather = getPerson(
											findParent(families, motherfatherfatherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfatherfatherfatherfather = getPerson(
											findParent(families, motherfatherfatherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
							motherfatherfatherfatherfather = getPerson(
									findParent(families, fatherfatherfatherfather.getId(), SexeEnum.F), persons);
							if (motherfatherfatherfatherfather != null) {
								fathermotherfatherfatherfatherfather = getPerson(
										findParent(families, motherfatherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fathermotherfatherfatherfatherfather != null) {
									fatherfathermotherfatherfatherfatherfather = getPerson(
											findParent(families, fathermotherfatherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfathermotherfatherfatherfatherfather = getPerson(
											findParent(families, fathermotherfatherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfatherfatherfatherfather = getPerson(
										findParent(families, motherfatherfatherfatherfather.getId(), SexeEnum.F),
										persons);
								if (mothermotherfatherfatherfatherfather != null) {
									fathermothermotherfatherfatherfatherfather = getPerson(
											findParent(families, mothermotherfatherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermothermotherfatherfatherfatherfather = getPerson(
											findParent(families, mothermotherfatherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
						}
						motherfatherfatherfather = getPerson(
								findParent(families, fatherfatherfather.getId(), SexeEnum.F), persons);
						if (motherfatherfatherfather != null) {
							fathermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.M), persons);
							if (fathermotherfatherfatherfather != null) {
								fatherfathermotherfatherfatherfather = getPerson(
										findParent(families, fathermotherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfathermotherfatherfatherfather != null) {
									fatherfatherfathermotherfatherfatherfather = getPerson(
											findParent(families, fatherfathermotherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfathermotherfatherfatherfather = getPerson(
											findParent(families, fatherfathermotherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}

								motherfathermotherfatherfatherfather = getPerson(
										findParent(families, fathermotherfatherfatherfather.getId(), SexeEnum.F),
										persons);
								if (motherfathermotherfatherfatherfather != null) {
									fathermotherfathermotherfatherfatherfather = getPerson(
											findParent(families, motherfathermotherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfathermotherfatherfatherfather = getPerson(
											findParent(families, motherfathermotherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
							mothermotherfatherfatherfather = getPerson(
									findParent(families, motherfatherfatherfather.getId(), SexeEnum.F), persons);
							if (mothermotherfatherfatherfather != null) {
								fathermothermotherfatherfatherfather = getPerson(
										findParent(families, mothermotherfatherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fathermothermotherfatherfatherfather != null) {
									fatherfathermothermotherfatherfatherfather = getPerson(
											findParent(families, fathermothermotherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfathermothermotherfatherfatherfather = getPerson(
											findParent(families, fathermothermotherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermothermotherfatherfatherfather = getPerson(
										findParent(families, mothermotherfatherfatherfather.getId(), SexeEnum.F),
										persons);
								if (mothermothermotherfatherfatherfather != null) {
									fathermothermothermotherfatherfatherfather = getPerson(
											findParent(families, mothermothermotherfatherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermothermothermotherfatherfatherfather = getPerson(
											findParent(families, mothermothermotherfatherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
						}
					}
					motherfatherfather = getPerson(findParent(families, fatherfather.getId(), SexeEnum.F), persons);
					if (motherfatherfather != null) {
						fathermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.M), persons);
						if (fathermotherfatherfather != null) {
							fatherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.M), persons);
							if (fatherfathermotherfatherfather != null) {
								fatherfatherfathermotherfatherfather = getPerson(
										findParent(families, fatherfathermotherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfathermotherfatherfather != null) {
									fatherfatherfatherfathermotherfatherfather = getPerson(
											findParent(families, fatherfatherfathermotherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfathermotherfatherfather = getPerson(
											findParent(families, fatherfatherfathermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfathermotherfatherfather = getPerson(
										findParent(families, fatherfathermotherfatherfather.getId(), SexeEnum.F),
										persons);
								if (motherfatherfathermotherfatherfather != null) {
									fathermotherfatherfathermotherfatherfather = getPerson(
											findParent(families, motherfatherfathermotherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfathermotherfatherfather = getPerson(
											findParent(families, motherfatherfathermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
							motherfathermotherfatherfather = getPerson(
									findParent(families, fathermotherfatherfather.getId(), SexeEnum.F), persons);
							if (motherfathermotherfatherfather != null) {
								fathermotherfathermotherfatherfather = getPerson(
										findParent(families, motherfathermotherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fathermotherfathermotherfatherfather != null) {
									fatherfathermotherfathermotherfatherfather = getPerson(
											findParent(families, fathermotherfathermotherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfathermotherfathermotherfatherfather = getPerson(
											findParent(families, fathermotherfathermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfathermotherfatherfather = getPerson(
										findParent(families, motherfathermotherfatherfather.getId(), SexeEnum.F),
										persons);
								if (mothermotherfathermotherfatherfather != null) {
									fathermothermotherfathermotherfatherfather = getPerson(
											findParent(families, mothermotherfathermotherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermothermotherfathermotherfatherfather = getPerson(
											findParent(families, mothermotherfathermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}

							}
						}
						mothermotherfatherfather = getPerson(
								findParent(families, motherfatherfather.getId(), SexeEnum.F), persons);
						if (mothermotherfatherfather != null) {
							fathermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.M), persons);
							if (fathermothermotherfatherfather != null) {
								fatherfathermothermotherfatherfather = getPerson(
										findParent(families, fathermothermotherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfathermothermotherfatherfather != null) {
									fatherfatherfathermothermotherfatherfather = getPerson(
											findParent(families, fatherfathermothermotherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfathermothermotherfatherfather = getPerson(
											findParent(families, fatherfathermothermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfathermothermotherfatherfather = getPerson(
										findParent(families, fathermothermotherfatherfather.getId(), SexeEnum.F),
										persons);
								if (motherfathermothermotherfatherfather != null) {
									fathermotherfathermothermotherfatherfather = getPerson(
											findParent(families, motherfathermothermotherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfathermothermotherfatherfather = getPerson(
											findParent(families, motherfathermothermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermothermotherfatherfather = getPerson(
									findParent(families, mothermotherfatherfather.getId(), SexeEnum.F), persons);
							if (mothermothermotherfatherfather != null) {
								fathermothermothermotherfatherfather = getPerson(
										findParent(families, mothermothermotherfatherfather.getId(), SexeEnum.M),
										persons);
								if (fathermothermothermotherfatherfather != null) {
									fatherfathermothermothermotherfatherfather = getPerson(
											findParent(families, fathermothermothermotherfatherfather.getId(),
													SexeEnum.M), persons);
									motherfathermothermothermotherfatherfather = getPerson(
											findParent(families, fathermothermothermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermothermothermotherfatherfather = getPerson(
										findParent(families, mothermothermotherfatherfather.getId(), SexeEnum.F),
										persons);
								if (mothermothermothermotherfatherfather != null) {
									fathermothermothermothermotherfatherfather = getPerson(
											findParent(families, mothermothermothermotherfatherfather.getId(),
													SexeEnum.M), persons);
									mothermothermothermothermotherfatherfather = getPerson(
											findParent(families, mothermothermothermotherfatherfather.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
				}
				motherfather = getPerson(findParent(families, father.getId(), SexeEnum.F), persons);
				if (motherfather != null) {
					fathermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.M), persons);
					if (fathermotherfather != null) {
						fatherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.M), persons);
						if (fatherfathermotherfather != null) {
							fatherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.M), persons);
							if (fatherfatherfathermotherfather != null) {
								fatherfatherfatherfathermotherfather = getPerson(
										findParent(families, fatherfatherfathermotherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfatherfathermotherfather != null) {
									fatherfatherfatherfatherfathermotherfather = getPerson(
											findParent(families, fatherfatherfatherfathermotherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfatherfathermotherfather = getPerson(
											findParent(families, fatherfatherfatherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfatherfathermotherfather = getPerson(
										findParent(families, fatherfatherfathermotherfather.getId(), SexeEnum.F),
										persons);
								if (motherfatherfatherfathermotherfather != null) {
									fathermotherfatherfatherfathermotherfather = getPerson(
											findParent(families, motherfatherfatherfathermotherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfatherfathermotherfather = getPerson(
											findParent(families, motherfatherfatherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
							motherfatherfathermotherfather = getPerson(
									findParent(families, fatherfathermotherfather.getId(), SexeEnum.F), persons);
							if (motherfatherfathermotherfather != null) {
								fathermotherfatherfathermotherfather = getPerson(
										findParent(families, motherfatherfathermotherfather.getId(), SexeEnum.M),
										persons);
								if (fathermotherfatherfathermotherfather != null) {
									fatherfathermotherfatherfathermotherfather = getPerson(
											findParent(families, fathermotherfatherfathermotherfather.getId(),
													SexeEnum.M), persons);
									motherfathermotherfatherfathermotherfather = getPerson(
											findParent(families, fathermotherfatherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfatherfathermotherfather = getPerson(
										findParent(families, motherfatherfathermotherfather.getId(), SexeEnum.F),
										persons);
								if (mothermotherfatherfathermotherfather != null) {
									fathermothermotherfatherfathermotherfather = getPerson(
											findParent(families, mothermotherfatherfathermotherfather.getId(),
													SexeEnum.M), persons);
									mothermothermotherfatherfathermotherfather = getPerson(
											findParent(families, mothermotherfatherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						motherfathermotherfather = getPerson(
								findParent(families, fathermotherfather.getId(), SexeEnum.F), persons);
						if (motherfathermotherfather != null) {
							fathermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.M), persons);
							if (fathermotherfathermotherfather != null) {
								fatherfathermotherfathermotherfather = getPerson(
										findParent(families, fathermotherfathermotherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfathermotherfathermotherfather != null) {
									fatherfatherfathermotherfathermotherfather = getPerson(
											findParent(families, fatherfathermotherfathermotherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfathermotherfathermotherfather = getPerson(
											findParent(families, fatherfathermotherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}

								motherfathermotherfathermotherfather = getPerson(
										findParent(families, fathermotherfathermotherfather.getId(), SexeEnum.F),
										persons);
								if (motherfathermotherfathermotherfather != null) {
									fathermotherfathermotherfathermotherfather = getPerson(
											findParent(families, motherfathermotherfathermotherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfathermotherfathermotherfather = getPerson(
											findParent(families, motherfathermotherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermotherfathermotherfather = getPerson(
									findParent(families, motherfathermotherfather.getId(), SexeEnum.F), persons);
							if (mothermotherfathermotherfather != null) {
								fathermothermotherfathermotherfather = getPerson(
										findParent(families, mothermotherfathermotherfather.getId(), SexeEnum.M),
										persons);
								if (fathermothermotherfathermotherfather != null) {
									fatherfathermothermotherfathermotherfather = getPerson(
											findParent(families, fathermothermotherfathermotherfather.getId(),
													SexeEnum.M), persons);
									motherfathermothermotherfathermotherfather = getPerson(
											findParent(families, fathermothermotherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermothermotherfathermotherfather = getPerson(
										findParent(families, mothermotherfathermotherfather.getId(), SexeEnum.F),
										persons);
								if (mothermothermotherfathermotherfather != null) {
									fathermothermothermotherfathermotherfather = getPerson(
											findParent(families, mothermothermotherfathermotherfather.getId(),
													SexeEnum.M), persons);
									mothermothermothermotherfathermotherfather = getPerson(
											findParent(families, mothermothermotherfathermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
					mothermotherfather = getPerson(findParent(families, motherfather.getId(), SexeEnum.F), persons);
					if (mothermotherfather != null) {
						fathermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.M), persons);
						if (fathermothermotherfather != null) {
							fatherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.M), persons);
							if (fatherfathermothermotherfather != null) {
								fatherfatherfathermothermotherfather = getPerson(
										findParent(families, fatherfathermothermotherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfathermothermotherfather != null) {
									fatherfatherfatherfathermothermotherfather = getPerson(
											findParent(families, fatherfatherfathermothermotherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfathermothermotherfather = getPerson(
											findParent(families, fatherfatherfathermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfathermothermotherfather = getPerson(
										findParent(families, fatherfathermothermotherfather.getId(), SexeEnum.F),
										persons);
								if (motherfatherfathermothermotherfather != null) {
									fathermotherfatherfathermothermotherfather = getPerson(
											findParent(families, motherfatherfathermothermotherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfathermothermotherfather = getPerson(
											findParent(families, motherfatherfathermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
							motherfathermothermotherfather = getPerson(
									findParent(families, fathermothermotherfather.getId(), SexeEnum.F), persons);
							if (motherfathermothermotherfather != null) {
								fathermotherfathermothermotherfather = getPerson(
										findParent(families, motherfathermothermotherfather.getId(), SexeEnum.M),
										persons);
								if (fathermotherfathermothermotherfather != null) {
									fatherfathermotherfathermothermotherfather = getPerson(
											findParent(families, fathermotherfathermothermotherfather.getId(),
													SexeEnum.M), persons);
									motherfathermotherfathermothermotherfather = getPerson(
											findParent(families, fathermotherfathermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfathermothermotherfather = getPerson(
										findParent(families, motherfathermothermotherfather.getId(), SexeEnum.F),
										persons);
								if (mothermotherfathermothermotherfather != null) {
									fathermothermotherfathermothermotherfather = getPerson(
											findParent(families, mothermotherfathermothermotherfather.getId(),
													SexeEnum.M), persons);
									mothermothermotherfathermothermotherfather = getPerson(
											findParent(families, mothermotherfathermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						mothermothermotherfather = getPerson(
								findParent(families, mothermotherfather.getId(), SexeEnum.F), persons);
						if (mothermothermotherfather != null) {
							fathermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.M), persons);
							if (fathermothermothermotherfather != null) {
								fatherfathermothermothermotherfather = getPerson(
										findParent(families, fathermothermothermotherfather.getId(), SexeEnum.M),
										persons);
								if (fatherfathermothermothermotherfather != null) {
									fatherfatherfathermothermothermotherfather = getPerson(
											findParent(families, fatherfathermothermothermotherfather.getId(),
													SexeEnum.M), persons);
									motherfatherfathermothermothermotherfather = getPerson(
											findParent(families, fatherfathermothermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
								motherfathermothermothermotherfather = getPerson(
										findParent(families, fathermothermothermotherfather.getId(), SexeEnum.F),
										persons);
								if (motherfathermothermothermotherfather != null) {
									fathermotherfathermothermothermotherfather = getPerson(
											findParent(families, motherfathermothermothermotherfather.getId(),
													SexeEnum.M), persons);
									mothermotherfathermothermothermotherfather = getPerson(
											findParent(families, motherfathermothermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermothermothermotherfather = getPerson(
									findParent(families, mothermothermotherfather.getId(), SexeEnum.F), persons);
							if (mothermothermothermotherfather != null) {
								fathermothermothermothermotherfather = getPerson(
										findParent(families, mothermothermothermotherfather.getId(), SexeEnum.M),
										persons);
								if (fathermothermothermothermotherfather != null) {
									fatherfathermothermothermothermotherfather = getPerson(
											findParent(families, fathermothermothermothermotherfather.getId(),
													SexeEnum.M), persons);
									motherfathermothermothermothermotherfather = getPerson(
											findParent(families, fathermothermothermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
								mothermothermothermothermotherfather = getPerson(
										findParent(families, mothermothermothermotherfather.getId(), SexeEnum.F),
										persons);
								if (mothermothermothermothermotherfather != null) {
									fathermothermothermothermothermotherfather = getPerson(
											findParent(families, mothermothermothermothermotherfather.getId(),
													SexeEnum.M), persons);
									mothermothermothermothermothermotherfather = getPerson(
											findParent(families, mothermothermothermothermotherfather.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
				}
			}
			if (mother != null) {

				fathermother = getPerson(findParent(families, mother.getId(), SexeEnum.M), persons);
				if (fathermother != null) {
					fatherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.M), persons);
					if (fatherfathermother != null) {
						fatherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.M), persons);
						if (fatherfatherfathermother != null) {
							fatherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.M), persons);
							if (fatherfatherfatherfathermother != null) {
								fatherfatherfatherfatherfathermother = getPerson(
										findParent(families, fatherfatherfatherfathermother.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfatherfatherfathermother != null) {
									fatherfatherfatherfatherfatherfathermother = getPerson(
											findParent(families, fatherfatherfatherfatherfathermother.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfatherfatherfathermother = getPerson(
											findParent(families, fatherfatherfatherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfatherfatherfathermother = getPerson(
										findParent(families, fatherfatherfatherfathermother.getId(), SexeEnum.F),
										persons);
								if (motherfatherfatherfatherfathermother != null) {
									fathermotherfatherfatherfatherfathermother = getPerson(
											findParent(families, motherfatherfatherfatherfathermother.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfatherfatherfathermother = getPerson(
											findParent(families, motherfatherfatherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}

							}
							motherfatherfatherfathermother = getPerson(
									findParent(families, fatherfatherfathermother.getId(), SexeEnum.F), persons);
							if (motherfatherfatherfathermother != null) {
								fathermotherfatherfatherfathermother = getPerson(
										findParent(families, motherfatherfatherfathermother.getId(), SexeEnum.M),
										persons);
								if (fathermotherfatherfatherfathermother != null) {
									fatherfathermotherfatherfatherfathermother = getPerson(
											findParent(families, fathermotherfatherfatherfathermother.getId(),
													SexeEnum.M), persons);
									motherfathermotherfatherfatherfathermother = getPerson(
											findParent(families, fathermotherfatherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfatherfatherfathermother = getPerson(
										findParent(families, motherfatherfatherfathermother.getId(), SexeEnum.F),
										persons);
								if (mothermotherfatherfatherfathermother != null) {
									fathermothermotherfatherfatherfathermother = getPerson(
											findParent(families, mothermotherfatherfatherfathermother.getId(),
													SexeEnum.M), persons);
									mothermothermotherfatherfatherfathermother = getPerson(
											findParent(families, mothermotherfatherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						motherfatherfathermother = getPerson(
								findParent(families, fatherfathermother.getId(), SexeEnum.F), persons);
						if (motherfatherfathermother != null) {
							fathermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.M), persons);
							if (fathermotherfatherfathermother != null) {
								fatherfathermotherfatherfathermother = getPerson(
										findParent(families, fathermotherfatherfathermother.getId(), SexeEnum.M),
										persons);
								if (fatherfathermotherfatherfathermother != null) {
									fatherfatherfathermotherfatherfathermother = getPerson(
											findParent(families, fatherfathermotherfatherfathermother.getId(),
													SexeEnum.M), persons);
									motherfatherfathermotherfatherfathermother = getPerson(
											findParent(families, fatherfathermotherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
								motherfathermotherfatherfathermother = getPerson(
										findParent(families, fathermotherfatherfathermother.getId(), SexeEnum.F),
										persons);
								if (motherfathermotherfatherfathermother != null) {
									fathermotherfathermotherfatherfathermother = getPerson(
											findParent(families, motherfathermotherfatherfathermother.getId(),
													SexeEnum.M), persons);
									mothermotherfathermotherfatherfathermother = getPerson(
											findParent(families, motherfathermotherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermotherfatherfathermother = getPerson(
									findParent(families, motherfatherfathermother.getId(), SexeEnum.F), persons);
							if (mothermotherfatherfathermother != null) {
								fathermothermotherfatherfathermother = getPerson(
										findParent(families, mothermotherfatherfathermother.getId(), SexeEnum.M),
										persons);
								if (fathermothermotherfatherfathermother != null) {
									fatherfathermothermotherfatherfathermother = getPerson(
											findParent(families, fathermothermotherfatherfathermother.getId(),
													SexeEnum.M), persons);
									motherfathermothermotherfatherfathermother = getPerson(
											findParent(families, fathermothermotherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
								mothermothermotherfatherfathermother = getPerson(
										findParent(families, mothermotherfatherfathermother.getId(), SexeEnum.F),
										persons);
								if (mothermothermotherfatherfathermother != null) {
									fathermothermothermotherfatherfathermother = getPerson(
											findParent(families, mothermothermotherfatherfathermother.getId(),
													SexeEnum.M), persons);
									mothermothermothermotherfatherfathermother = getPerson(
											findParent(families, mothermothermotherfatherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
					motherfathermother = getPerson(findParent(families, fathermother.getId(), SexeEnum.F), persons);
					if (motherfathermother != null) {
						fathermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.M), persons);
						if (fathermotherfathermother != null) {
							fatherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.M), persons);
							if (fatherfathermotherfathermother != null) {
								fatherfatherfathermotherfathermother = getPerson(
										findParent(families, fatherfathermotherfathermother.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfathermotherfathermother != null) {
									fatherfatherfatherfathermotherfathermother = getPerson(
											findParent(families, fatherfatherfathermotherfathermother.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfathermotherfathermother = getPerson(
											findParent(families, fatherfatherfathermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfathermotherfathermother = getPerson(
										findParent(families, fatherfathermotherfathermother.getId(), SexeEnum.F),
										persons);
								if (motherfatherfathermotherfathermother != null) {
									fathermotherfatherfathermotherfathermother = getPerson(
											findParent(families, motherfatherfathermotherfathermother.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfathermotherfathermother = getPerson(
											findParent(families, motherfatherfathermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
							motherfathermotherfathermother = getPerson(
									findParent(families, fathermotherfathermother.getId(), SexeEnum.F), persons);
							if (motherfathermotherfathermother != null) {
								fathermotherfathermotherfathermother = getPerson(
										findParent(families, motherfathermotherfathermother.getId(), SexeEnum.M),
										persons);
								if (fathermotherfathermotherfathermother != null) {
									fatherfathermotherfathermotherfathermother = getPerson(
											findParent(families, fathermotherfathermotherfathermother.getId(),
													SexeEnum.M), persons);
									motherfathermotherfathermotherfathermother = getPerson(
											findParent(families, fathermotherfathermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfathermotherfathermother = getPerson(
										findParent(families, motherfathermotherfathermother.getId(), SexeEnum.F),
										persons);
								if (mothermotherfathermotherfathermother != null) {
									fathermothermotherfathermotherfathermother = getPerson(
											findParent(families, mothermotherfathermotherfathermother.getId(),
													SexeEnum.M), persons);
									mothermothermotherfathermotherfathermother = getPerson(
											findParent(families, mothermotherfathermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						mothermotherfathermother = getPerson(
								findParent(families, motherfathermother.getId(), SexeEnum.F), persons);
						if (mothermotherfathermother != null) {
							fathermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.M), persons);
							if (fathermothermotherfathermother != null) {
								fatherfathermothermotherfathermother = getPerson(
										findParent(families, fathermothermotherfathermother.getId(), SexeEnum.M),
										persons);
								if (fatherfathermothermotherfathermother != null) {
									fatherfatherfathermothermotherfathermother = getPerson(
											findParent(families, fatherfathermothermotherfathermother.getId(),
													SexeEnum.M), persons);
									motherfatherfathermothermotherfathermother = getPerson(
											findParent(families, fatherfathermothermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
								motherfathermothermotherfathermother = getPerson(
										findParent(families, fathermothermotherfathermother.getId(), SexeEnum.F),
										persons);
								if (motherfathermothermotherfathermother != null) {
									fathermotherfathermothermotherfathermother = getPerson(
											findParent(families, motherfathermothermotherfathermother.getId(),
													SexeEnum.M), persons);
									mothermotherfathermothermotherfathermother = getPerson(
											findParent(families, motherfathermothermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermothermotherfathermother = getPerson(
									findParent(families, mothermotherfathermother.getId(), SexeEnum.F), persons);
							if (mothermothermotherfathermother != null) {
								fathermothermothermotherfathermother = getPerson(
										findParent(families, mothermothermotherfathermother.getId(), SexeEnum.M),
										persons);
								if (fathermothermothermotherfathermother != null) {
									fatherfathermothermothermotherfathermother = getPerson(
											findParent(families, fathermothermothermotherfathermother.getId(),
													SexeEnum.M), persons);
									motherfathermothermothermotherfathermother = getPerson(
											findParent(families, fathermothermothermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
								mothermothermothermotherfathermother = getPerson(
										findParent(families, mothermothermotherfathermother.getId(), SexeEnum.F),
										persons);
								if (mothermothermothermotherfathermother != null) {
									fathermothermothermothermotherfathermother = getPerson(
											findParent(families, mothermothermothermotherfathermother.getId(),
													SexeEnum.M), persons);
									mothermothermothermothermotherfathermother = getPerson(
											findParent(families, mothermothermothermotherfathermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
				}
				mothermother = getPerson(findParent(families, mother.getId(), SexeEnum.F), persons);
				if (mothermother != null) {
					fathermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.M), persons);
					if (fathermothermother != null) {
						fatherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.M), persons);
						if (fatherfathermothermother != null) {
							fatherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.M), persons);
							if (fatherfatherfathermothermother != null) {
								fatherfatherfatherfathermothermother = getPerson(
										findParent(families, fatherfatherfathermothermother.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfatherfathermothermother != null) {
									fatherfatherfatherfatherfathermothermother = getPerson(
											findParent(families, fatherfatherfatherfathermothermother.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfatherfathermothermother = getPerson(
											findParent(families, fatherfatherfatherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfatherfathermothermother = getPerson(
										findParent(families, fatherfatherfathermothermother.getId(), SexeEnum.F),
										persons);
								if (motherfatherfatherfathermothermother != null) {
									fathermotherfatherfatherfathermothermother = getPerson(
											findParent(families, motherfatherfatherfathermothermother.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfatherfathermothermother = getPerson(
											findParent(families, motherfatherfatherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
							motherfatherfathermothermother = getPerson(
									findParent(families, fatherfathermothermother.getId(), SexeEnum.F), persons);
							if (motherfatherfathermothermother != null) {
								fathermotherfatherfathermothermother = getPerson(
										findParent(families, motherfatherfathermothermother.getId(), SexeEnum.M),
										persons);
								if (fathermotherfatherfathermothermother != null) {
									fatherfathermotherfatherfathermothermother = getPerson(
											findParent(families, fathermotherfatherfathermothermother.getId(),
													SexeEnum.M), persons);
									motherfathermotherfatherfathermothermother = getPerson(
											findParent(families, fathermotherfatherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfatherfathermothermother = getPerson(
										findParent(families, motherfatherfathermothermother.getId(), SexeEnum.F),
										persons);
								if (mothermotherfatherfathermothermother != null) {
									fathermothermotherfatherfathermothermother = getPerson(
											findParent(families, mothermotherfatherfathermothermother.getId(),
													SexeEnum.M), persons);
									mothermothermotherfatherfathermothermother = getPerson(
											findParent(families, mothermotherfatherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						motherfathermothermother = getPerson(
								findParent(families, fathermothermother.getId(), SexeEnum.F), persons);
						if (motherfathermothermother != null) {
							fathermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.M), persons);
							if (fathermotherfathermothermother != null) {
								fatherfathermotherfathermothermother = getPerson(
										findParent(families, fathermotherfathermothermother.getId(), SexeEnum.M),
										persons);
								if (fatherfathermotherfathermothermother != null) {
									fatherfatherfathermotherfathermothermother = getPerson(
											findParent(families, fatherfathermotherfathermothermother.getId(),
													SexeEnum.M), persons);
									motherfatherfathermotherfathermothermother = getPerson(
											findParent(families, fatherfathermotherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
								motherfathermotherfathermothermother = getPerson(
										findParent(families, fathermotherfathermothermother.getId(), SexeEnum.F),
										persons);
								if (motherfathermotherfathermothermother != null) {
									fathermotherfathermotherfathermothermother = getPerson(
											findParent(families, motherfathermotherfathermothermother.getId(),
													SexeEnum.M), persons);
									mothermotherfathermotherfathermothermother = getPerson(
											findParent(families, motherfathermotherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermotherfathermothermother = getPerson(
									findParent(families, motherfathermothermother.getId(), SexeEnum.F), persons);
							if (mothermotherfathermothermother != null) {
								fathermothermotherfathermothermother = getPerson(
										findParent(families, mothermotherfathermothermother.getId(), SexeEnum.M),
										persons);
								if (fathermothermotherfathermothermother != null) {
									fatherfathermothermotherfathermothermother = getPerson(
											findParent(families, fathermothermotherfathermothermother.getId(),
													SexeEnum.M), persons);
									motherfathermothermotherfathermothermother = getPerson(
											findParent(families, fathermothermotherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
								mothermothermotherfathermothermother = getPerson(
										findParent(families, mothermotherfathermothermother.getId(), SexeEnum.F),
										persons);
								if (mothermothermotherfathermothermother != null) {
									fathermothermothermotherfathermothermother = getPerson(
											findParent(families, mothermothermotherfathermothermother.getId(),
													SexeEnum.M), persons);
									mothermothermothermotherfathermothermother = getPerson(
											findParent(families, mothermothermotherfathermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
					mothermothermother = getPerson(findParent(families, mothermother.getId(), SexeEnum.F), persons);
					if (mothermothermother != null) {
						fathermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.M), persons);
						if (fathermothermothermother != null) {
							fatherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.M), persons);
							if (fatherfathermothermothermother != null) {
								fatherfatherfathermothermothermother = getPerson(
										findParent(families, fatherfathermothermothermother.getId(), SexeEnum.M),
										persons);
								if (fatherfatherfathermothermothermother != null) {
									fatherfatherfatherfathermothermothermother = getPerson(
											findParent(families, fatherfatherfathermothermothermother.getId(),
													SexeEnum.M), persons);
									motherfatherfatherfathermothermothermother = getPerson(
											findParent(families, fatherfatherfathermothermothermother.getId(),
													SexeEnum.F), persons);
								}
								motherfatherfathermothermothermother = getPerson(
										findParent(families, fatherfathermothermothermother.getId(), SexeEnum.F),
										persons);
								if (motherfatherfathermothermothermother != null) {
									fathermotherfatherfathermothermothermother = getPerson(
											findParent(families, motherfatherfathermothermothermother.getId(),
													SexeEnum.M), persons);
									mothermotherfatherfathermothermothermother = getPerson(
											findParent(families, motherfatherfathermothermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
							motherfathermothermothermother = getPerson(
									findParent(families, fathermothermothermother.getId(), SexeEnum.F), persons);
							if (motherfathermothermothermother != null) {
								fathermotherfathermothermothermother = getPerson(
										findParent(families, motherfathermothermothermother.getId(), SexeEnum.M),
										persons);
								if (fathermotherfathermothermothermother != null) {
									fatherfathermotherfathermothermothermother = getPerson(
											findParent(families, fathermotherfathermothermothermother.getId(),
													SexeEnum.M), persons);
									motherfathermotherfathermothermothermother = getPerson(
											findParent(families, fathermotherfathermothermothermother.getId(),
													SexeEnum.F), persons);
								}
								mothermotherfathermothermothermother = getPerson(
										findParent(families, motherfathermothermothermother.getId(), SexeEnum.F),
										persons);
								if (mothermotherfathermothermothermother != null) {
									fathermothermotherfathermothermothermother = getPerson(
											findParent(families, mothermotherfathermothermothermother.getId(),
													SexeEnum.M), persons);
									mothermothermotherfathermothermothermother = getPerson(
											findParent(families, mothermotherfathermothermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
						mothermothermothermother = getPerson(
								findParent(families, mothermothermother.getId(), SexeEnum.F), persons);
						if (mothermothermothermother != null) {
							fathermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.M), persons);
							if (fathermothermothermothermother != null) {
								fatherfathermothermothermothermother = getPerson(
										findParent(families, fathermothermothermothermother.getId(), SexeEnum.M),
										persons);
								if (fatherfathermothermothermothermother != null) {
									fatherfatherfathermothermothermothermother = getPerson(
											findParent(families, fatherfathermothermothermothermother.getId(),
													SexeEnum.M), persons);
									motherfatherfathermothermothermothermother = getPerson(
											findParent(families, fatherfathermothermothermothermother.getId(),
													SexeEnum.F), persons);
								}
								motherfathermothermothermothermother = getPerson(
										findParent(families, fathermothermothermothermother.getId(), SexeEnum.F),
										persons);
								if (motherfathermothermothermothermother != null) {
									fathermotherfathermothermothermothermother = getPerson(
											findParent(families, motherfathermothermothermothermother.getId(),
													SexeEnum.M), persons);
									mothermotherfathermothermothermothermother = getPerson(
											findParent(families, motherfathermothermothermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
							mothermothermothermothermother = getPerson(
									findParent(families, mothermothermothermother.getId(), SexeEnum.F), persons);
							if (mothermothermothermothermother != null) {
								fathermothermothermothermothermother = getPerson(
										findParent(families, mothermothermothermothermother.getId(), SexeEnum.M),
										persons);
								if (fathermothermothermothermothermother != null) {
									fatherfathermothermothermothermothermother = getPerson(
											findParent(families, fathermothermothermothermothermother.getId(),
													SexeEnum.M), persons);
									motherfathermothermothermothermothermother = getPerson(
											findParent(families, fathermothermothermothermothermother.getId(),
													SexeEnum.F), persons);
								}
								mothermothermothermothermothermother = getPerson(
										findParent(families, mothermothermothermothermother.getId(), SexeEnum.F),
										persons);
								if (mothermothermothermothermothermother != null) {
									fathermothermothermothermothermothermother = getPerson(
											findParent(families, mothermothermothermothermothermother.getId(),
													SexeEnum.M), persons);
									mothermothermothermothermothermothermother = getPerson(
											findParent(families, mothermothermothermothermothermother.getId(),
													SexeEnum.F), persons);
								}
							}
						}
					}
				}
			}
			boolean isFatherFatherFatherFatherFatherFatherGeneration = fatherfatherfatherfatherfatherfatherfather != null
					&& motherfatherfatherfatherfatherfatherfather != null;
			boolean isFatherFatherFatherFatherMotherFatherGeneration = fatherfatherfatherfatherfathermotherfather != null
					&& motherfatherfatherfatherfathermotherfather != null;
			boolean isFatherFatherFatherFatherFatherMotherGeneration = fatherfatherfatherfatherfatherfathermother != null
					&& motherfatherfatherfatherfatherfathermother != null;
			boolean isFatherFatherFatherFatherMotherMotherGeneration = fatherfatherfatherfatherfathermothermother != null
					&& motherfatherfatherfatherfathermothermother != null;
			boolean isFatherFatherFatherMotherFatherFatherGeneration = fatherfatherfatherfathermotherfatherfather != null
					&& motherfatherfatherfathermotherfatherfather != null;
			boolean isFatherFatherFatherMotherMotherFatherGeneration = fatherfatherfatherfathermothermotherfather != null
					&& motherfatherfatherfathermothermotherfather != null;
			boolean isFatherFatherFatherMotherFatherMotherGeneration = fatherfatherfatherfathermotherfathermother != null
					&& motherfatherfatherfathermotherfathermother != null;
			boolean isFatherFatherFatherMotherMotherMotherGeneration = fatherfatherfatherfathermothermothermother != null
					&& motherfatherfatherfathermothermothermother != null;
			boolean isFatherFatherMotherFatherFatherFatherGeneration = fatherfatherfathermotherfatherfatherfather != null
					&& motherfatherfathermotherfatherfatherfather != null;
			boolean isFatherFatherMotherFatherMotherFatherGeneration = fatherfatherfathermotherfathermotherfather != null
					&& motherfatherfathermotherfathermotherfather != null;
			boolean isFatherFatherMotherFatherFatherMotherGeneration = fatherfatherfathermotherfatherfathermother != null
					&& motherfatherfathermotherfatherfathermother != null;
			boolean isFatherFatherMotherFatherMotherMotherGeneration = fatherfatherfathermotherfathermothermother != null
					&& motherfatherfathermotherfathermothermother != null;
			boolean isFatherFatherMotherMotherFatherFatherGeneration = fatherfatherfathermothermotherfatherfather != null
					&& motherfatherfathermothermotherfatherfather != null;
			boolean isFatherFatherMotherMotherMotherFatherGeneration = fatherfatherfathermothermothermotherfather != null
					&& motherfatherfathermothermothermotherfather != null;
			boolean isFatherFatherMotherMotherFatherMotherGeneration = fatherfatherfathermothermotherfathermother != null
					&& motherfatherfathermothermotherfathermother != null;
			boolean isFatherFatherMotherMotherMotherMotherGeneration = fatherfatherfathermothermothermothermother != null
					&& motherfatherfathermothermothermothermother != null;
			boolean isFatherMotherFatherFatherFatherFatherGeneration = fatherfathermotherfatherfatherfatherfather != null
					&& motherfathermotherfatherfatherfatherfather != null;
			boolean isFatherMotherFatherFatherMotherFatherGeneration = fatherfathermotherfatherfathermotherfather != null
					&& motherfathermotherfatherfathermotherfather != null;
			boolean isFatherMotherFatherFatherFatherMotherGeneration = fatherfathermotherfatherfatherfathermother != null
					&& motherfathermotherfatherfatherfathermother != null;
			boolean isFatherMotherFatherFatherMotherMotherGeneration = fatherfathermotherfatherfathermothermother != null
					&& motherfathermotherfatherfathermothermother != null;
			boolean isFatherMotherFatherMotherFatherFatherGeneration = fatherfathermotherfathermotherfatherfather != null
					&& motherfathermotherfathermotherfatherfather != null;
			boolean isFatherMotherFatherMotherMotherFatherGeneration = fatherfathermotherfathermothermotherfather != null
					&& motherfathermotherfathermothermotherfather != null;
			boolean isFatherMotherFatherMotherFatherMotherGeneration = fatherfathermotherfathermotherfathermother != null
					&& motherfathermotherfathermotherfathermother != null;
			boolean isFatherMotherFatherMotherMotherMotherGeneration = fatherfathermotherfathermothermothermother != null
					&& motherfathermotherfathermothermothermother != null;
			boolean isFatherMotherMotherFatherFatherFatherGeneration = fatherfathermothermotherfatherfatherfather != null
					&& motherfathermothermotherfatherfatherfather != null;
			boolean isFatherMotherMotherFatherMotherFatherGeneration = fatherfathermothermotherfathermotherfather != null
					&& motherfathermothermotherfathermotherfather != null;
			boolean isFatherMotherMotherFatherFatherMotherGeneration = fatherfathermothermotherfatherfathermother != null
					&& motherfathermothermotherfatherfathermother != null;
			boolean isFatherMotherMotherFatherMotherMotherGeneration = fatherfathermothermotherfathermothermother != null
					&& motherfathermothermotherfathermothermother != null;
			boolean isFatherMotherMotherMotherFatherFatherGeneration = fatherfathermothermothermotherfatherfather != null
					&& motherfathermothermothermotherfatherfather != null;
			boolean isFatherMotherMotherMotherMotherFatherGeneration = fatherfathermothermothermothermotherfather != null
					&& motherfathermothermothermothermotherfather != null;
			boolean isFatherMotherMotherMotherFatherMotherGeneration = fatherfathermothermothermotherfathermother != null
					&& motherfathermothermothermotherfathermother != null;
			boolean isFatherMotherMotherMotherMotherMotherGeneration = fatherfathermothermothermothermothermother != null
					&& motherfathermothermothermothermothermother != null;

			boolean isMotherFatherFatherFatherFatherFatherGeneration = fathermotherfatherfatherfatherfatherfather != null
					&& mothermotherfatherfatherfatherfatherfather != null;
			boolean isMotherFatherFatherFatherMotherFatherGeneration = fathermotherfatherfatherfathermotherfather != null
					&& mothermotherfatherfatherfathermotherfather != null;
			boolean isMotherFatherFatherFatherFatherMotherGeneration = fathermotherfatherfatherfatherfathermother != null
					&& mothermotherfatherfatherfatherfathermother != null;
			boolean isMotherFatherFatherFatherMotherMotherGeneration = fathermotherfatherfatherfathermothermother != null
					&& mothermotherfatherfatherfathermothermother != null;
			boolean isMotherFatherFatherMotherFatherFatherGeneration = fathermotherfatherfathermotherfatherfather != null
					&& mothermotherfatherfathermotherfatherfather != null;
			boolean isMotherFatherFatherMotherMotherFatherGeneration = fathermotherfatherfathermothermotherfather != null
					&& mothermotherfatherfathermothermotherfather != null;
			boolean isMotherFatherFatherMotherFatherMotherGeneration = fathermotherfatherfathermotherfathermother != null
					&& mothermotherfatherfathermotherfathermother != null;
			boolean isMotherFatherFatherMotherMotherMotherGeneration = fathermotherfatherfathermothermothermother != null
					&& mothermotherfatherfathermothermothermother != null;
			boolean isMotherFatherMotherFatherFatherFatherGeneration = fathermotherfathermotherfatherfatherfather != null
					&& mothermotherfathermotherfatherfatherfather != null;
			boolean isMotherFatherMotherFatherMotherFatherGeneration = fathermotherfathermotherfathermotherfather != null
					&& mothermotherfathermotherfathermotherfather != null;
			boolean isMotherFatherMotherFatherFatherMotherGeneration = fathermotherfathermotherfatherfathermother != null
					&& mothermotherfathermotherfatherfathermother != null;
			boolean isMotherFatherMotherFatherMotherMotherGeneration = fathermotherfathermotherfathermothermother != null
					&& mothermotherfathermotherfathermothermother != null;
			boolean isMotherFatherMotherMotherFatherFatherGeneration = fathermotherfathermothermotherfatherfather != null
					&& mothermotherfathermothermotherfatherfather != null;
			boolean isMotherFatherMotherMotherMotherFatherGeneration = fathermotherfathermothermothermotherfather != null
					&& mothermotherfathermothermothermotherfather != null;
			boolean isMotherFatherMotherMotherFatherMotherGeneration = fathermotherfathermothermotherfathermother != null
					&& mothermotherfathermothermotherfathermother != null;
			boolean isMotherFatherMotherMotherMotherMotherGeneration = fathermotherfathermothermothermothermother != null
					&& mothermotherfathermothermothermothermother != null;
			boolean isMotherMotherFatherFatherFatherFatherGeneration = fathermothermotherfatherfatherfatherfather != null
					&& mothermothermotherfatherfatherfatherfather != null;
			boolean isMotherMotherFatherFatherMotherFatherGeneration = fathermothermotherfatherfathermotherfather != null
					&& mothermothermotherfatherfathermotherfather != null;
			boolean isMotherMotherFatherFatherFatherMotherGeneration = fathermothermotherfatherfatherfathermother != null
					&& mothermothermotherfatherfatherfathermother != null;
			boolean isMotherMotherFatherFatherMotherMotherGeneration = fathermothermotherfatherfathermothermother != null
					&& mothermothermotherfatherfathermothermother != null;
			boolean isMotherMotherFatherMotherFatherFatherGeneration = fathermothermotherfathermotherfatherfather != null
					&& mothermothermotherfathermotherfatherfather != null;
			boolean isMotherMotherFatherMotherMotherFatherGeneration = fathermothermotherfathermothermotherfather != null
					&& mothermothermotherfathermothermotherfather != null;
			boolean isMotherMotherFatherMotherFatherMotherGeneration = fathermothermotherfathermotherfathermother != null
					&& mothermothermotherfathermotherfathermother != null;
			boolean isMotherMotherFatherMotherMotherMotherGeneration = fathermothermotherfathermothermothermother != null
					&& mothermothermotherfathermothermothermother != null;
			boolean isMotherMotherMotherFatherFatherFatherGeneration = fathermothermothermotherfatherfatherfather != null
					&& mothermothermothermotherfatherfatherfather != null;
			boolean isMotherMotherMotherFatherMotherFatherGeneration = fathermothermothermotherfathermotherfather != null
					&& mothermothermothermotherfathermotherfather != null;
			boolean isMotherMotherMotherFatherFatherMotherGeneration = fathermothermothermotherfatherfathermother != null
					&& mothermothermothermotherfatherfathermother != null;
			boolean isMotherMotherMotherFatherMotherMotherGeneration = fathermothermothermotherfathermothermother != null
					&& mothermothermothermotherfathermothermother != null;
			boolean isMotherMotherMotherMotherFatherFatherGeneration = fathermothermothermothermotherfatherfather != null
					&& mothermothermothermothermotherfatherfather != null;
			boolean isMotherMotherMotherMotherMotherFatherGeneration = fathermothermothermothermothermotherfather != null
					&& mothermothermothermothermothermotherfather != null;
			boolean isMotherMotherMotherMotherFatherMotherGeneration = fathermothermothermothermotherfathermother != null
					&& mothermothermothermothermotherfathermother != null;
			boolean isMotherMotherMotherMotherMotherMotherGeneration = fathermothermothermothermothermothermother != null
					&& mothermothermothermothermothermothermother != null;

			try {
				File firstGen = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\genI"
						+ person.getId() + ".html");
				firstGen.createNewFile();
				writer = new BufferedWriter(new FileWriter(firstGen));
				addStart(writer);
				writer.append("<div class=\"section1\">");
				writer.append("<h2>Arbre Généalogique de " + person.getFirstName() + " " + person.getLastName()
						+ "</h2>");
				writer.append("<div id=\"gen1\"><table>");
				if (isFatherFatherFatherFatherFatherFatherGeneration
						|| isFatherFatherFatherFatherMotherFatherGeneration
						|| isFatherFatherFatherFatherFatherMotherGeneration
						|| isFatherFatherFatherFatherMotherMotherGeneration
						|| isFatherFatherFatherMotherFatherFatherGeneration
						|| isFatherFatherFatherMotherMotherFatherGeneration
						|| isFatherFatherFatherMotherFatherMotherGeneration
						|| isFatherFatherFatherMotherMotherMotherGeneration
						|| isFatherFatherMotherFatherFatherFatherGeneration
						|| isFatherFatherMotherFatherMotherFatherGeneration
						|| isFatherFatherMotherFatherFatherMotherGeneration
						|| isFatherFatherMotherFatherMotherMotherGeneration
						|| isFatherFatherMotherMotherFatherFatherGeneration
						|| isFatherFatherMotherMotherMotherFatherGeneration
						|| isFatherFatherMotherMotherFatherMotherGeneration
						|| isFatherFatherMotherMotherMotherMotherGeneration
						|| isFatherMotherFatherFatherFatherFatherGeneration
						|| isFatherMotherFatherFatherMotherFatherGeneration
						|| isFatherMotherFatherFatherFatherMotherGeneration
						|| isFatherMotherFatherFatherMotherMotherGeneration
						|| isFatherMotherFatherMotherFatherFatherGeneration
						|| isFatherMotherFatherMotherMotherFatherGeneration
						|| isFatherMotherFatherMotherFatherMotherGeneration
						|| isFatherMotherFatherMotherMotherMotherGeneration
						|| isFatherMotherMotherFatherFatherFatherGeneration
						|| isFatherMotherMotherFatherMotherFatherGeneration
						|| isFatherMotherMotherFatherFatherMotherGeneration
						|| isFatherMotherMotherFatherMotherMotherGeneration
						|| isFatherMotherMotherMotherFatherFatherGeneration
						|| isFatherMotherMotherMotherMotherFatherGeneration
						|| isFatherMotherMotherMotherFatherMotherGeneration
						|| isFatherMotherMotherMotherMotherMotherGeneration
						|| isMotherFatherFatherFatherFatherFatherGeneration
						|| isMotherFatherFatherFatherMotherFatherGeneration
						|| isMotherFatherFatherFatherFatherMotherGeneration
						|| isMotherFatherFatherFatherMotherMotherGeneration
						|| isMotherFatherFatherMotherFatherFatherGeneration
						|| isMotherFatherFatherMotherMotherFatherGeneration
						|| isMotherFatherFatherMotherFatherMotherGeneration
						|| isMotherFatherFatherMotherMotherMotherGeneration
						|| isMotherFatherMotherFatherFatherFatherGeneration
						|| isMotherFatherMotherFatherMotherFatherGeneration
						|| isMotherFatherMotherFatherFatherMotherGeneration
						|| isMotherFatherMotherFatherMotherMotherGeneration
						|| isMotherFatherMotherMotherFatherFatherGeneration
						|| isMotherFatherMotherMotherMotherFatherGeneration
						|| isMotherFatherMotherMotherFatherMotherGeneration
						|| isMotherFatherMotherMotherMotherMotherGeneration
						|| isMotherMotherFatherFatherFatherFatherGeneration
						|| isMotherMotherFatherFatherMotherFatherGeneration
						|| isMotherMotherFatherFatherFatherMotherGeneration
						|| isMotherMotherFatherFatherMotherMotherGeneration
						|| isMotherMotherFatherMotherFatherFatherGeneration
						|| isMotherMotherFatherMotherMotherFatherGeneration
						|| isMotherMotherFatherMotherFatherMotherGeneration
						|| isMotherMotherFatherMotherMotherMotherGeneration
						|| isMotherMotherMotherFatherFatherFatherGeneration
						|| isMotherMotherMotherFatherMotherFatherGeneration
						|| isMotherMotherMotherFatherFatherMotherGeneration
						|| isMotherMotherMotherFatherMotherMotherGeneration
						|| isMotherMotherMotherMotherFatherFatherGeneration
						|| isMotherMotherMotherMotherMotherFatherGeneration
						|| isMotherMotherMotherMotherFatherMotherGeneration
						|| isMotherMotherMotherMotherMotherMotherGeneration) {

					if (isFatherFatherFatherFatherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherFatherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherFatherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherFatherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherMotherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherMotherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherMotherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherFatherMotherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherFatherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherFatherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherFatherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherFatherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherMotherFatherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherMotherFatherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherMotherMotherFatherGeneration) {
						throw new UnsupportedOperationException();
					}
					if (isFatherFatherMotherMotherMotherMotherGeneration) {
						throw new UnsupportedOperationException();
					}
				}
				writer.append("<br /><br />");
				addDescendance(persons, families, writer, person, "eigthgen.html", "ninthgen.html");
				writer.append("</div>");

				writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

				writer.append("</div>");
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

	private static void addPersonDetail(BufferedWriter writer, Person person, String page1, String page2)
			throws IOException {
		if (person.getFamilyId().isEmpty()) {
			writer.append("<a href=\"" + page1 + "#c" + (person.getId() - 1) + "\" >" + person.getName() + "</a>&nbsp;");
		} else {
			writer.append("<a href=\"" + page2 + "#i" + (person.getId() - 1) + "\" >" + person.getName() + "</a>&nbsp;");
		}
		switch (person.getSexe()) {
		case F:
			writer.append(addFemale() + "<br />");
			break;
		case M:
			writer.append(addMale() + "<br />");
			break;
		}

		if (person.getBirthDate() != null || person.getBirthPlace() != null || person.getDeathDate() != null
				|| person.getDeathPlace() != null) {
			writer.append("<span>" + person.getDates() + "</span>");
		}
	}

	private static void addDescendance(Set<Person> persons, Set<Family> families, BufferedWriter writer, Person person,
			String page1, String page2) throws IOException {
		if (!getFamily(person.getFamilyId(), families).isEmpty()) {
			for(Family family : getFamily(person.getFamilyId(), families)){
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
				if (partner != null && (family.getWeddingDate() != null || family.getWeddingPlace() != null)) {
					writer.append("<div id=\"gen11\"><table><tr><th>Conjoint(s)</th><th>Enfant(s)</th></tr><tr>");
					writer.append("<td><p><a href=\"" + page1 + "#i" + (partner.getId() - 1) + "\" >" + partner.getName()
							+ "</a>&nbsp;");
					switch (partner.getSexe()) {
					case F:
						writer.append(addFemale() + "&nbsp;");
						break;
					case M:
						writer.append(addMale() + "&nbsp;");
						break;
					}
					if (partner.getBirthDate() != null || partner.getBirthPlace() != null || partner.getDeathDate() != null
							|| partner.getDeathPlace() != null) {
						writer.append(partner.getDates());
					}
					writer.append("</p></td>");
					writer.append("<td><br /><p>");
					for (Integer childId : family.getChildren()) {
						Person child = getPerson(childId, persons);
						if (child.getFamilyId().isEmpty()) {
							writer.append("<a href=\"" + page1 + "#c" + (child.getId() - 1) + "\" >" + child.getName()
									+ "</a>&nbsp;");
						} else {
							writer.append("<a href=\"" + page2 + "#i" + (child.getId() - 1) + "\" >" + child.getName()
									+ "</a>&nbsp;");
						}
						switch (child.getSexe()) {
						case F:
							writer.append(addFemale() + "&nbsp;");
							break;
						case M:
							writer.append(addMale() + "&nbsp;");
							break;
						}
						if (child.getBirthDate() != null || child.getBirthPlace() != null || child.getDeathDate() != null
								|| child.getDeathPlace() != null) {
							writer.append(child.getDates());
						}
						writer.append("<br />");
					}
					writer.append("</p>");
					writer.append("<br /></td>");
					writer.append("</tr></table><br /><br /></div>");
				}
			}
		}
	}

}