package Graphe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;


public class gerationGraphe {

  private final static String FILE_NAME_CSV = "Data/soc-sign-bitcoinalpha.csv";
  private final static String FILE_NAME_TXT = "Data/soc-sign-bitcoinalpha.txt";

  private  static Graph graphe;
  private static List<String> data;
  private static Donnees donnees;

  public   gerationGraphe(Graph graph) {}

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


  public static List<String> readFile(File file) throws Exception {

    List<String> result = new ArrayList<String>();

    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);

    for (String line = br.readLine(); line != null; line = br.readLine()) {
      result.add(line);
    }

    br.close();
    fr.close();

    return result;
  }
  public static void transformationCsvEnTx(List<String> tab,String filename) throws Exception  {
    String filepath = System.getProperty("user.dir") + File.separator + filename;
    FileWriter fileWriter = new FileWriter(filepath);
    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    for (int i = 0; i < tab.size(); i++) {
      String b=tab.get(i);
      String[] ligne = b.split(",");          
      for (int j = 0; j < ligne.length; j++) {
        if(j<=1 || j==3) {
          StringBuilder line = new StringBuilder();
          line.append(ligne[j]).append(" ");
          bufferedWriter.write(line + "");
        }
      }
      bufferedWriter.newLine();

    }
    bufferedWriter.close();

  }

  public static void visualisationStatique(List<String> tab,Graph graphe) throws Exception  {
    graphe=new DefaultGraph("graphe");
    for (int i = 0; i < tab.size(); i++) {
      String b=tab.get(i);
      String[] ligne = b.split(",");
      if(graphe.getNode(ligne[1])==null) {
        graphe.addNode(ligne[1]);

      }
      if(graphe.getNode(ligne[0])==null) {
        graphe.addNode(ligne[0]);

      }
    }

    for (int i = 0; i < tab.size(); i++) {
      String b=tab.get(i);
      String[] ligne = b.split(",");
      if(graphe.getEdge(ligne[0]+"-->"+ligne[1])==null) {
        graphe.addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
      }
    }
    System.out.println("Nombre de noeud:"+graphe.getNodeCount());
    System.out.println("Nombre de arret:"+graphe.getEdgeCount());
    System.setProperty("org.graphstream.ui", "swing");
    //graphe.display();

  }

  public static void visualisationDynamique(List<String> tab,Graph graphe,int taille) throws Exception  {
    int compter=0;
    //ajout des noeuds
    donnees=new Donnees();
    graphe=new DefaultGraph("graphe");
    for (int i = 0; i < tab.size(); i++) {
      String b=tab.get(i);
      String[] ligne = b.split(",");
      if(graphe.getNode(ligne[1])==null) {
        graphe.addNode(ligne[1]);

      }
      if(graphe.getNode(ligne[0])==null) {
        graphe.addNode(ligne[0]);

      }
    }
    //ajout des sommets
    for (int i = 0; i < tab.size(); i++) {
      String b=tab.get(i);
      String[] ligne = b.split(",");
      if(graphe.getEdge(ligne[0]+"-->"+ligne[1])==null) {
        graphe.addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
        donnees.init(ligne[0], ligne[1], ligne[2], ligne[3]);
      }
    }
    System.out.println(donnees.getTabDonnees().size());;
    Graph graphe1=new DefaultGraph("graphe1");
    System.setProperty("org.graphstream.ui", "swing");

    graphe1.display();
    int debut=1289192400;
    int fin=1453438800;
    while(debut<=fin) {
      for (int i = 0; i < donnees.getTabDonnees().size(); i++) {
        if (donnees.getTabDonnees().get(i).getTime()>=debut && donnees.getTabDonnees().get(i).getTime()<=debut+taille ) {//intervalle temps
          if(graphe1.getNode(donnees.getTabDonnees().get(i).getSource())==null) {
            graphe1.addNode(donnees.getTabDonnees().get(i).getSource());

          }
          if(graphe1.getNode(donnees.getTabDonnees().get(i).getTarget())==null) {
            graphe1.addNode(donnees.getTabDonnees().get(i).getTarget());

          }
          //ajout des sommets
          if(graphe1.getEdge(donnees.getTabDonnees().get(i).getSource()+"-->"+donnees.getTabDonnees().get(i).getTarget())==null) {
            graphe1.addEdge(donnees.getTabDonnees().get(i).getSource()+"-->"+donnees.getTabDonnees().get(i).getTarget(),donnees.getTabDonnees().get(i).getSource(),donnees.getTabDonnees().get(i).getTarget(),true);
          }
        }



      }


      Thread.sleep( 1500);
      System.out.println("Nombre de noeud:"+graphe1.getNodeCount());
      compter+=graphe1.getNodeCount();
      graphe1.clear();
      debut+=taille;

    }
    System.out.println("compter:"+compter);

  }






  @SuppressWarnings("unused")
  private static void testTransformationCsvEnTxt() throws Exception {
    File f=getResource(FILE_NAME_CSV);
    data = readFile(f);
    transformationCsvEnTx(data,FILE_NAME_TXT);

  }

  public static void main(String[] args) throws Exception   {
    testTransformationCsvEnTxt();
    //visualisationStatique(data, graphe);
    visualisationDynamique(data, graphe,500000);


  }
}
