package Graphe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;


public class GenerationGraphe {

	private final static String FILE_NAME_CSV = "Data/soc-sign-bitcoinalpha.csv";
	private final static String FILE_NAME_TXT = "Data/soc-sign-bitcoinalpha.txt";
	private final static String FILE_NAME_CSV_MSG = "Data/data.csv";
	private final static String FILE_NAME_TXT_MSG = "Data/data.txt";
	private final static String FILE_NALE_EMAIL_EU_CSV = "Data/email-Eu-core-temporal.csv";
	private final static String FILE_NALE_EMAIL_EU_TXT = "Data/email-Eu-core-temporal.txt";
	private  static String separateur;
	private static int id;



	private Graph graphe;
	private static List<String> data;
	private static Donnees donnees;
	private static List<Double> tableauRatio=new ArrayList<Double>() ;
	private static List<Double> taNbSommetCourant=new ArrayList<Double>() ;
	private static List<Double> tabNbArretCourant=new ArrayList<Double>() ;


	private static List<Double> distributionDegre=new ArrayList<Double>() ;


	public   GenerationGraphe() {
		graphe=new DefaultGraph("graphe");

	}
	public Graph getGraphe() {
		return graphe;
	}



	public  void visualisationStatique() throws Exception  {
		for (int i = 0; i < data.size(); i++) {
			String b=data.get(i);
			String[] ligne = b.split(separateur);
			if(this.getGraphe().getNode(ligne[1])==null) {
				graphe.addNode(ligne[1]);

			}
			if(this.getGraphe().getNode(ligne[0])==null) {
				this.getGraphe().addNode(ligne[0]);

			}
		}

		for (int i = 0; i < data.size(); i++) {
			String b=data.get(i);
			String[] ligne = b.split(separateur);
			if(this.getGraphe().getEdge(ligne[0]+"-->"+ligne[1])==null) {
				this.getGraphe().addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
			}
		}
		calculMetrique(this.getGraphe());
		System.setProperty("org.graphstream.ui", "swing");
		this.getGraphe().display();

	}

