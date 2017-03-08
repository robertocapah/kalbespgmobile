package library.salesforce.common;

/**
 * Created by Rian Andrivani on 3/8/2017.
 */

public class mProductCompetitorData {
    public synchronized String get_intmProductUmbrandId() {
        return _intmProductUmbrandId;
    }
    public synchronized void set_intmProductUmbrandId(String _intmProductUmbrandId) {
        this._intmProductUmbrandId = _intmProductUmbrandId;
    }

    public synchronized String get_txtProductCompetitorCode() {
        return _txtProductCompetitorCode;
    }
    public synchronized void set_txtProductCompetitorCode(String _txtProductCompetitorCode) {
        this._txtProductCompetitorCode = _txtProductCompetitorCode;
    }

    public synchronized String get_txtProductCompetitorName() {
        return _txtProductCompetitorName;
    }
    public synchronized void set_txtProductCompetitorName(String _txtProductCompetitorName) {
        this._txtProductCompetitorName = _txtProductCompetitorName;
    }

    public synchronized String get_txtAliasName() {
        return _txtAliasName;
    }
    public synchronized void set_txtAliasName(String _txtAliasName) {
        this._txtAliasName = _txtAliasName;
    }

    private String _intmProductUmbrandId;
    private String _txtProductCompetitorCode;
    private String _txtProductCompetitorName;
    private String _txtAliasName;
    public mProductCompetitorData(){

    }

    public String Property_intmProductUmbrandId="intmProductUmbrandId";
    public String Property_txtProductCompetitorCode="_txtProductCompetitorCode";
    public String Property_txtProductCompetitorName="_txtProductCompetitorName";
    public String Property_txtAliasName="_txtAliasName";
    public String Property_ListOfmProductCompetitor="ListOfmProductCompetitor";
    public String Property_All=Property_intmProductUmbrandId+","+Property_txtAliasName+","+Property_txtProductCompetitorCode+","+
            Property_txtProductCompetitorName;

}
