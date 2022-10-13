package au.com.d2dcrc.yago2es;


import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonTest {
    public static void main(String[] args) throws IOException {



        Graph linkedGraph = LinkedEntityGraph.create("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\rdf-20220801\\s01.ttl");
        linkedGraph.getAllVertex();

        Map<String, Integer> nodeLabels = new HashMap<>();
        Map<String, Integer> edgeLabels = new HashMap<>();
        Scanner scanner1 = new Scanner(new File("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\node_label.txt"));
        Scanner scanner2 = new Scanner(new File("C:\\D_Drive\\ASM\\DataSets\\museum-edm\\edge_label.txt"));
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

        Map<Integer, String> entitiesMap = new HashMap<>();
//        Scanner scanner3 = new Scanner(new File("C:\\D_Drive\\ASM\\DataSets\\museum-crm\\entity.txt"));
//        while(scanner3.hasNextLine()){
//            String line = scanner3.nextLine().trim();
//            String[] strs = line.split("\t");
//            entitiesMap.put(Integer.parseInt(strs[0]),strs[1]);
//        }
//        scanner3.close();

        System.out.println(entitiesMap);
        writeIntoJson(linkedGraph,nodeLabels,edgeLabels,entitiesMap,"C:\\D_Drive\\ASM\\DataSets\\museum-edm\\s01.json");

    }


    public static void writeIntoJson(Graph graph, Map<String,Integer> nodeLabels, Map<String,Integer> edgeLabels,
                                     Map<Integer,String> entitiesMap, String fileName) throws IOException {

        Collection<Vertex> allVertex = graph.getAllVertex();
        Iterator<Vertex> vit = allVertex.iterator();
        graph.reOrderLabel(nodeLabels,edgeLabels,graph);
        FileWriter fw = new FileWriter(fileName);
        fw.write("{\"Vertices\":[\n");

        while (vit.hasNext()){
            Vertex vertex = vit.next();
            int id = vertex.getIndex();
            String labelStr = vertex.getLabel();
            int label = Integer.parseInt(labelStr);
            Map<String,String> attributes = vertex.getAttributes();
            System.out.println(attributes);
//            for (Map.Entry<String, String> entry : attributes.entrySet()) {
////                System.out.println(entry.getKey());
//                System.out.println(entry.getValue());
//            }
            String str = "";
//            str += "{\"";
//            str += entitiesMap.get(label);
            str += attributes.toString();

//            str += "\":\"";
//            try {
//                if (label == 0){
//                    str += attributes.get("label");
//                }else {
//                    str += attributes.values().toArray()[0];
//                }
//            }catch (ArrayIndexOutOfBoundsException e){
//                str += "";
//            }
//            str += "\"}";

//            System.out.println(str);
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("entity_type", label);
//            if (label == 2 || label == 7 || label == 8 || label == 14 || label == 1){
//                str = "";
//            }
            if (label == 2){
                str = "";
            }
            jsonObject.addProperty("properties", str);
            fw.write("\t");
            fw.write(gson.toJson(jsonObject).replace("\\u003d", ":"));
//            System.out.println(jsonObject);
            if (vit.hasNext()){
                fw.write(",");
            }

            fw.write("\n");
        }
        fw.write("]}\n");

//        System.out.println("===================================");
//        System.out.println(count);

        Iterator<Edge> eit = graph.getAllEdges().iterator();
        fw.write("{\"Edges\":[\n");
        while (eit.hasNext()){
            Edge edge = eit.next();
            int weight = edge.getWeight();
            int start = edge.getStartPoint().getIndex();
            int end = edge.getEndPoint().getIndex();
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("source_id",start);
            jsonObject.addProperty("target_id",end);
            jsonObject.addProperty("relationship_type",weight);
//            System.out.println(gson.toJson(jsonObject));
            fw.write("\t");
            fw.write(gson.toJson(jsonObject));
            if (eit.hasNext()){
                fw.write(",");
            }
            fw.write("\n");
        }
        fw.write("]}");

        fw.close();

    }


}
