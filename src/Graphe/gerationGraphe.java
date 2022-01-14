package Graphe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;


public class gerationGraphe {
  
  private final static String FILE_NAME_CSV = "Data/soc-sign-bitcoinalpha.csv";
  private final static String FILE_NAME_TXT = "Data/soc-sign-bitcoinalpha.txt";
  
 private  static Graph graphe;
 private static List<String> data;
 
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
    System.out.println("nombre noeud**** "+graphe.getNodeCount());;

    System.out.println("Nombre de noeud++:"+graphe.getNodeCount());
    System.out.println("Nombre de arret:"+graphe.getEdgeCount());
    System.setProperty("org.graphstream.ui", "swing");
    graphe.display();

  }
  
  
  @SuppressWarnings("unused")
  private static void testTransformationCsvEnTxt() throws Exception {
    File f=getResource(FILE_NAME_CSV);
     data = readFile(f);
    transformationCsvEnTx(data,FILE_NAME_TXT);

  }

  public static void main(String[] args) throws Exception   {
    testTransformationCsvEnTxt();

    visualisationStatique(data, graphe);


  }
}
