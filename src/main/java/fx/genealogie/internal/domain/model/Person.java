package fx.genealogie.internal.domain.model;

import static fx.genealogie.internal.util.ModelUtil.hasFamily;
import static fx.genealogie.internal.util.Util.isBlankOrNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Person implements Comparable<Person> {

	private Integer id;
	private Integer generationId;
	private List<Integer> familyId;
	private String firstName;
	private String lastName;
	private SexeEnum sexe;
	private String birthDate;
	private String birthPlace;
	private String deathDate;
	private String deathPlace;
	private Integer noteId;
	private String religion;
	private String photo;

	public Person(Integer id) {
		this.id = id;
	}

	public int compareTo(Person o) {
		return this.id.compareTo(o.getId());
	}

	public String getFirstName() {
		if(this.firstName == null)
			return "";
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public SexeEnum getSexe() {
		return sexe;
	}

	public void setSexe(SexeEnum sexe) {
		this.sexe = sexe;
	}

	public String getBirthDate() {
		if (birthDate == null)
			return "";
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		if (birthPlace == null)
			birthPlace = "";
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getDeathDate() {
		if (deathDate == null)
			return "";
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getDeathPlace() {
		if (deathPlace == null)
			deathPlace = "";
		return deathPlace;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return this.getLastName() + ", " + this.getFirstName();
	}

	public String getDates() {
		if (isBlankOrNull(this.getBirthDate()) && isBlankOrNull(this.getBirthPlace())
				&& isBlankOrNull(this.getDeathDate()) && isBlankOrNull(this.getDeathPlace()))
			return "";
		return "(<span class=\"ref\" >&deg;&nbsp;" + this.getBirthDate() + " <i>" + this.getBirthPlace()
				+ "</i>&nbsp; - "
				+ ((!isBlankOrNull(this.getDeathDate()) || !isBlankOrNull(this.getDeathPlace())) ? "&#134;" : "")
				+ "&nbsp;" + this.getDeathDate() + " <i>" + this.getDeathPlace() + "</i></span>)";
	}

	public static String addMale() {
		return "<img src=\"male1.gif\" alt=\"Garcon\" />";
	}

	public static String addFemale() {
		return "<img src=\"female.gif\" alt=\"Fille\" />";
	}

	public void addPhoto(BufferedWriter writer) throws IOException {
		writer.append("<a href='" + getPhoto() + "'>");
		writer.append("<img src='" + getPhoto() + "' class=\"photo\" title=\"" + getName() + "\" alt=\""
				+ getLastName() + "," + getFirstName() + "\" />");
		writer.append("</a>");
	}

	public List<Integer> getFamilyId() {
		if (familyId == null)
			familyId = new ArrayList<Integer>();
		return familyId;
	}

	public void setFamilyId(List<Integer> familyId) {
		this.familyId = familyId;
	}

	public Integer getGenerationId() {
		return generationId;
	}

	public void setGenerationId(Integer generationId) {
		this.generationId = generationId;
	}

	public String getRealLastName() {
		if (this.lastName.startsWith("de ")) {
			return this.lastName.substring(3);
		} else if (this.lastName.startsWith("d'")) {
			return this.lastName.substring(2);
		} else {
			return this.lastName.replace("é", "e").replace("ç", "c");
		}
	}

	public String getRealFirstName() {
		if(this.firstName == null)
			return this.firstName;
		return this.firstName.replace("é", "e").replace("ç", "c");
	}

	public String getGenerationPages(Set<Family> families) {
		if (this.generationId == null)
			return null;
		switch (this.generationId) {
		case 1:
				return "firstgen.html"+ "#i" + (this.id - 1);
		case 2:
			if(hasFamily(this.id, families))
				return "secondgen.html"+ "#i" + (this.id - 1);
			else
				return "firstgen.html"+ "#c" + (this.id - 1);
		case 3:
			if(hasFamily(this.id, families))
				return "thirdgen.html"+ "#i" + (this.id - 1);
			else
				return "secondgen.html"+ "#c" + (this.id - 1);
		case 4:
			if(hasFamily(this.id, families))
				return "fourthgen.html"+ "#i" + (this.id - 1);
			else
				return "thirdgen.html"+ "#c" + (this.id - 1);
		case 5:
			if(hasFamily(this.id, families))
				return "fifth.html"+ "#i" + (this.id - 1);
			else
				return "fourthgen.html"+ "#c" + (this.id - 1);
		case 6:
			if(hasFamily(this.id, families))
				return "sixthgen.html"+ "#i" + (this.id - 1);
			else
				return "fifthgen.html"+ "#c" + (this.id - 1);
		case 7:
			if(hasFamily(this.id, families))
				return "seventhgen.html"+ "#i" + (this.id - 1);
			else
				return "sixthgen.html"+ "#c" + (this.id - 1);
		case 8:
			if(hasFamily(this.id, families))
				return "eigthgen.html"+ "#i" + (this.id - 1);
			else
				return "seventhgen.html"+ "#c" + (this.id - 1);
		case 9:
			if(hasFamily(this.id, families))
				return "ninthgen.html"+ "#i" + (this.id - 1);
			else
				return "eigthgen.html"+ "#c" + (this.id - 1);
		default:
			return null;
		}

	}
}
