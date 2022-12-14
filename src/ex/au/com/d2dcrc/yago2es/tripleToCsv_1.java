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

                // indexNode???????????????????????????
                int indexNode = list.get(1).substring(2).indexOf('1') + 3;

                // node??????????????????
                String node = list.get(1).substring(2, indexNode);

                // ??????????????????ttl????????????NXXX????????????csv???Id?????????
                String[] id = list.get(1).split("_");

                // first???????????????Id
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
                    // ?????????????????????Map???
                    dataMap.put("D" + dataId, data);

                    // ??????????????????????????????????????????
                    int indexJin = list.get(2).indexOf('#');

                    // property???????????????
                    String property = list.get(2).substring(indexJin + 1, list.get(2).length() - 1);

//                System.out.println(nodeId + "  " + propertyId);

                    // ?????????????????????????????????csv???
                    bufferedWriter1.write(nodeId + ",D" + dataId + "," + "Directed" + "," + property + ",1");
                    bufferedWriter1.newLine();
                }

                if (list.get(3).charAt(0) == '_') {

                    // ??????????????????ttl????????????NXXX????????????csv???Id?????????
                    String[] idd = list.get(3).split("_");

                    String otherNodeID = "V";

                    for (int i = 0; i < idd.length; i++) {

//                System.out.println(idd[i]);
                        if (idd[i].length() != 0 && idd[i].charAt(0) == 'N'){

                            otherNodeID += "_" + idd[i];
                        }
                    }

                    String edge = list.get(2).substring(list.get(2).indexOf('#') + 1, list.get(2).length() - 1);

                    // ???????????????????????????????????????csv???
                    bufferedWriter1.write(nodeId + "," + otherNodeID + "," + "Directed" + "," + edge + ",1");
                    bufferedWriter1.newLine();
                }


                bufferedWriter1.close();

            }else if (list.get(1).contains("N") && list.get(2).charAt(0) == '<' && list.get(3).charAt(0) == '<'){
                // ??????????????????????????????

                // indexNode???????????????????????????
                int indexNode = list.get(1).substring(2).indexOf('1') + 3;

                // node??????????????????
                String node = list.get(1).substring(2, indexNode);

                // ??????????????????ttl????????????NXXX????????????csv???Id?????????
                String[] id = list.get(1).split("_");

                // nodeId???????????????Id
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
