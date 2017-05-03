import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.*;

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
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        String line;
        //select_file();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                ps.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
            if(line.contains("Dest =")){
                String segments[] = line.split(" ");
                String document = segments[segments.length - 1];
                System.out.println(document);
                progressWindow(document,true);
            }
            else if(line.contains("Dest -")){
                String segments[] = line.split(" ");
                String document = segments[segments.length - 1];
                System.out.println(document);
                progressWindow(document, false);
            }

            String error = line;
            if(error.contains("ERROR") || error.contains("ERROR") || error.contains("The parameter is incorrect")){
                System.out.println("ERROR: UNABLE TO FIND THE DESTINATION /n Please ensure no disks are disconnected during the process.Reconnect the disk and try again");
                errorCheck();
            }
        }
        stdout.close();

    }

    public static void  errorCheck(){
        JOptionPane.showMessageDialog(null, "There has been an issue with one of your drives \nThis error message may reoccur on the same drive \n Process will attempt to continue on other drives", "alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void progressWindow(String input, Boolean check){
        Color color = gray;
        if(check){
            color = green;
        }
        else if(!check){
            color = red;
        }

        if(input.contains("Q")){
            display.drive1.setText("Q:");
            display.drive1.setBackground(color);
        }
        else if(input.contains("R")){
            display.drive2.setText("R:");
            display.drive2.setBackground(color);
        }
        else if(input.contains("S")){
            display.drive3.setText("S:");
            display.drive3.setBackground(color);
        }
        else if(input.contains("T")){
            display.drive4.setText("T:");
            display.drive4.setBackground(color);
        }
        else if(input.contains("U")){
            display.drive5.setText("U:");
            display.drive5.setBackground(color);
        }

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

        try(  PrintWriter out = new PrintWriter( "scripts/path.txt" )  ){
            out.println( path );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


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