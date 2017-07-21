package br.com.johnjohncc.patrimonio.adapters;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.johnjohncc.patrimonio.ItemDetailActivity;
import br.com.johnjohncc.patrimonio.R;
import br.com.johnjohncc.patrimonio.entities.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WGL003 on 04/07/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items;
    private List<Item> selectedItems = new ArrayList<>(0);

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String TAG = "ItemAdapter";

    private boolean isLoadingAdded = false;

    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    for (int i = 0; i < items.size(); i++) {
                        if (selectedItems.contains(items.get(i))) {
                            removerItem(i);
                        }
                    }
                    selectedItems.clear();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedItems.clear();
            notifyDataSetChanged();
            mActionMode = null;
        }
    };

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case ITEM :
                itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
                return new ItemViewHolder(itemView);
            case LOADING :
                itemView = layoutInflater.inflate(R.layout.progress_loading, parent, false);
                return  new LoadingViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LOADING){
            ((LoadingViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
        } else {
            if (selectedItems.contains(items.get(position))) holder.itemView.setSelected(true);
            ((ItemViewHolder) holder).title.setText(items.get(position).getTitle());
            ((ItemViewHolder) holder).description.setText(items.get(position).getDescription());
            ((ItemViewHolder) holder).acquisitionDate.setText(items.get(position).getAcquisitionDate());
            ((ItemViewHolder) holder).price.setText(items.get(position).getPrice());
            ((ItemViewHolder) holder).numberQuantity.setText(items.get(position).getNumber() + " / " + items.get(position).getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void addItem(Item item, int position){
        items.add(item);
        notifyItemInserted(position);
    }

    public void addList(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    // Método responsável por inserir um novo item na lista e notificar que há novos itens.
    private void insertItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por atualizar um item já existente na lista.
    private void updateItem(int position, Item item) {
        items.remove(position);
        items.add(position, item);
        notifyItemChanged(position);
    }

    public void removerItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    private void toggleSelection(int position) {
        if(selectedItems.contains(items.get(position))){
            selectedItems.remove(items.get(position));
        } else {
            selectedItems.add(items.get(position));
        }
        notifyItemChanged(position);
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        insertItem(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        Item result = items.get(position);

        if (result != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.item_title)
        protected TextView title;
        @BindView(R.id.item_description)
        protected TextView description;
        @BindView(R.id.item_price)
        protected TextView price;
        @BindView(R.id.item_acquisition_date)
        protected TextView acquisitionDate;
        @BindView(R.id.item_number_quantity)
        protected TextView numberQuantity;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "onClick: " +getAdapterPosition(), Toast.LENGTH_SHORT).show();
            Log.w(TAG, "onClick: " + getAdapterPosition() );
            Item item = items.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
            intent.putExtra("item", item);
            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {

            toggleSelection(getAdapterPosition());

            if (mActionMode == null) {
                mActionMode = v.startActionMode(mActionModeCallback);
            }

            mActionMode.setTitle(String.valueOf(selectedItems.size()) + " selected");

            if (selectedItems.size() > 1) {
                mActionMode.getMenu().removeItem(R.id.item_edit);
            }

            if (selectedItems.size() < 1) {
                mActionMode.finish();
            }

            return true;

        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar2)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
