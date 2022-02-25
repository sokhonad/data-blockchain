package Graphe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;


public class gerationGraphe {

	private final static String FILE_NAME_CSV = "Data/soc-sign-bitcoinalpha.csv";
	private final static String FILE_NAME_TXT = "Data/soc-sign-bitcoinalpha.txt";
	private final static String FILE_NAME_CSV_MSG = "Data/data.csv";
	private final static String FILE_NAME_TXT_MSG = "Data/data.txt";
	private  static String separateur;



	private  static Graph graphe;
	private static List<String> data;
	private static Donnees donnees;
	private static List<Double> tableauRatio=new ArrayList<Double>() ;

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
	public static void transformationCsvEnTx(List<String> tab,String filename,String separateur) throws Exception  {
		String filepath = System.getProperty("user.dir") + File.separator + filename;
		FileWriter fileWriter = new FileWriter(filepath);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < tab.size(); i++) {
			String b=tab.get(i);
			if (separateur==",") {
				String[] ligne = b.split(",");          
				for (int j = 0; j < ligne.length; j++) {
					if(j<=1 || j==3) {
						StringBuilder line = new StringBuilder();
						line.append(ligne[j]).append(" ");
						bufferedWriter.write(line + "");
					}
				}	
			}else {
				String[] ligne = b.split(" ");          
				for (int j = 0; j < ligne.length; j++) {
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
			String[] ligne = b.split(separateur);
			if(graphe.getNode(ligne[1])==null) {
				graphe.addNode(ligne[1]);

			}
			if(graphe.getNode(ligne[0])==null) {
				graphe.addNode(ligne[0]);

			}
		}

		for (int i = 0; i < tab.size(); i++) {
			String b=tab.get(i);
			String[] ligne = b.split(separateur);
			if(graphe.getEdge(ligne[0]+"-->"+ligne[1])==null) {
				graphe.addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
			}
		}
		calculMetrique(graphe);
		System.setProperty("org.graphstream.ui", "swing");
		graphe.display();

	}

	public static void visualisationDynamique(List<String> tab,Graph graphe,int taille) throws Exception  {
		//les graphes temporaire
		Graph grapheTemp1=null;
		Graph grapheTemp2=null;
		//demarrager de comparaison de grapheTemp1 et grapheTemp2
		int demarrer=-1;
		int compter=0;
		//ajout des noeuds
		donnees=new Donnees();
		graphe=new DefaultGraph("graphe");
		for (int i = 0; i < tab.size(); i++) {
			String b=tab.get(i);
			String[] ligne = b.split(separateur);
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
			String[] ligne = b.split(separateur);
			if(graphe.getEdge(ligne[0]+"-->"+ligne[1])==null) {
				graphe.addEdge(ligne[0]+"-->"+ligne[1],ligne[0],ligne[1],true);
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
		if (separateur==",") {
			debut=1289192400;
			fin=1453438800;
		}else if(separateur==" ") {
			debut=1082040961;
			fin=1098777142;
		}
		while(debut<=fin ) {
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
			Thread.sleep( 10);
			//affichage nombre des noeuds graphe dynamique
			//System.out.println("Nombre de noeud:"+graphe1.getNodeCount());
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
			debut+=taille;
		}
		System.out.println("compter:"+compter);
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


	public static void calculMetrique( Graph graph) {
		System.out.println("Nombre de nœuds: "+graph.getNodeCount());
		System.out.println("Nombre de liens: "+graph.getEdgeCount());
		System.out.println("Degré moyen: "+Toolkit.averageDegree(graph));
		System.out.println("le coefficient de clustering: "+Toolkit.averageClusteringCoefficient(graph));
		System.out.println("isConnected: "+Toolkit.isConnected(graph));

		System.out.println("Distribution de degree");

		int[] tableauDistributionDegre=Toolkit.degreeDistribution(graph);
		for (int i = 0; i < tableauDistributionDegre.length; i++) {
			System.out.println(tableauDistributionDegre[i]);

		}

	}

	@SuppressWarnings("unused")
	private static void analyse(int numeroAnalyse) throws Exception {
		//FILE_NAME_CSV_MSG
		//FILE_NAME_TXT_MSG
		if (numeroAnalyse==1) {
			separateur=",";
			File f=getResource(FILE_NAME_CSV);
			data = readFile(f);
			transformationCsvEnTx(data,FILE_NAME_TXT,",");
		}else if(numeroAnalyse==2) {
			separateur=" ";
			File f=getResource(FILE_NAME_CSV_MSG);
			data = readFile(f);
			transformationCsvEnTx(data,FILE_NAME_TXT_MSG," ");
		}
	}

	private static void affichageTableauRatio() {
		for (int i = 0; i < tableauRatio.size(); i++) {
			System.out.println(tableauRatio.get(i));
		}
		System.out.println("La taille tableauRatio: "+tableauRatio.size());
	}

	public static void writeData(String filename, List<Double> tab) {
		try {
			File file = new File(System.getProperty("user.dir") + "/" + filename);
			// créer le fichier s'il n'existe pas
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0 ; i < tab.size() ; i++) {
				bw.write(tab.get(i)+"");
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception   {
		analyse(1);
		//visualisationStatique(data, graphe);
		visualisationDynamique(data, graphe,900000 );//500000
				affichageTableauRatio();
				System.out.println("Fin programme!");
				writeData("1donneeRatio900000.dat",tableauRatio );

	}
}
