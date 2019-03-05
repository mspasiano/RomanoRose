package it.spasia.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.EditTextPreference;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vveye.T2u;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.spasia.R;
import it.spasia.dao.DAOCard;
import it.spasia.database.Database;
import it.spasia.model.Card;
import it.spasia.util.PortStatusTask;

public class CardFragment extends android.support.v4.app.Fragment {
    private static final byte OPEN = 0x01;
    private static final byte CLOSE = 0x02;
    public char remotePort;
    private int port;
    private Socket socket;

    private Card card;
    private List<Switch> switches = new ArrayList<Switch>();
    private RelativeLayout relativeLayoutMain;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.card = (Card) getArguments().getSerializable("card");
        this.remotePort = (char) (8080);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Connessione in corso...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        relativeLayoutMain = (RelativeLayout) inflater.inflate(R.layout.card, null);

        EditText editText = relativeLayoutMain.findViewById(R.id.editTextTimeout);
        editText.setText(String.valueOf(card.getTimeout()));
        editText.setSelection(String.valueOf(card.getTimeout()).length());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String s1 = editText.getText().toString();
                if (s1 != null && s1.length() > 0) {
                    Log.i("Temporizzatore:", s1);
                    Database db = Database.create(getActivity().getApplicationContext());
                    DAOCard daoCard = new DAOCard(db.open());
                    card.setTimeout(new Integer(s1));
                    daoCard.update(card);
                }
            }
        });

        Switch aSwitch = relativeLayoutMain.findViewById(R.id.timeout_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editText.setEnabled(isChecked);
            }
        });

        ScrollView nestedScrollView = relativeLayoutMain.findViewById(R.id.container);

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        nestedScrollView.addView(tableLayout);

        TableRow tableRowButtons = createTableRow(tableLayout);
        Button buttonConnect = new Button(getActivity());
        buttonConnect.setText(R.string.connect);
        buttonConnect.setId(R.id.connect);
        buttonConnect.setCompoundDrawablesWithIntrinsicBounds(getActivity().getDrawable(R.drawable.ic_icon_connect), null, null, null);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (connect()) {
                            v.setEnabled(false);
                            relativeLayoutMain.findViewById(R.id.disconnect).setEnabled(true);
                            for (Switch window : switches) {
                                window.setEnabled(true);
                            }
                        }
                    }
                }, 100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allClose();
                    }
                }, card.getTimeout() * 1000);
            }
        });
        tableRowButtons.addView(buttonConnect);

        Button buttonDisconnect = new Button(getActivity());
        buttonDisconnect.setText(R.string.disconnect);
        buttonDisconnect.setId(R.id.disconnect);
        buttonDisconnect.setCompoundDrawablesWithIntrinsicBounds(getActivity().getDrawable(R.drawable.ic_icon_disconnect), null, null, null);
        buttonDisconnect.setEnabled(false);
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });
        tableRowButtons.addView(buttonDisconnect);

        TableRow tableRow = createTableRow(tableLayout);
        int k = 0;
        for (int i = 1; i < 17; i++) {
            final String releValue = card.getReleValue(i);
            if (releValue != null && releValue.length() > 0) {
                k++;
                Switch windowSwitch = new Switch(getActivity());
                if ((k % 2) != 0) {
                    tableRow = createTableRow(tableLayout);
                }
                windowSwitch.setEnabled(false);
                windowSwitch.setText(releValue);
                final int index = i;
                windowSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (windowSwitch.isChecked())
                            open(index, windowSwitch);
                        else
                            close(index);
                    }
                });
                switches.add(windowSwitch);
                tableRow.addView(windowSwitch);
            }
        }
        TextView noteTextView = relativeLayoutMain.findViewById(R.id.note_footer);
        noteTextView.setText(card.getNote());
        return relativeLayoutMain;
    }

    private TableRow createTableRow(TableLayout tableLayout) {
        TableRow tableRow = new TableRow(getActivity());
        tableLayout.addView(tableRow);
        return tableRow;
    }

    @Override
    public void onDestroyView() {
        Log.d("T2u", "onDestroyView card->" + card.getName());
        disconnect();
        super.onDestroyView();
    }

    private void open(int i, Switch windowSwitch) {
        byte[] abc = {(byte) 0xaa, 0X0F, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, (byte) 0xbb};
        abc[2] = (byte) (i - 1);
        abc[3] = OPEN;
        try {
            OutputStream output = socket.getOutputStream();
            output.write(abc);
            output.flush();
        } catch (IOException e) {
            Log.e("T2u", "Socket Exception", e);
        }
        if (((Switch) relativeLayoutMain.findViewById(R.id.timeout_switch)).isChecked()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    close(i);
                    windowSwitch.setChecked(false);
                }
            }, card.getTimeout() * 1000);

        }
    }

    private void allOpen() {
        byte[] abc = {(byte) 0xaa, 0X0F, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, (byte) 0xbb};
        abc[0] = (byte) -86;
        abc[1] = (byte) 10;
        for (int i = 0; i < 16; i++) {
            abc[i + 2] = OPEN;
        }
        abc[19] = (byte) -69;
        try {
            OutputStream output = socket.getOutputStream();
            output.write(abc);
            output.flush();
        } catch (IOException e) {
            Log.e("T2u", "Socket Exception", e);
        }
        for (Switch window : switches) {
            window.setChecked(true);
        }
        if (((Switch) relativeLayoutMain.findViewById(R.id.timeout_switch)).isChecked()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    allClose();
                }
            }, card.getTimeout() * 1000);
        }
    }

    private void close(int i) {
        byte[] abc = {(byte) 0xaa, 0X0F, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, (byte) 0xbb};
        abc[2] = (byte) (i - 1);
        abc[3] = CLOSE;
        try {
            OutputStream output = socket.getOutputStream();
            output.write(abc);
            output.flush();
        } catch (IOException e) {
            Log.e("T2u", "Socket Exception", e);
        }

    }

    private void allClose() {
        byte[] abc = {(byte) 0xaa, 0X0F, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, 0X01, (byte) 0xbb};
        abc[0] = (byte) -86;
        abc[1] = (byte) 0x0b;
        for (int i = 0; i < 16; i++) {
            abc[i + 2] = CLOSE;
        }
        abc[19] = (byte) -69;
        try {
            if (!socket.isClosed()) {
                OutputStream output = socket.getOutputStream();
                output.write(abc);
                output.flush();
            }
        } catch (IOException e) {
            Log.e("T2u", "Socket Exception", e);
        }
        for (Switch window : switches) {
            window.setChecked(false);
        }
    }

    public void disconnect() {
        if (socket != null) {
            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    if (T2u.Status() != -1) {
                        T2u.DelPort(remotePort);
                        T2u.Exit();
                    }
                    if (!socket.isClosed()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            Log.e("T2u", "Disconnect error...", e);
                        }
                    }
                }
            }, 2000);
            allClose();
        }
        relativeLayoutMain.findViewById(R.id.disconnect).setEnabled(false);
        relativeLayoutMain.findViewById(R.id.connect).setEnabled(true);
        for (Switch window : switches) {
            window.setEnabled(false);
        }

    }

    public boolean connect() {
        Log.d("T2u", "Start connect for T2u!");
        T2u.Init("nat.vveye.net", (char) 8000, "");
        byte[] result = new byte[1500];
        int num = T2u.Search(result);
        String tmp;
        Log.d("T2u", "T2u.Search:" + num);

        if (num > 0) {
            tmp = new String(result);
            Log.d("T2u", tmp);
        }

        while (T2u.Status() == 0) {
            SystemClock.sleep(1000);
            Log.d("T2u", "T2u.Status=" + T2u.Status());
        }

        Log.d("T2u", "T2u.status -> " + T2u.Status());


        if (T2u.Status() == 1) {
            int ret = T2u.SearchDVR(card.getUid(), result);
            System.out.println("T2u.SearchDVR:" + ret);
            if (ret > 0) {
                tmp = new String(result);
                System.out.println("T2u=" + tmp);
            }
        }

        if (T2u.Status() == 1) {
            int ret = T2u.Query(card.getUid());
            Log.d("T2u", "T2u.Query " + card.getUid() + " -> " + ret);
            if (ret > 0) {
                port = T2u.AddPortV3(card.getUid(), card.getPassword(), "127.0.0.1", remotePort, remotePort);
                Log.d("T2u", "T2u.add_port -> port:" + port);
            }
        }
        int portstatus = T2u.PortStatus(remotePort);
        Log.d("T2u", "portstatus:" + portstatus);

        while (portstatus == 0) {
            AsyncTask<Character, Integer, Integer> execute = new PortStatusTask().execute(remotePort);
            try {
                portstatus = execute.get();
                if (portstatus != 0)
                    execute.cancel(true);
            } catch (InterruptedException e) {
                Log.e("T2u", "PortStatusTask portstatus:" + portstatus, e);
            } catch (ExecutionException e) {
                Log.e("T2u", "PortStatusTask portstatus:" + portstatus, e);
            }
        }
        if (portstatus == 1) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", remotePort), 5000);
            } catch (IOException e) {
                Log.e("T2u", "Socket Exception", e);
            }
            Toast.makeText(this.getActivity(), "Dispositivo collegato!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return true;
        } else if (portstatus == -5) {
            progressDialog.dismiss();
            Toast.makeText(this.getActivity(), "Password errata riprovare!", Toast.LENGTH_SHORT).show();
        } else if (portstatus == -1) {
            progressDialog.dismiss();
            Toast.makeText(this.getActivity(), "Dispositivo non trovato!", Toast.LENGTH_SHORT).show();
        } else if (portstatus == -2) {
            return connect();
        } else {
            Toast.makeText(this.getActivity(), "Errore non previsto codice: " + portstatus, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
