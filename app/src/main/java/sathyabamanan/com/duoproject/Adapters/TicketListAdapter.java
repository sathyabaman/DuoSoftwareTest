package sathyabamanan.com.duoproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sathyabamanan.com.duoproject.ModelObjects.TicketListModel;
import sathyabamanan.com.duoproject.R;

/**
 * Created by baman on 6/25/17.
 */

public class TicketListAdapter extends RecyclerView.Adapter<TicketListViewHolders> {
    private List<TicketListModel> tickets;
    private Context context;

    public TicketListAdapter(Context contet, List<TicketListModel> TicketArrayList) {
        this.tickets = TicketArrayList;
        this.context = contet;
    }

    @Override
    public TicketListViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ticket_list, null);
        TicketListViewHolders rcv = new TicketListViewHolders(layoutView, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TicketListViewHolders holder, int position) {

        switch (tickets.get(position).priority){
            case "normal":
                holder.priority.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGreen));
                break;
            case "high":
                holder.priority.setBackgroundColor(context.getResources().getColor(R.color.colorDarkYellow));
                break;
            case "urgent":
                holder.priority.setBackgroundColor(context.getResources().getColor(R.color.colorDarkRed));
                break;
            default:
                holder.priority.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGreen));
                break;
        }

        holder.status.setText(tickets.get(position).status);
        holder.ticketid.setText(String.valueOf(tickets.get(position).tid));
        holder.subject.setText(tickets.get(position).subject);
        holder.type.setText(tickets.get(position).type);
        holder.priority_text.setText(tickets.get(position).priority);


    }


    @Override
    public int getItemCount() {
        return tickets.size();
    }

}