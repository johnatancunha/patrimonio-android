package br.com.johnjohncc.patrimonio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {

    @BindView(R.id.item_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ButterKnife.bind(this);

//        mToolbar.setSubtitle("Just a subtitle");
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //botão voltar na toolbar

//        setupList();

        ItemFragment itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (itemFragment == null){
            openFragment(itemFragment);
        }

    }

    private void openFragment(final Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

}