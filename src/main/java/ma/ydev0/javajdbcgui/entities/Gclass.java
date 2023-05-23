package ma.ydev0.javajdbcgui.entities;

public class Gclass {
  private Integer id;
  private String label;
  private String description;

  public Gclass() {

  }

  public Gclass(Integer id, String label, String description) {
    this.id = id != null ? id : -1;
    this.label = label;
    this.description = description;
  }
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Class{" +
        "id = " + id +
        ", label = '" + label + '\'' +
        ", description = '" + description + '\'' +
        '}';
  }
}
