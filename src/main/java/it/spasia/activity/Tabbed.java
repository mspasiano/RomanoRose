package it.spasia.activity;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import it.spasia.R;
import it.spasia.dao.DAOCard;
import it.spasia.database.Database;
import it.spasia.fragment.CardFragment;
import it.spasia.fragment.CardFragmentPagerAdapter;
import it.spasia.model.Card;

public class Tabbed extends FragmentActivity implements TabLayout.OnTabSelectedListener {

    ActionBar mActionBar;
    private List<Card> cards;
    private DAOCard daoCard;
    CardFragmentPagerAdapter adapter;
    ViewPager viewPager;

    public List<Card> getCards() {
        return cards;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database db = Database.create(getApplicationContext());
        daoCard = new DAOCard(db.open());
        cards = daoCard.findAll();

        setContentView(R.layout.tabbed);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new CardFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        addTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            finish();
            startActivity(getIntent());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(Tabbed.this, Settings.class);
                Tabbed.this.startActivityForResult(intent, RESULT_CANCELED);
                break;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void addTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.removeAllTabs();
        for (int i = 0; i < cards.size(); i++) {
            tabLayout.addTab(
                    tabLayout.newTab()
                            .setText(cards.get(i).getName())
                            .setIcon(R.drawable.ic_icon_card)
                            .setTag(cards.get(i))
            );
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Object o = adapter.instantiateItem(viewPager, tab.getPosition());
        if (o instanceof CardFragment) {
            ((CardFragment)o).disconnect();
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
