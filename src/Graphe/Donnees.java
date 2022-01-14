package Graphe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Donnees {

  private ArrayList<Donnees> tabDonnees;
  private String source;
  private String target; 
  private String rating;
  private Integer time;


  public Donnees() {
    tabDonnees =new  ArrayList<Donnees>();
    source=null;
    target=null;
    rating=null;
    time=0;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public static String getResourcePath(String fileName) {
    final File f = new File("");
    final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
    return dossierPath;
  }

  public static File getResource(String fileName) {
    final String completeFileName = getResourcePath(fileName);
    File file = new File(completeFileName);
    return file;
  }
  public void init(String source,String target,String rating,String time) throws IOException {
    Donnees donnees=new Donnees();
    donnees.setSource(source);
    donnees.setTarget(target);
    donnees.setRating(rating);
    donnees.setTime(Integer.parseInt(time));
    tabDonnees.add(donnees);
  }


  public ArrayList<Donnees> getTabDonnees() {
    return tabDonnees;
  }

  public void setTabDonnees(ArrayList<Donnees> tabDonnees) {
    this.tabDonnees = tabDonnees;
  }


}
