package Graphe;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

//for (Node n : g2) { 
//	if (g1.getNode(n.getId())==null) {
//		V1UnionV0++;
//	}
//}
//V1UnionV0+=g1.getNodeCount();

public class Test {
	public static void main(String[] args) {
		Graph g=new DefaultGraph("resultat");
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		g.addNode("D");
		for (Node n : g) {
			System.out.println(g.getNode(n.getId()));
		}
		

	}

}
