package library.salesforce.common;

/**
 * Created by Robert on 06/04/2017.
 */

public class tLogErrorData {
    private String _intLogId;
    private String _txtLogDesk;
    private String _dtDate;
    private String _txtDeviceId;
    private String _intSubmit;
    private String _intSync;
    private String _txtFileName;

    public String Property_intLogId = "intLogId";
    public String Property_txtLogDesk = "txtLogDesk";
    public String Property_txtDeviceId = "txtDeviceId";
    public String Property_dtDate = "dtDate";
    public String Property_intSubmit = "intSubmit";
    public String Property_intSync = "intSync";
    public String Property_txtFileName = "txtFileName";
    public String Property_All = Property_intLogId + "," + Property_txtLogDesk + "," + Property_txtDeviceId + "," + Property_dtDate + "," + Property_intSubmit + "," + Property_intSync + "," + Property_txtFileName;

    public String get_intLogId() {
        return _intLogId;
    }

    public void set_intLogId(String _intLogId) {
        this._intLogId = _intLogId;
    }

    public String get_TxtLogDesk() {
        return _txtLogDesk;
    }

    public void set_txtLogDesk(String _txtLogDesk) {
        this._txtLogDesk = _txtLogDesk;
    }

    public String get_dtDate() {
        return _dtDate;
    }

    public void set_dtDate(String _dtDate) {
        this._dtDate = _dtDate;
    }

    public String get_txtDeviceId() {
        return _txtDeviceId;
    }

    public void set_txtDeviceId(String _txtDeviceId) {
        this._txtDeviceId = _txtDeviceId;
    }

    public String get_intSubmit(){
        return _intSubmit;
    }
    public void set_intSubmit(String _intSubmit){
        this._intSubmit = _intSubmit;
    }
    public String get_intSync(){
        return _intSync;
    }
    public void set_intSync(String _intSync){
        this._intSubmit = _intSync;
    }
    public String get_txtFileName(){
        return _txtFileName;
    }
    public void set_txtFileName(String _txtFileName){
        this._txtFileName = _txtFileName;
    }
}
