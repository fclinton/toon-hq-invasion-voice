import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

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
            for (int i = 0; i < invasions.length(); i++) {
                cogInvasions.add(new CogInvasion(invasions.getJSONObject(i)));
            }
        } catch (JSONException e){
            try {
                InputStream in = new URL("http://toonhq.org/api/v1/invasion/").openStream();
                e.addSuppressed(new Exception("TOONHQ RETURNED:" + IOUtils.toString(in)));
                IOUtils.closeQuietly(in);
            } catch (IOException e1) {
                e1.printStackTrace();
             }
            e.printStackTrace();
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
    public String getMessage(String specificCogName){
        if(Objects.equals(specificCogName, "blood sucker"))specificCogName="bloodsucker";
        if(Objects.equals(specificCogName, "headhunter"))specificCogName="head hunter";
        if(Objects.equals(specificCogName, "bigwig"))specificCogName="big wig";
        ArrayList<CogInvasion> specificInvasions = new ArrayList<>();
        for(CogInvasion cogInvasion:cogInvasions){
            if(cogInvasion.getCogName().equalsIgnoreCase(specificCogName))specificInvasions.add(cogInvasion);
        }
        if(specificInvasions.size()==0){
            return "There are currently no "+specificCogName+ " invasions.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("There is");
        if(specificInvasions.size()==1){
            sb
                    .append(" an ")
                    .append(specificInvasions.get(0).getCogName())
                    .append(" invasion in ")
                    .append(specificInvasions.get(0).getDistrictName())
                    .append(".");
        }else {
            for (int i = 0; i < specificInvasions.size(); i++) {
                if (i + 1 == specificInvasions.size()) {
                    sb
                            .append(" and a ")
                            .append(specificInvasions.get(i).getCogName())
                            .append(" invasion in ")
                            .append(specificInvasions.get(i).getDistrictName())
                            .append(".");
                    break;
                }
                sb
                        .append(" a ")
                        .append(specificInvasions.get(i).getCogName())
                        .append(" invasion in ")
                        .append(specificInvasions.get(i).getDistrictName())
                        .append(",");
            }
        }
        return sb.toString();
    }
    public int size(){
        return cogInvasions.size();
    }
}
