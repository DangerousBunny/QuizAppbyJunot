package com.example.junot.quizapp;

import android.os.AsyncTask;

import com.example.junot.enums.EnumAsyncTask;
import com.example.junot.interfaces.AsyncTaskListener;


public class AsyncTaskUtils extends AsyncTask<Object, Integer, String> {

    private AsyncTaskListener listener;
    private EnumAsyncTask enums;

    public AsyncTaskUtils() {

    }

    public AsyncTaskUtils(AsyncTaskListener listener, EnumAsyncTask enums) {
        this.listener = listener;
        this.enums = enums;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (listener != null) {
            listener.onProgressUpdate(enums, values);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onPreExecute(enums);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null) {
            listener.onPostExecute(enums, s);
        }
    }

    @Override
    protected String doInBackground(Object... objects) {
        if (listener != null) {
            return listener.doInBackground(enums, objects);
        }
        return null;
    }

    public void setListener(AsyncTaskListener listener) {
        this.listener = listener;
    }

    public void setEnums(EnumAsyncTask enums) {
        this.enums = enums;
    }


}
