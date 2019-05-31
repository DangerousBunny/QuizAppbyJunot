package com.example.junot.interfaces;

import com.example.junot.enums.EnumAsyncTask;

public interface AsyncTaskListener {

    void onProgressUpdate(EnumAsyncTask enums, Integer... values);
    void onPreExecute(EnumAsyncTask enums);
    String doInBackground(EnumAsyncTask enums, Object... objects);
    void onPostExecute(EnumAsyncTask enums, String result);
}
