import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by foster626@gmail.com on 3/5/2017.
 */
public class InvasionCollection {
    private ArrayList<CogInvasion> cogInvasions;
    InvasionCollection(){
        cogInvasions=new ArrayList<>();
        try {
            JSONObject jsonObject = common.readJsonFromUrl("http://toonhq.org/api/v1/invasion/");
            JSONArray invasions = jsonObject.getJSONArray("invasions");
            for(int i=0;i<invasions.length();i++){
                cogInvasions.add(new CogInvasion(invasions.getJSONObject(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getMessage(){
        if(cogInvasions.size()==0){
            return "There are no invasions.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("There is");
        for(int i=0;i<cogInvasions.size();i++){
            if(i+1==cogInvasions.size()){
                sb
                        .append(" and a ")
                        .append(cogInvasions.get(i).getCogName())
                        .append(" invasion in ")
                        .append(cogInvasions.get(i).getDistrictName())
                        .append(".");
                break;
            }
            sb
                    .append(" a ")
                    .append(cogInvasions.get(i).getCogName())
                    .append(" invasion in ")
                    .append(cogInvasions.get(i).getDistrictName())
                    .append(",");
        }
        return sb.toString();
    }
    public int size(){
        return cogInvasions.size();
    }
}
