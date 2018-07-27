package it.spasia.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import it.spasia.R;
import it.spasia.dao.DAOCard;
import it.spasia.database.Database;
import it.spasia.model.Card;

public class Settings extends PreferenceActivity {
    MyPreferenceFragment myPreferenceFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreferenceFragment = new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, myPreferenceFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_OK, new Intent());
    }

    private void importDB() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "it.spasia"
                        + "//databases//" + Database.DATABASE_NAME;
                String backupDBPath  = "/" + Database.DATABASE_NAME;
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                setResult(RESULT_OK, new Intent());
                onResume();
                Toast.makeText(getBaseContext(), "Ripristino dei dati eseguito da: " + backupDB.toString(),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "it.spasia" + "//databases//" + Database.DATABASE_NAME;
                String backupDBPath  = "/" + Database.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), "Backup eseguito in: " + backupDB.toString(),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_card:
                Intent intent = new Intent(this, SettingsCard.class);
                startActivityForResult(intent, RESULT_CANCELED);
                break;
            case R.id.action_export_db:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Attenzione");
                alertDialogBuilder.setIcon(R.drawable.ic_icon_warning);
                alertDialogBuilder
                        .setMessage("Confermi il backup dei dati sulla scheda SD?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                exportDB();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.action_import_db:
                AlertDialog.Builder alertDialogBuilderImport = new AlertDialog.Builder(this);
                alertDialogBuilderImport.setTitle("Attenzione");
                alertDialogBuilderImport.setIcon(R.drawable.ic_icon_warning);
                alertDialogBuilderImport
                        .setMessage("Confermi il ripristino dei dati dalla scheda SD?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                importDB();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialogImport = alertDialogBuilderImport.create();
                alertDialogImport.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = Database.create(getApplicationContext());
        DAOCard daoCard = new DAOCard(db.open());
        PreferenceScreen preferenceScreen = myPreferenceFragment.getPreferenceScreen();
        preferenceScreen.removeAll();
        for (Card card : daoCard.findAll()) {
            Preference preference = new Preference(preferenceScreen.getContext(), null, R.style.SettingsListTheme);
            preference.setTitle(card.getName());
            preference.setSummary(card.getUid());
            preference.setIcon(R.drawable.ic_icon_card);
            preference.setKey(String.valueOf(card.getId()));
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Card card = daoCard.findById(Long.valueOf(preference.getKey()));
                    Intent intent = new Intent(Settings.this, SettingsCard.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("card", card);
                    intent.putExtras(bundle);
                    Settings.this.startActivityForResult(intent, RESULT_CANCELED);
                    return false;
                }
            });
            preferenceScreen.addPreference(preference);
        }

    }

    @SuppressLint("ValidFragment")
    public static class MyPreferenceFragment extends PreferenceFragment {
        public MyPreferenceFragment() {
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Context activityContext = getActivity();
            addPreferencesFromResource(R.xml.preference);
        }
    }
}