	public  void visualisationDynamique(int taille,int devision) throws Exception  {
		//les graphes temporaire
		Graph grapheTemp1=null;
		Graph grapheTemp2=null;
		//demarrager de comparaison de grapheTemp1 et grapheTemp2
		int demarrer=-1;
		int compter=0;
		//ajout des noeuds
		donnees=new Donnees();
		for (int i = 0; i < data.size(); i++) {
			String b=data.get(i);
			String[] ligne = b.split(separateur);
			if(this.getGraphe().getNode(ligne[1])==null) {
				this.getGraphe().addNode(ligne[1]);

			}
			if(this.getGraphe().getNode(ligne[0])==null) {
				this.getGraphe().addNode(ligne[0]);

			}
		}
		//ajout des sommets
		for (int i = 0; i < data.size(); i++) {
			String b=data.get(i);
			String[] ligne = b.split(separateur);
			if(this.getGraphe().getEdge(ligne[0]+"-->"+ligne[1])==null) {
				this.getGraphe().addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
				if (separateur==",") {
					donnees.init(ligne[0], ligne[1], ligne[2], ligne[3]);

				}
				if (separateur==" ") {
					donnees.init(ligne[0], ligne[1], ligne[2], ligne[2]);

				}
			}
		}
		System.out.println(donnees.getTabDonnees().size());;
		Graph graphe1=new DefaultGraph("graphe1");
		System.setProperty("org.graphstream.ui", "swing");
		// affichage graphe dynamique
		int debut=0;
		int fin=0;
		//graphe1.display();
		if (separateur==","  && id==1) {
			debut=1289192400;
			fin=1453438800;
		}else if(separateur==" "  && id==2) {
			debut=1082040961;
			fin=1098777142;
		} else if(separateur==" " && id==3) {
			debut=0;
			fin=69379200;
		}
		while(debut<=fin ) {
			System.out.println("************variable***************:"+debut/3600);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@variable@@@@@@@@@@@@:"+(debut+taille)/3600);
			for (int i = 0; i < donnees.getTabDonnees().size(); i++) {
				if (donnees.getTabDonnees().get(i).getTime()>=debut/devision && donnees.getTabDonnees().get(i).getTime()<=debut+taille ) {//intervalle temps
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
			Thread.sleep( 1);
			//affichage nombre des noeuds graphe dynamique
			System.out.println("Nombre de noeud:"+graphe1.getNodeCount());
			taNbSommetCourant.add((double) graphe1.getNodeCount());
			tabNbArretCourant.add((double) graphe1.getEdgeCount());
			System.out.println("Nombre de arret:******************"+graphe1.getEdgeCount());

			compter+=graphe1.getNodeCount();
			//chargement graphe des graphe temporaire
			if (demarrer==0) {
				grapheTemp1=copie(grapheTemp2);
				grapheTemp2=copie(graphe1);
				tableauRatio.add(ratio(grapheTemp1, grapheTemp2));

			}
			grapheTemp2=copie(graphe1);
			demarrer=0;
			graphe1.clear();

			debut+=(taille/devision);
		}
		//		System.out.println("compter:"+compter);
	}

	//formule ratio: ((V1 - V0) + (V0 - V1)) / ((V1 union V0) - (V1 intersection V0))
	private static double ratio(Graph g1,Graph g2) {
		double resultat;
		double V1MoinsV0,V0MoinsV1;
		double V1UnionV0=0;
		double V1IntersectionV0=0;
		//calcule V1 - V0
		double nbNoeudg1=g1.getNodeCount();
		double nbNoeudg2=g2.getNodeCount();
		for (Node n : g2) { 
			if (g1.getNode(n.getId())!=null) {
				nbNoeudg1--;
			}
		}
		V1MoinsV0=nbNoeudg1;
		nbNoeudg1=g1.getNodeCount();
		//calcul V0 - V1
		for (Node n : g1) { 
			if (g2.getNode(n.getId())!=null) {
				nbNoeudg2--;
			}
		}
		V0MoinsV1=nbNoeudg2;
		nbNoeudg2=g2.getNodeCount();
		//calcul (V1 union V0)
		V1UnionV0+=g1.getNodeCount()+g2.getNodeCount();
		//calcul V1 intersection V0
		for (Node n : g2) { 
			if (g1.getNode(n.getId())!=null) {
				V1IntersectionV0++;
			}
		}
		resultat=((V1MoinsV0) + (V0MoinsV1)) / ((V1UnionV0) - (V1IntersectionV0));
		return resultat;
	}

	//copie de g1 
	private static Graph copie(Graph g1) {
		Graph resultat=new DefaultGraph("resultat");
		for (Node n : g1) {
			resultat.addNode(n.getId());
		}
		return resultat;
	}


	public  void calculMetrique( Graph graph) {
		System.out.println("Nombre de nœuds: "+graph.getNodeCount());
		System.out.println("Nombre de liens: "+graph.getEdgeCount());
		System.out.println("Degré moyen: "+Toolkit.averageDegree(graph));
		System.out.println("le coefficient de clustering: "+Toolkit.averageClusteringCoefficient(graph));
		System.out.println("isConnected: "+Toolkit.isConnected(graph));

		System.out.println("Distribution de degree");

		int[] tableauDistributionDegre=Toolkit.degreeDistribution(graph);
		for (int i = 0; i < tableauDistributionDegre.length; i++) {
			System.out.println(tableauDistributionDegre[i]);
			distributionDegre.add(0.0+tableauDistributionDegre[i]);

		}

	}

	@SuppressWarnings("unused")
	public  void analyse(int numeroAnalyse) throws Exception {
		Fichier fichier=new Fichier();
		if (numeroAnalyse==1) {
			id=numeroAnalyse;
			separateur=",";
			File f=fichier.getResource(FILE_NAME_CSV);
			data = fichier.readFile(f);
			fichier.transformationCsvEnTx(data,FILE_NAME_TXT,",");
		}else if(numeroAnalyse==2) {
			id=numeroAnalyse;
			separateur=" ";
			File f=fichier.getResource(FILE_NAME_CSV_MSG);
			data = fichier.readFile(f);
			fichier.transformationCsvEnTx(data,FILE_NAME_TXT_MSG," ");
		} else if(numeroAnalyse==3) {
			id=numeroAnalyse;
			separateur=" ";
			File f=fichier.getResource(FILE_NALE_EMAIL_EU_CSV);
			data = fichier.readFile(f);
			fichier.transformationCsvEnTx(data,FILE_NALE_EMAIL_EU_TXT," ");
		}
	}

	public  void affichageTableauRatio() {
		for (int i = 0; i < tableauRatio.size(); i++) {
			System.out.println(tableauRatio.get(i));
		}
		System.out.println("La taille tableauRatio: "+tableauRatio.size());
	}

	public  void writeData(String filename) {
		try {
			File file = new File(System.getProperty("user.dir") + "/" + filename);
			// créer le fichier s'il n'existe pas
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0 ; i < tableauRatio.size() ; i++) {
				bw.write(tableauRatio.get(i)+"");
				//bw.write(i+" "+tab.get(i));

				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
