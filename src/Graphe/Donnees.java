package Graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

public class Donnees {
  private final static String FILE_NAME = "Data/soc-sign-bitcoinalpha.csv";

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
    public void init(List<String> line) throws IOException {
        Donnees donnees=new Donnees();
        donnees.setSource(line.get(0));
        donnees.setTarget(line.get(1));
        donnees.setRating(line.get(2));
        donnees.setTime(Integer.parseInt(line.get(3)));
        tabDonnees.add(donnees);
    }


    public ArrayList<Donnees> getTabDonnees() {
      return tabDonnees;
    }

    public void setTabDonnees(ArrayList<Donnees> tabDonnees) {
      this.tabDonnees = tabDonnees;
    }

    public static void main(String[] args) {
//      Donnees donnees=new Donnees();
//      File f=getResource(FILE_NAME);
//
//      try {
//        //donnees.init(f);
//      } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//         Graph  GG=new DefaultGraph("graph");
//
//      int k=0;
//      Node v=null;
//      Node w=null;
//      ArrayList<String> tab=new ArrayList<String>();
//      for (Donnees string : donnees.getTabDonnees()) {
//        if(!tab.contains(string.source)) {
//          tab.add(string.source);
//        }
//        if(!tab.contains(string.target)) {
//          tab.add(string.target);
//        }

//        if (GG.getNode(string.source)==null) {
//          v = GG.addNode(string.source);
//        }
//        if (GG.getNode(string.target)==null) {
//           w= GG.addNode(string.target);
//        }
//        Edge e = GG.addEdge(v.getId()+"-->"+w.getId(),v.getId(),w.getId(),true);
      }
     // System.out.println(tab.size());
//      for (String string : tab) {
//        System.out.println(string);
//
//      }

    //}
    
}
