package fx.genealogie.main;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;
import fx.genealogie.service.IGenealogyService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext.xml");
		IGenealogyService service = (IGenealogyService) applicationContext
				.getBean("genealogyService");
		service.initialize();
		Set<Person> persons = new HashSet<Person>();
		Set<Note> notes = new HashSet<Note>();
		Set<Family> families = new HashSet<Family>();
		service.parseFile(IGenealogyService.class.getClassLoader()
				.getResourceAsStream("input/igndx.ged"), persons, notes,
				families);
		service.createGenerationPages(persons, families, notes);
//		service.createNomPages(persons, families, 40);
		service.createTreePages(persons, families);
	}
}
