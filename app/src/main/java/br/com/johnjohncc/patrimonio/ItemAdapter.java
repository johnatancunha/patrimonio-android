package br.com.johnjohncc.patrimonio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.johnjohncc.patrimonio.entities.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WGL003 on 04/07/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        protected TextView title;
        @BindView(R.id.item_description)
        protected TextView description;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
