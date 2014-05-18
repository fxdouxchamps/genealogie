package fx.genealogie.internal.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Family implements Comparable<Family> {

    private Integer id;
    private Integer husband;
    private Integer wife;
    private String weddingDate;
    private String weddingPlace;

    private List<Integer> children;

    public Family(Integer id) {
        this.id = id;
    }

    public int compareTo(Family o) {
        return this.id.compareTo(o.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHusband() {
        return husband;
    }

    public void setHusband(Integer husband) {
        this.husband = husband;
    }

    public Integer getWife() {
        return wife;
    }

    public void setWife(Integer wife) {
        this.wife = wife;
    }

    public List<Integer> getChildren() {
        if (children == null)
            children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    public String getWeddingDate() {
        if (weddingDate == null || weddingDate.contains("Inconnue"))
            weddingDate = "";
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getWeddingPlace() {
        if (weddingPlace == null)
            return "";
        return weddingPlace;
    }

    public void setWeddingPlace(String weddingPlace) {
        this.weddingPlace = weddingPlace;
    }

}
