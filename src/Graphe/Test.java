package Graphe;

import java.util.List;

public class Test {


	public static void main(String[] args) throws Exception   {

		GenerationGraphe generationGraphe=new GenerationGraphe();
		//choix de fichier
		generationGraphe.analyse(3);
		//les visualisations
		//generationGraphe.visualisationStatique();
		generationGraphe.visualisationDynamique(86400,1);//500000
		generationGraphe.affichageTableauRatio();
		generationGraphe.writeData("EU24.dat");
		System.out.println("Fin programme!");

	}

}
