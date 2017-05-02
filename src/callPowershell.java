import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class callPowershell {



    private static Object[] model_1;
    private static int count;


    public static void main(String[] args) throws IOException {
        make_map(parse_file());
        //System.out.print(Arrays.toString(parse_file()));
        display app = new display();


    }

    public static void powerShellMount() throws IOException {
        String dir = System.getProperty("user.dir");

        String st = new String(dir);
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"test2.ps1\"");
        System.out.println(dir);
        String command = "powershell.exe  \"C:\\test2.ps1\"";
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        String line;
        System.out.println("Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                ps.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
            if(line.contains("Dest =")){
                String segments[] = line.split(" ");
                String document = segments[segments.length - 1];
                System.out.println(document);
            }

            String error = line;
            if(error.contains("ERROR 3") || error.contains("ERROR 2") || error.contains("The parameter is incorrect")){
                System.out.println("ERROR: UNABLE TO FIND THE DESTINATION /n Please ensure no disks are disconnected during the process.Reconnect the disk and try again");
                errorCheck();
            }
        }
        stdout.close();

    }

    public static void  errorCheck(){
        JOptionPane.showMessageDialog(null, "There has been an issue with one of your drives \nThis error message may reoccur on the same drive \n Process will attempt to continue on other drives", "alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void progressWindow(){


    }

    public static String[] parse_file() {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get("settings.ini"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] array = list.toArray(new String[list.size()]);

        return array;
    }

    public static HashMap make_map(String[] array){
        HashMap<String, String[]> map = new HashMap<>();
        for(int i=0; i < array.length; i++){
            String[] split = array[i].split(":");
            String key = split[0];
            String[] values = split[1].split(",");
            map.put(key,values);
        }
        return map;
    }

    public static String select_file(HashMap map, String input){
        System.out.print(map.isEmpty());

        String key = getKeyFromValue(map,input);


        String path = key;
        System.out.print(path);
        System.out.print(input);


        return path;
    }

    public static Object[] generateDeviceList(HashMap map) {
        ArrayList<String[]> all_keys = new ArrayList<>();
        for (Object key: map.keySet()) {
            all_keys.add((String[])map.get(key));
        }

        List list = new ArrayList();
        for(int i=0; i < 3;i++){
            String[] individual = all_keys.get(i);
            for(int x=0; x<individual.length;x++){
                list.add(individual[x]);
            }

        }
        model_1 = list.toArray();
        Arrays.sort(model_1);
        return model_1;
    }

    public static String getKeyFromValue(HashMap map, String input) {
        System.out.print(input);
        for (Object o : map.keySet()) {
            if (map.get(o).equals(input)) {
                System.out.print("Yes");
                return (String)o;
            }
        }
        return null;
    }



}