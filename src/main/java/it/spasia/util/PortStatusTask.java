package it.spasia.util;

import android.os.AsyncTask;

import com.vveye.T2u;

public class PortStatusTask extends AsyncTask<Character, Integer, Integer> {
    @Override
    protected Integer doInBackground(Character... strings) {
        return T2u.PortStatus(strings[0]);
    }
}
