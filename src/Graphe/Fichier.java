package Graphe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Fichier {
	public Fichier() {
		// TODO Auto-generated constructor stub
	}

	public  String getResourcePath(String fileName) {
		final File f = new File("");
		final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
		return dossierPath;
	}

	public  File getResource(String fileName) {
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		return file;
	}


	public  List<String> readFile(File file) throws Exception {

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
	public  void transformationCsvEnTx(List<String> tab,String filename,String separateur) throws Exception  {
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

}
