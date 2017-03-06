import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by foster626@gmail.com on 3/5/2017.
 */
public class CogInvasion {
    private int id;
    private double defeatRate;
    private long startTime;
    private int defeatedNum;
    private int totalNum;
    private long asOf;
    private boolean manual;
    private int reports;
    private String districtName;
    private String cogName;

    public CogInvasion(int id, double defeatRate,long startTime,int defeatedNum,int totalNum,long asOf,
                       boolean manual,int reports, String districtName,String cogName){
        this.asOf=asOf;
        this.manual=manual;
        this.reports=reports;
        this.cogName=cogName;
        this.districtName=districtName;
        this.id=id;
        this.defeatRate=defeatRate;
        this.startTime=startTime;
        this.defeatedNum=defeatedNum;
        this.totalNum=totalNum;
        adjustNames();
    }
    public CogInvasion(JSONObject invasionAsJson){
        id=invasionAsJson.getInt("id");
        defeatRate=invasionAsJson.getDouble("defeat_rate");
        startTime=invasionAsJson.getLong("start_time");
        defeatedNum=invasionAsJson.getInt("defeated");
        totalNum=invasionAsJson.getInt("total");
        asOf=invasionAsJson.getLong("as_of");
        manual=invasionAsJson.getBoolean("manual");
        reports=invasionAsJson.getInt("reports");
        districtName=invasionAsJson.getString("district");
        cogName=invasionAsJson.getString("cog");
        adjustNames();
    }
    private void adjustNames(){
        if(Objects.equals(cogName, "The Big Cheese"))cogName="Big Cheese";
        if(Objects.equals(cogName, "Two-Face"))cogName="Two Face";
    }//Change name for cogs in order to be easily listed, formed for dictation






    //Getters


    public boolean isManual() {
        return manual;
    }

    public double getDefeatRate() {
        return defeatRate;
    }

    public int getDefeatedNum() {
        return defeatedNum;
    }

    public int getId() {
        return id;
    }

    public int getReports() {
        return reports;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public long getAsOf() {
        return asOf;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getCogName() {
        return cogName;
    }

    public String getDistrictName() {
        return districtName;
    }
}
