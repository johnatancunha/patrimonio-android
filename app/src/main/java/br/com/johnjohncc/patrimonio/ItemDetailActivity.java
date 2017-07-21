package br.com.johnjohncc.patrimonio;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.johnjohncc.patrimonio.adapters.ItemAdapter;
import br.com.johnjohncc.patrimonio.entities.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.item_description)
    TextView itemDescription;

    @BindView(R.id.item_acquisition_date)
    TextView itemAcquisitionDate;

    @BindView(R.id.item_price)
    TextView itemPrice;

    @BindView(R.id.item_number_quantity)
    TextView itemNumberQuantity;

    @BindView(R.id.layout_subitems)
    LinearLayout layoutSubitems;

    @BindView(R.id.rl_item_detail)
    RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (getIntent() != null) {
            Item item = (Item) getIntent().getSerializableExtra("item");
            collapsingToolbarLayout.setTitle(item.getTitle());
            toolbar.setTitle(item.getTitle());
            itemDescription.setText(item.getDescription());
            itemAcquisitionDate.setText(item.getAcquisitionDate());
            itemPrice.setText(item.getPrice());
            itemNumberQuantity.setText(item.getNumber() + " / " + item.getQuantity());
            if (item.getSubitems() == null) {
                layoutSubitems.setVisibility(LinearLayout.GONE);
            }
            itemAdapter = new ItemAdapter(item.getSubitems());
            recyclerView.setAdapter(itemAdapter);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
