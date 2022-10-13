package au.com.d2dcrc.yago2es;

import java.io.*;
import java.util.HashMap;
import java.util.*;

public class graph_on {
    public static void main(String[] args) throws IOException {
        /**--------Phase 0: Create Linked Entity Graph based on output Karma RDF--------------------**/
//        String s = "museum_kg_s01.ttl";
//        System.out.println(s.substring(0, s.length() -4 ));
//        System.exit(1);

//        test(args);
//
//        System.exit(1);

        File rdfDir = new File("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\rdf-20220801");
        File[] files = rdfDir.listFiles();
        for (File file : files) {
//            System.out.println(file.getName());

//            Graph linkedGraph = LinkedEntityGraph.create1(file.getAbsolutePath());
            Graph linkedGraph = LinkedEntityGraph.create1("C:\\Users\\16141\\Desktop\\KG_demo\\s01.ttl");
//        Graph linkedGraph = LinkedEntityGraph.create(args[0]);
//        for (Vertex vertex : linkedGraph.getAllVertex()) {
//            System.out.println(vertex.getLabel());
//        }

//        for (Edge edge : linkedGraph.getAllEdges()) {
//            System.out.println(edge.getLabel());
//        }


            List<Graph> temp = new ArrayList<>();
            Map<String, Integer> nodeLabels = new HashMap<>();
            Map<String, Integer> edgeLabels = new HashMap<>();
            Scanner scanner1 = new Scanner(new File("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\node_label.txt"));
            Scanner scanner2 = new Scanner(new File("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\edge_label.txt"));
//        Scanner scanner = new Scanner(new File(labelPath));

            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine().trim();
                String[] strs = line.split("\\s+");
                nodeLabels.put(strs[0], Integer.parseInt(strs[1]));
            }
            scanner1.close();

            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine().trim();
                String[] strs = line.split("\\s+");
                edgeLabels.put(strs[0], Integer.parseInt(strs[1]));
            }
            scanner2.close();


            System.out.println(nodeLabels);
            System.out.println(edgeLabels);
//        System.out.println(linkedGraph.numerateEdgeLables());
//        System.out.println(linkedGraph.numerateVertexLables());
//        linkedGraph.reOrderLabel(linkedGraph.numerateVertexLables(), linkedGraph.numerateEdgeLables(), linkedGraph);
//        System.exit(1);
        linkedGraph.reOrderLabel(nodeLabels, edgeLabels, linkedGraph);

//        FileWriter fw = new FileWriter("D:\\DataMatching\\labels.txt");
//        fw.write(linkedGraph.numerateVertexLables().toString()+"\n");
//        fw.write(linkedGraph.numerateEdgeLables().toString()+"\n");
//        fw.close();

            temp.add(linkedGraph);
//        GSpanWrapper.writeToInputFile(temp, args[1]);
//            GSpanWrapper.writeToInputFile(temp, "C:\\D_Drive\\ASM\\DataSets\\museum-edm\\tmp\\"+file.getName()+".lg");
            GSpanWrapper.writeToInputFile(temp, "C:\\Users\\16141\\Desktop\\KG_demo\\s01.lg");
            System.exit(1);
        }

    }

    public static void ttlToLg(String ttlPath, String nodeLabelPath, String edgeLabelPath, String lgPath) throws FileNotFoundException {

        Graph linkedGraph = LinkedEntityGraph.create1(ttlPath);
        List<Graph> temp=new ArrayList<Graph>();
        Map<String, Integer> nodeLabels = new HashMap<>();
        Map<String, Integer> edgeLabels = new HashMap<>();
        Scanner scanner1 = new Scanner(new File(nodeLabelPath));
        Scanner scanner2 = new Scanner(new File(edgeLabelPath));

//        Scanner scanner = new Scanner(new File(labelPath));
        while (scanner1.hasNextLine()){
            String line = scanner1.nextLine().trim();
            String[] strs = line.split("\\s+");
            nodeLabels.put(strs[0],Integer.parseInt(strs[1]));
        }
        scanner1.close();

        while (scanner2.hasNextLine()){
            String line = scanner2.nextLine().trim();
            String[] strs = line.split("\\s+");
            edgeLabels.put(strs[0],Integer.parseInt(strs[1]));
        }
        scanner2.close();

        linkedGraph.reOrderLabel(nodeLabels, edgeLabels, linkedGraph);

        temp.add(linkedGraph);
        GSpanWrapper.writeToInputFile(temp, lgPath);
    }


    public static void test(String[] args) throws FileNotFoundException {
        String rdfPath = args[0];
        String nodeLabelPath = args[1];
        String edgeLabelPath = args[2];
        String lgPath = args[3];
//        String lgPath = "C:\\D_Drive\\ASM\\DataSets\\museum-crm\\tmp\\kg";

//        File rdfDirPath = new File("C:\\D_Drive\\ASM\\DataSets\\museum-crm\\tmp\\rdf");
        File rdfDirPath = new File(rdfPath);

        File[] rdfs = rdfDirPath.listFiles();
        for (File rdf : rdfs) {
            System.out.println(rdf.getAbsolutePath());
            System.out.println(rdf.getName());
            String rdfLgPath = lgPath + "/" + rdf.getName().substring(0, rdf.getName().length() - 4) + ".lg";
//            String labelPath = "C:\\D_Drive\\ASM\\DataSets\\museum-crm\\label.txt";
            ttlToLg(rdf.getAbsolutePath(), nodeLabelPath, edgeLabelPath, rdfLgPath);
        }


        System.exit(1);
    }

}
