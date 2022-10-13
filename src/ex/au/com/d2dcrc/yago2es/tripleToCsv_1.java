package au.com.d2dcrc.yago2es;

import java.io.*;
import java.util.*;

public class tripleToCsv_1 {

    public static Map<String, String> nodeMap = new HashMap<>();
    public static Map<String, String> dataMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

//        String triplePath = "C:\\karma\\example\\json_res\\WSP1WS8triple.txt";
//        String csvNodePath = "C:\\karma\\example\\json_res\\WSP1WS8NodeCsv.csv";
//        String csvEdgePath = "C:\\karma\\example\\json_res\\WSP1WS8EdgeCsv.csv";

        String triplePath = "C:\\Users\\Dell\\Desktop\\tmp\\addSoilWeaData_Triple.txt";
        String csvNodePath = "C:\\Users\\Dell\\Desktop\\tmp\\addSoilWeaData_nodeCsv.csv";
        String csvEdgePath = "C:\\Users\\Dell\\Desktop\\tmp\\addSoilWeaData_edgeCsv.csv";

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvNodePath, false));
        bufferedWriter.write("Id,Label,Polygon");
        bufferedWriter.newLine();

        BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(csvEdgePath, false));
        bufferedWriter1.write("Source,Target,Type,Label,Weight");
        bufferedWriter1.newLine();
        bufferedWriter1.close();


        Scanner scanner = new Scanner(new File(triplePath));
        List<String> list = new ArrayList<>();

        while (scanner.hasNext()) {

            if (list.size() < 4) {

                list.add(scanner.nextLine().trim());
            } else {

                judge(list, csvNodePath, csvEdgePath);
                list.clear();
            }
        }

        Iterator iter = nodeMap.entrySet().iterator();
        while (iter.hasNext()) {
            String[] temp = iter.next().toString().split("=");
            bufferedWriter.write(temp[0] + "," + temp[1] + ",0");
            bufferedWriter.newLine();
        }

        Iterator iter1 = dataMap.entrySet().iterator();
        while (iter1.hasNext()) {
            String[] temp = iter1.next().toString().split("=");
            bufferedWriter.write(temp[0] + "," + temp[1] + ",3");
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        System.out.println(nodeMap);
        System.out.println(dataMap);
        System.out.println("Done!");
    }

    public static void judge(List<String> list, String csvNodePath, String csvEdgePath) {

        try {

//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvNodePath,true));
            BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(csvEdgePath, true));

            if (list.get(1).contains("N") && (list.get(3).contains("N") || list.get(1).contains("N") && list.get(3).contains("\""))) {

                // indexNode是实体类开始的下标
                int indexNode = list.get(1).substring(2).indexOf('1') + 3;

                // node是实体类名称
                String node = list.get(1).substring(2, indexNode);

                // 接下来是使用ttl文件中的NXXX用来进行csv中Id的表示
                String[] id = list.get(1).split("_");

                // first是实体类的Id
                String nodeId = "V";

                for (int i = 0; i < id.length; i++) {

//                System.out.println(id[i]);
                    if (id[i].length() != 0 && id[i].charAt(0) == 'N') {

                        nodeId += "_" + id[i];
                    }
                }

                nodeMap.put(nodeId, node);

//            System.out.println(nodeId);

                if (list.get(3).charAt(0) == '"') {

                    String data = list.get(3).substring(1, list.get(3).length() - 1);

                    int dataId = dataMap.size();
                    // 将数据存入数据Map中
                    dataMap.put("D" + dataId, data);

                    // 该情况下第三行中保存着属性名
                    int indexJin = list.get(2).indexOf('#');

                    // property是属性名称
                    String property = list.get(2).substring(indexJin + 1, list.get(2).length() - 1);

//                System.out.println(nodeId + "  " + propertyId);

                    // 将实体和属性的关系写入csv中
                    bufferedWriter1.write(nodeId + ",D" + dataId + "," + "Directed" + "," + property + ",1");
                    bufferedWriter1.newLine();
                }

                if (list.get(3).charAt(0) == '_') {

                    // 接下来是使用ttl文件中的NXXX用来进行csv中Id的表示
                    String[] idd = list.get(3).split("_");

                    String otherNodeID = "V";

                    for (int i = 0; i < idd.length; i++) {

//                System.out.println(idd[i]);
                        if (idd[i].length() != 0 && idd[i].charAt(0) == 'N'){

                            otherNodeID += "_" + idd[i];
                        }
                    }

                    String edge = list.get(2).substring(list.get(2).indexOf('#') + 1, list.get(2).length() - 1);

                    // 将两个实体类之间的关系写入csv中
                    bufferedWriter1.write(nodeId + "," + otherNodeID + "," + "Directed" + "," + edge + ",1");
                    bufferedWriter1.newLine();
                }


                bufferedWriter1.close();

            }else if (list.get(1).contains("N") && list.get(2).charAt(0) == '<' && list.get(3).charAt(0) == '<'){
                // 对于实体类定义的情况

                // indexNode是实体类开始的下标
                int indexNode = list.get(1).substring(2).indexOf('1') + 3;

                // node是实体类名称
                String node = list.get(1).substring(2, indexNode);

                // 接下来是使用ttl文件中的NXXX用来进行csv中Id的表示
                String[] id = list.get(1).split("_");

                // nodeId是实体类的Id
                String nodeId = "V";

                for (int i = 0; i < id.length; i++) {

//                System.out.println(id[i]);
                    if (id[i].length() != 0 && id[i].charAt(0) == 'N') {

                        nodeId += "_" + id[i];
                    }
                }

                nodeMap.put(nodeId, node);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
