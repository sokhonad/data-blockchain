package Graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

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
  public void init(File file) throws IOException {
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    int c=0;
    while (br.read()!=-1) {
      String line[] = br.readLine().split(",");
      Donnees donnees=new Donnees();
      donnees.setSource(line[0]);
      donnees.setTarget(line[1]);
      donnees.setRating(line[2]);
      donnees.setTime(Integer.parseInt(line[3]));
      tabDonnees.add(donnees);
      c++;
    }

    br.close();
    fr.close();
    System.out.println(c);

  }


  public ArrayList<Donnees> getTabDonnees() {
    return tabDonnees;
  }

  public void setTabDonnees(ArrayList<Donnees> tabDonnees) {
    this.tabDonnees = tabDonnees;
  }

}
