package br.com.johnjohncc.patrimonio.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.johnjohncc.patrimonio.R;
import br.com.johnjohncc.patrimonio.entities.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WGL003 on 04/07/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String TAG = "ItemAdapter";

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        holder.acquisitionDate.setText(items.get(position).getAcquisitionDate());
        holder.price.setText(items.get(position).getPrice());
        holder.numberQuantity.setText(items.get(position).getNumber() + " / " + items.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void addListItem(Item item, int position){
        items.add(item);
        notifyItemInserted(position);
    }

    public void addList(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    // Método publico chamado para atualziar a lista.
    public void updateList(Item item) {
        insertItem(item);
    }

    // Método responsável por inserir um novo usuário na lista e notificar que há novos itens.
    private void insertItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount());
    }

    // Método responsável por atualizar um usuário já existente na lista.
    private void updateItem(int position, Item item) {
        items.remove(position);
        items.add(position, item);
        notifyItemChanged(position);
    }

    private void removerItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
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
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "onLongClick: " +getAdapterPosition(), Toast.LENGTH_SHORT).show();
            Log.w(TAG, "onLongClick: " + getAdapterPosition() );

            return true;
        }
    }

}
