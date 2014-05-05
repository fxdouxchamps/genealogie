package fx.genealogie.internal.serviceimpl;

import static fx.genealogie.internal.util.GenPagesUtil.createFirstGenPages;
import static fx.genealogie.internal.util.GenPagesUtil.createNextGenPages;
import static fx.genealogie.internal.util.ModelUtil.getFamily;
import static fx.genealogie.internal.util.ModelUtil.getPerson;
import static fx.genealogie.internal.util.NamePagesUtil.createNamePages;
import static fx.genealogie.internal.util.NamePagesUtil.filterPersons;
import static fx.genealogie.internal.util.NamePagesUtil.getAlphabet;
import static fx.genealogie.internal.util.ParserUtil.parse;
import static fx.genealogie.internal.util.Util.addEnd;
import static fx.genealogie.internal.util.Util.addStart;
import static fx.genealogie.internal.util.Util.copy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;
import fx.genealogie.internal.util.TreeUtil;
import fx.genealogie.service.IGenealogyService;

public class GenealogyServiceImpl implements IGenealogyService {

	public void parseFile(InputStream input, Set<Person> persons, Set<Note> notes, Set<Family> families) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		try {
			parse(persons, notes, families, reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		File output = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\");
		if (!output.exists()) {
			output.mkdir();
		} else {
			for (File f : output.listFiles()) {
				if (f.isDirectory()) {
					for (File f2 : f.listFiles()) {
						f2.delete();
					}
				}
				f.delete();
			}
		}

		File constants = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\constants\\");
		for (File f : constants.listFiles()) {
			if (f.isDirectory()) {
				for (File f2 : f.listFiles()) {
					File newDir = new File(output + "\\" + f.getName());
					if (!newDir.exists())
						newDir.mkdir();
					File newFile = new File(output + "\\" + f.getName() + "\\" + f2.getName());
					try {
						copy(f2, newFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				File newFile = new File(output + "\\" + f.getName());
				try {
					copy(f, newFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void createGenerationPages(Set<Person> persons, Set<Family> families, Set<Note> notes) {
		Person person = getPerson(1, persons);
		person.setGenerationId(1);
		createFirstGenPages(Arrays.asList(person), persons, families, notes);

		Family family = getFamily(Arrays.asList(person.getId()), families).get(0);
		if (family.getHusband() != null) {
			getPerson(family.getHusband(), persons).setGenerationId(1);
		}
		if (family.getWife() != null) {
			getPerson(family.getWife(), persons).setGenerationId(1);
		}

		List<Person> secondGen = new ArrayList<Person>();
		for (Integer childId : family.getChildren()) {
			secondGen.add(getPerson(childId, persons));
			getPerson(childId, persons).setGenerationId(2);
		}
		createNextGenPages("Seconde", "firstgen.html", "secondgen.html", "thirdgen.html", secondGen, persons, families,
				notes);
		List<Family> secondFam = new ArrayList<Family>();
		for (Person p : secondGen) {
			secondFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> thirdGen = new ArrayList<Person>();
		for (Family f : secondFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(2);
			}
			if (family.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(2);
			}
			for (Integer childId : f.getChildren()) {
				thirdGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(3);
			}
		}
		createNextGenPages("Troisième", "secondgen.html", "thirdgen.html", "fourthgen.html", thirdGen, persons,
				families, notes);

		List<Family> thirdFam = new ArrayList<Family>();
		for (Person p : thirdGen) {
			thirdFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> fourthGen = new ArrayList<Person>();
		for (Family f : thirdFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(3);
			}
			if (f.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(3);
			}
			for (Integer childId : f.getChildren()) {
				fourthGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(4);
			}
		}
		createNextGenPages("Quatrième", "thirdgen.html", "fourthgen.html", "fifthgen.html", fourthGen, persons,
				families, notes);

		List<Family> fourthFam = new ArrayList<Family>();
		for (Person p : fourthGen) {
			fourthFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> fifthGen = new ArrayList<Person>();
		for (Family f : fourthFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(4);
			}
			if (f.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(4);
			}
			for (Integer childId : f.getChildren()) {
				fifthGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(5);
			}
		}
		createNextGenPages("Cinquième", "fourthgen.html", "fifthgen.html", "sixthgen.html", fifthGen, persons,
				families, notes);

		List<Family> fifthFam = new ArrayList<Family>();
		for (Person p : fifthGen) {
			fifthFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> sixthGen = new ArrayList<Person>();
		for (Family f : fifthFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(5);
			}
			if (f.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(5);
			}
			for (Integer childId : f.getChildren()) {
				sixthGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(6);
			}
		}
		createNextGenPages("Sixième", "fifthgen.html", "sixthgen.html", "seventhgen.html", sixthGen, persons, families,
				notes);

		List<Family> sixthFam = new ArrayList<Family>();
		for (Person p : sixthGen) {
			sixthFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> seventhGen = new ArrayList<Person>();
		for (Family f : sixthFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(6);
			}
			if (f.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(6);
			}
			for (Integer childId : f.getChildren()) {
				seventhGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(7);
			}
		}
		createNextGenPages("Septième", "sixthgen.html", "seventhgen.html", "eigthgen.html", seventhGen, persons,
				families, notes);

		List<Family> seventhFam = new ArrayList<Family>();
		for (Person p : seventhGen) {
			seventhFam.addAll(getFamily(p.getFamilyId(), families));
		}
		List<Person> eigthGen = new ArrayList<Person>();
		for (Family f : seventhFam) {
			if (f.getHusband() != null) {
				getPerson(f.getHusband(), persons).setGenerationId(7);
			}
			if (f.getWife() != null) {
				getPerson(f.getWife(), persons).setGenerationId(7);
			}
			for (Integer childId : f.getChildren()) {
				eigthGen.add(getPerson(childId, persons));
				getPerson(childId, persons).setGenerationId(8);
			}
		}
		createNextGenPages("Huitième", "seventhgen.html", "eigthgen.html", "ninthgen.html", eigthGen, persons,
				families, notes);
	}

	public void createNomPages(Set<Person> persons, Set<Family> families, Integer numberOfPerson) {
		createNamePages(persons, families, numberOfPerson);
		BufferedWriter writer = null;
		try {
			File page = new File("d:\\workspace\\fx.genealogie\\src\\main\\resources\\output\\patronymes.html");
			page.createNewFile();
			writer = new BufferedWriter(new FileWriter(page));
			addStart(writer);
			writer.append("<div class=\"section1\"><h2>Descendance d'Ignace Douxchamps : Liste des patronymes</h2>");
			for (String letter : getAlphabet()) {
				writer.append("<p class=\"pat1\">" + letter.toUpperCase() + "</p>");
				List<Person> limitedPersons = filterPersons(persons, letter);
				Map<String, Integer> map = new LinkedHashMap<String, Integer>();
				for (Person p : limitedPersons) {
					if (map.get(p.getLastName()) == null) {
						map.put(p.getLastName(), 1);
					} else {
						map.put(p.getLastName(), map.get(p.getLastName()) + 1);
					}
				}
				writer.append("<p class=\"pat2\">");
				for (String key : map.keySet()) {
					writer.append("<a href=\"nom" + letter.toLowerCase() + "n0.html#"
							+ key.toLowerCase().replace(" ", "_") + "\">" + key + "</a>" + "(" + map.get(key)
							+ ")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
				}
				writer.append("</p>");
				// writer.append("<a href=\"noman0.html#ange\">Ange</a>(1)&nbsp;&nbsp;&nbsp;<a href="noman0.html#anonyme">Anonyme</a>(2)&nbsp;&nbsp;&nbsp;<a href="noman0.html#de
				// aquino">de_Aquino</a>(1)&nbsp;&nbsp;&nbsp;<a href="noman0.html#arendt">Arendt</a>(3)&nbsp;&nbsp;&nbsp;<a href="noman0.html#auvray">Auvray</a>(3)&nbsp;&nbsp;&nbsp;</p>");
			}
			writer.append("<div id=\"section11\"> <img src=\"separation.gif\" alt=\"ligne\" /><p>Webmaster : F-X Douxchamps</p> <br /> <p>Ce site Web a été créé le 19 Novembre 2007 avec&nbsp;<a href=\"http://software.visicommedia.com/fr/products/webexpert/\">Webexpert 6.6</a></p> <img src=\"separation.gif\" alt=\"ligne\" /> <br /> <p>© Copyright 2007</p> <br /> </div>");

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

	public void createTreePages(Set<Person> persons, Set<Family> families) {
		List<Person> generations = new ArrayList<Person>();
		for (Person p : persons) {
			if (new Integer(1).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createFirstGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(2).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createSecondGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(3).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createThirdGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(4).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createFourthGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(5).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createFifthGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(6).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createSixthGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(7).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createSeventhGenPages(generations, persons, families);
		generations.clear();
		for (Person p : persons) {
			if (new Integer(8).equals(p.getGenerationId())) {
				generations.add(p);
			}
		}
		TreeUtil.createEigthGenPages(generations, persons, families);
	}

}
