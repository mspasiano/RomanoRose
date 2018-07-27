package it.spasia.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import it.spasia.R;
import it.spasia.dao.DAOCard;
import it.spasia.database.Database;
import it.spasia.model.Card;

public class SettingsCard extends PreferenceActivity {
    MyPreferenceFragment myPreferenceFragment;
    private Card card = null;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreferenceFragment = new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, myPreferenceFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
        if (getIntent().getExtras() == null)
            menu.findItem(R.id.action_delete_card).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Database db = Database.create(getApplicationContext());
        DAOCard daoCard = new DAOCard(db.open());
        switch (item.getItemId()) {
            case R.id.action_save_card: {
                Map<Integer, EditTextPreference> preference = findPreference(myPreferenceFragment);
                if (card == null)
                    card = new Card();
                card.setName(preference.get(R.string.card_name).getText());
                card.setUid(preference.get(R.string.card_uid).getText());
                card.setPassword(preference.get(R.string.card_password).getText());
                card.setTimeout(Integer.valueOf(preference.get(R.string.card_timeout).getText()));
                card.setRele_1(preference.get(R.string.card_rele_1).getText());
                card.setRele_2(preference.get(R.string.card_rele_2).getText());
                card.setRele_3(preference.get(R.string.card_rele_3).getText());
                card.setRele_4(preference.get(R.string.card_rele_4).getText());
                card.setRele_5(preference.get(R.string.card_rele_5).getText());
                card.setRele_6(preference.get(R.string.card_rele_6).getText());
                card.setRele_7(preference.get(R.string.card_rele_7).getText());
                card.setRele_8(preference.get(R.string.card_rele_8).getText());
                card.setRele_9(preference.get(R.string.card_rele_9).getText());
                card.setRele_10(preference.get(R.string.card_rele_10).getText());
                card.setRele_11(preference.get(R.string.card_rele_11).getText());
                card.setRele_12(preference.get(R.string.card_rele_12).getText());
                card.setRele_13(preference.get(R.string.card_rele_13).getText());
                card.setRele_14(preference.get(R.string.card_rele_14).getText());
                card.setRele_15(preference.get(R.string.card_rele_15).getText());
                card.setRele_16(preference.get(R.string.card_rele_16).getText());
                if (card.getId() == null)
                    daoCard.insert(card);
                else
                    daoCard.update(card);
                Toast.makeText(this, "Salvataggio effettuato.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, new Intent());
                finish();
                break;
            }
            case R.id.action_delete_card:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Attenzione");
                alertDialogBuilder.setIcon(R.drawable.ic_icon_warning);
                alertDialogBuilder
                        .setMessage("Confermi la cancellazione della scheda " + card.getName() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (daoCard.delete(card))
                                    Toast.makeText(SettingsCard.this, "Cancellazione effettuata.", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK, new Intent());
                                SettingsCard.this.finish();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private Map<Integer, EditTextPreference> findPreference(PreferenceFragment preferenceFragment) {
        Map<Integer, EditTextPreference> result = new HashMap<Integer, EditTextPreference>();
        result.put(R.string.card_name, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_name)));
        result.put(R.string.card_uid, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_uid)));
        result.put(R.string.card_password, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_password)));
        result.put(R.string.card_timeout, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_timeout)));

        result.put(R.string.card_rele_1, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_1)));
        result.put(R.string.card_rele_2, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_2)));
        result.put(R.string.card_rele_3, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_3)));
        result.put(R.string.card_rele_4, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_4)));
        result.put(R.string.card_rele_5, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_5)));
        result.put(R.string.card_rele_6, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_6)));
        result.put(R.string.card_rele_7, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_7)));
        result.put(R.string.card_rele_8, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_8)));
        result.put(R.string.card_rele_9, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_9)));
        result.put(R.string.card_rele_10, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_10)));
        result.put(R.string.card_rele_11, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_11)));
        result.put(R.string.card_rele_12, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_12)));
        result.put(R.string.card_rele_13, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_13)));
        result.put(R.string.card_rele_14, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_14)));
        result.put(R.string.card_rele_15, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_15)));
        result.put(R.string.card_rele_16, (EditTextPreference) preferenceFragment.findPreference(getResources().getString(R.string.card_rele_16)));
        return result;
    }

    @SuppressLint("ValidFragment")
    public static class MyPreferenceFragment extends PreferenceFragment {
        public MyPreferenceFragment() {
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final SettingsCard activity = (SettingsCard) getActivity();
            addPreferencesFromResource(R.xml.preference_card);
            final PreferenceChangeListener preferenceChangeListener = new PreferenceChangeListener();
            Bundle extras = activity.getIntent().getExtras();

            if (extras != null && extras.containsKey("card"))
                activity.setCard((Card) extras.getSerializable("card"));

            Map<Integer, EditTextPreference> preference = activity.findPreference(this);
            if (activity.getCard() != null) {
                getActivity().setTitle(activity.getCard().getName());
                preference.get(R.string.card_name).setText(activity.getCard().getName());
                preference.get(R.string.card_name).setSummary(activity.getCard().getName());
                preference.get(R.string.card_uid).setText(activity.getCard().getUid());
                preference.get(R.string.card_uid).setSummary(activity.getCard().getUid());
                preference.get(R.string.card_password).setText(activity.getCard().getPassword());
                preference.get(R.string.card_timeout).setText(String.valueOf(activity.getCard().getTimeout()));
                preference.get(R.string.card_timeout).setSummary(String.valueOf(activity.getCard().getTimeout()));

                if (activity.getCard().getRele_1() != null) {
                    preference.get(R.string.card_rele_1).setText(activity.getCard().getRele_1());
                    preference.get(R.string.card_rele_1).setSummary(activity.getCard().getRele_1());
                }
                if (activity.getCard().getRele_2() != null) {
                    preference.get(R.string.card_rele_2).setText(activity.getCard().getRele_2());
                    preference.get(R.string.card_rele_2).setSummary(activity.getCard().getRele_2());
                }
                if (activity.getCard().getRele_3() != null) {
                    preference.get(R.string.card_rele_3).setText(activity.getCard().getRele_3());
                    preference.get(R.string.card_rele_3).setSummary(activity.getCard().getRele_3());
                }
                if (activity.getCard().getRele_4() != null) {
                    preference.get(R.string.card_rele_4).setText(activity.getCard().getRele_4());
                    preference.get(R.string.card_rele_4).setSummary(activity.getCard().getRele_4());
                }
                if (activity.getCard().getRele_5() != null) {
                    preference.get(R.string.card_rele_5).setText(activity.getCard().getRele_5());
                    preference.get(R.string.card_rele_5).setSummary(activity.getCard().getRele_5());
                }
                if (activity.getCard().getRele_6() != null) {
                    preference.get(R.string.card_rele_6).setText(activity.getCard().getRele_6());
                    preference.get(R.string.card_rele_6).setSummary(activity.getCard().getRele_6());
                }
                if (activity.getCard().getRele_7() != null) {
                    preference.get(R.string.card_rele_7).setText(activity.getCard().getRele_7());
                    preference.get(R.string.card_rele_7).setSummary(activity.getCard().getRele_7());
                }
                if (activity.getCard().getRele_8() != null) {
                    preference.get(R.string.card_rele_8).setText(activity.getCard().getRele_8());
                    preference.get(R.string.card_rele_8).setSummary(activity.getCard().getRele_8());
                }
                if (activity.getCard().getRele_9() != null) {
                    preference.get(R.string.card_rele_9).setText(activity.getCard().getRele_9());
                    preference.get(R.string.card_rele_9).setSummary(activity.getCard().getRele_9());
                }
                if (activity.getCard().getRele_10() != null) {
                    preference.get(R.string.card_rele_10).setText(activity.getCard().getRele_10());
                    preference.get(R.string.card_rele_10).setSummary(activity.getCard().getRele_10());
                }
                if (activity.getCard().getRele_11() != null) {
                    preference.get(R.string.card_rele_11).setText(activity.getCard().getRele_11());
                    preference.get(R.string.card_rele_11).setSummary(activity.getCard().getRele_11());
                }
                if (activity.getCard().getRele_12() != null) {
                    preference.get(R.string.card_rele_12).setText(activity.getCard().getRele_12());
                    preference.get(R.string.card_rele_12).setSummary(activity.getCard().getRele_12());
                }
                if (activity.getCard().getRele_13() != null) {
                    preference.get(R.string.card_rele_13).setText(activity.getCard().getRele_13());
                    preference.get(R.string.card_rele_13).setSummary(activity.getCard().getRele_13());
                }
                if (activity.getCard().getRele_14() != null) {
                    preference.get(R.string.card_rele_14).setText(activity.getCard().getRele_14());
                    preference.get(R.string.card_rele_14).setSummary(activity.getCard().getRele_14());
                }
                if (activity.getCard().getRele_15() != null) {
                    preference.get(R.string.card_rele_15).setText(activity.getCard().getRele_15());
                    preference.get(R.string.card_rele_15).setSummary(activity.getCard().getRele_15());
                }
                if (activity.getCard().getRele_16() != null) {
                    preference.get(R.string.card_rele_16).setText(activity.getCard().getRele_16());
                    preference.get(R.string.card_rele_16).setSummary(activity.getCard().getRele_16());
                }
            } else {
                getActivity().setTitle("Creazione nuova scheda");
            }
            preference.get(R.string.card_name).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_uid).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_timeout).setOnPreferenceChangeListener(preferenceChangeListener);

            preference.get(R.string.card_rele_1).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_2).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_3).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_4).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_5).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_6).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_7).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_8).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_9).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_10).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_11).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_12).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_13).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_14).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_15).setOnPreferenceChangeListener(preferenceChangeListener);
            preference.get(R.string.card_rele_16).setOnPreferenceChangeListener(preferenceChangeListener);
        }

        private class PreferenceChangeListener implements Preference.OnPreferenceChangeListener {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(String.valueOf(newValue));
                return true;
            }
        }
    }
}