package info.androidhive.tac.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.tac.R;
import info.androidhive.tac.activity.LoginActivity;
import info.androidhive.tac.activity.MainActivity;
import info.androidhive.tac.activity.PKO_Activity;
import info.androidhive.tac.model.Klient;

import static android.R.attr.width;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    static List<Klient> dbList;
    static Context context;

    public RecyclerAdapter(Context context, List<Klient> dbList) {
        this.dbList = new ArrayList<Klient>();
        this.context = context;
        this.dbList = dbList;

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.name.setText(dbList.get(position).getName());
        holder.id.setText(String.valueOf(dbList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id, name;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            id = (TextView)itemLayoutView.findViewById(R.id.klient_id);
            name = (TextView) itemLayoutView.findViewById(R.id.klient_name);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(context, v, Gravity.CENTER);
            popupMenu.inflate(R.menu.klient_menu);
            Object menuHelper;
            Class[] argTypes;
            try {
                Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                fMenuHelper.setAccessible(true);
                menuHelper = fMenuHelper.get(popupMenu);
                argTypes = new Class[] { boolean.class };
                menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
            } catch (Exception e) {
                return;
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {

                                case R.id.menu1:
                                    // Launching the login activity
                                    Intent intent = new Intent(context, PKO_Activity.class);
                                    intent.putExtra("name", name.getText().toString());
                                    intent.putExtra("id", id.getText().toString());
                                    context.startActivity(intent);
                                    //context.finish();
                                    return true;
                                case R.id.menu2:

                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

                @Override
                public void onDismiss(PopupMenu menu) {
                   // Toast.makeText(context, "onDismiss",
                   //         Toast.LENGTH_SHORT).show();
                }
            });
            popupMenu.show();
           // Intent intent = new Intent(context,DetailsActivity.class);

           // Bundle extras = new Bundle();
           // extras.putInt("position",getAdapterPosition());
            //intent.putExtras(extras);

            //int i=getAdapterPosition();
            //intent.putExtra("position", getAdapterPosition());
            //context.startActivity(intent);
            //Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }
    }
}
