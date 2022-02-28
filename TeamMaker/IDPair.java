package TeamMaker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IDPair {
    public int id1;
    public int id2;

    public IDPair(int id1, int id2){
        this.id1=id1;
        this.id2=id2;
    }

    public static ArrayList<IDPair> readIDPair(String filename) throws IOException {
        ArrayList<IDPair> pairs = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null)  {
            String[] s = line.split(",");    // use comma as separator
            int id1 = Integer.parseInt(s[1]);
            int id2 = Integer.parseInt(s[3]);
            pairs.add(new IDPair(id1, id2));
        }
        return pairs;
    }

    public static ArrayList<Integer> search(int id, ArrayList<IDPair> idPairs){
        ArrayList<Integer> matches = new ArrayList<>();
        for(IDPair idPair : idPairs){
            if(idPair.id1==id) matches.add(idPair.id2);
            if(idPair.id2==id) matches.add(idPair.id1);
        }
        return matches;
    }
}
