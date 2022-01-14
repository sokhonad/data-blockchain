package Graphe;

import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;


public class gerationGraphe {

  private final static String FILE_NAME = "Data/soc-sign-bitcoinalpha.csv";

  private  static Graph graphe;
  private static Donnees donnees;

  public   gerationGraphe() {}


  //
  public static void visualisationStatique() throws Exception  {
    graphe=new DefaultGraph("graph");
    donnees=new Donnees();
    File f=Donnees.getResource(FILE_NAME);

    try {
      donnees.init(f);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for (int i = 0; i < donnees.getTabDonnees().size(); i++) {
      if(graphe.getNode(donnees.getTabDonnees().get(i).getSource())==null) {
        graphe.addNode(donnees.getTabDonnees().get(i).getSource());

      }
      if(graphe.getNode(donnees.getTabDonnees().get(i).getTarget())==null) {
        graphe.addNode(donnees.getTabDonnees().get(i).getTarget());

      }
    }
    for (int i = 0; i < donnees.getTabDonnees().size(); i++) {
      if (graphe.getEdge(donnees.getTabDonnees().get(i).getSource()+"-->"+donnees.getTabDonnees().get(i).getTarget())==null) {
        graphe.addEdge(donnees.getTabDonnees().get(i).getSource()+"-->"+donnees.getTabDonnees().get(i).getTarget(),donnees.getTabDonnees().get(i).getSource(),donnees.getTabDonnees().get(i).getTarget(),true);
      }
    }
    graphe.display();

  }


  public static void main(String[] args) throws Exception   {
    System.setProperty("org.graphstream.ui", "swing");
    visualisationStatique();

    System.out.println("nombre noeud**** "+graphe.getNodeCount());;
    System.out.println("Nombre de noeud++:"+graphe.getNodeCount());
    System.out.println("Nombre de arret:"+graphe.getEdgeCount());
  }
}
