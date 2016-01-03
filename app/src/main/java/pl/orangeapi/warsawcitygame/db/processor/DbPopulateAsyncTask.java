package pl.orangeapi.warsawcitygame.db.processor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;

public class DbPopulateAsyncTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> {

    WarsawCitiGameDBAdapter adapter;
    WarsawCitiGameDBProcessor dbProcessor;
    Context context;
    private final String DIALOG_MESSAGE = "Updating contacts";

    private ProgressDialog mDialog = null;

    private void setDialog(Context context) {
        this.mDialog = new ProgressDialog(context);
        this.mDialog.setMessage(DIALOG_MESSAGE);
        this.mDialog.setCancelable(false);
    }

    public DbPopulateAsyncTask(Context context, WarsawCitiGameDBAdapter adapter) {
        this.setDialog(context);
        this.adapter = adapter;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        this.mDialog.show();
    }

    @Override
    protected Result doInBackground(Params... arg0) {

        //if (!adapter.isAlreadyPopulated()){
            dbProcessor = new WarsawCitiGameDBProcessor(context, adapter);
            try {
               dbProcessor.populateDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        // Update the UI if u need to

        // And then dismiss the dialog
        if (this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }
}