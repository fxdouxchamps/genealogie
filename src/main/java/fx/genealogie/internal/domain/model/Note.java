package fx.genealogie.internal.domain.model;

public class Note implements Comparable<Note> {

    private Integer id;
    private String text;

    public Note(Integer id) {
        this.id = id;
    }

    public int compareTo(Note o) {
        return this.id.compareTo(o.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
