package co.edu.udea.pi.sjm.petted.vista.mascota;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Mascota;

public class MascotaActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private Mascota mascota;

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    List<Integer> iconos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        iconos = new ArrayList<>();
        iconos.add(R.mipmap.ic_assignment_turned_in_gray);
        iconos.add(R.mipmap.ic_assignment_gray);
        iconos.add(R.mipmap.ic_medical_gray);
        iconos.add(R.mipmap.ic_vacuna_gray);

        iconos.add(R.mipmap.ic_assignment_turned_in_white);
        iconos.add(R.mipmap.ic_assignment_white);
        iconos.add(R.mipmap.ic_medical_white);
        iconos.add(R.mipmap.ic_vacuna_white);


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(iconos.get(i))
                            .setTabListener(this));
        }

        mascota = new Mascota();
        mascota.setNombre(this.getIntent().getStringExtra("nombre"));
        mascota.setTipo(this.getIntent().getStringExtra("tipo"));
        mascota.setRaza(this.getIntent().getStringExtra("raza"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mascota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

        }
        switch (tab.getPosition()) {
            case 0:
                setTitle("Información");
                tab.setIcon(iconos.get(4));
                break;
            case 1:
                setTitle("Citas");
                tab.setIcon(iconos.get(5));
                break;
            case 2:
                setTitle("Medicamentos");
                tab.setIcon(iconos.get(6));
                break;
            case 3:
                setTitle("Vacunas");
                tab.setIcon(iconos.get(7));
                break;
        }
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        switch (tab.getPosition()) {
            case 0:
                tab.setIcon(iconos.get(0));
                break;
            case 1:
                tab.setIcon(iconos.get(1));
                break;
            case 2:
                tab.setIcon(iconos.get(2));
                break;
            case 3:
                tab.setIcon(iconos.get(3));
                break;
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return MascotaInformacionFragment.newInstance();
                case 1:
                    return MascotaCitasFragment.newInstance();
                case 2:
                    return MascotaMedicamentosFragment.newInstance();
                case 3:
                    return MascotaVacunasFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.titulo_informacion).toUpperCase(l);
                case 1:
                    return getString(R.string.titulo_citas).toUpperCase(l);
                case 2:
                    return getString(R.string.titulo_medicamentos).toUpperCase(l);
                case 3:
                    return getString(R.string.titulo_vacunas).toUpperCase(l);
            }
            return null;
        }
    }

}
