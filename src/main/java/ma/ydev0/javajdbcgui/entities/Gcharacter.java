package ma.ydev0.javajdbcgui.entities;

public class Gcharacter  {
  private int id;
  private String name;
  private int health;
  private float damage;
  private Gclass gClass;

  public Gcharacter() {

  }

  public Gcharacter(Integer id, String name, int health, float damage, Gclass gClass) {
    this.id = id != null ? id : -1;
    this.name = name;
    this.health = health;
    this.damage = damage;
    this.gClass = gClass;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public float getDamage() {
    return damage;
  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public Gclass getgClass() {
    return gClass;
  }

  public void setgClass(Gclass gClass) {
    this.gClass = gClass;
  }

  @Override
  public String toString() {
    return "Character{" +
        "id = " + id +
        ", name = '" + name + '\'' +
        ", health = " + health +
        ", damage = " + damage +
        ", Class = " + gClass.getLabel() +
        '}';
  }
}
