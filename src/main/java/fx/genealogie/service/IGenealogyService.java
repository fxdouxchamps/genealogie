package fx.genealogie.service;

import java.io.InputStream;
import java.util.Set;

import fx.genealogie.internal.domain.model.Family;
import fx.genealogie.internal.domain.model.Note;
import fx.genealogie.internal.domain.model.Person;

public interface IGenealogyService {

	void parseFile(InputStream input, Set<Person> persons, Set<Note> notes,
			Set<Family> families);

	void initialize();

	void createGenerationPages(Set<Person> persons, Set<Family> families,
			Set<Note> notes);

	void createTreePages(Set<Person> persons, Set<Family> families);

	void createNomPages(Set<Person> persons, Set<Family> families, Integer numberOfPerson);
}
