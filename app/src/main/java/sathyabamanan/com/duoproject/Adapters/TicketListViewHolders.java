package sathyabamanan.com.duoproject.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sathyabamanan.com.duoproject.R;

/**
 * Created by baman on 6/25/17.
 */


public class TicketListViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView priority;
    public TextView status;
    public TextView ticketid;
    public TextView subject;
    public TextView type;
    public TextView priority_text;
    public Context context;


    public TicketListViewHolders(View itemView, Context context) {
        super(itemView);
        this.context = context;
        itemView.setOnClickListener(this);

        priority = (TextView) itemView.findViewById(R.id.CTL_priority);
        status = (TextView) itemView.findViewById(R.id.CTL_status);
        ticketid = (TextView) itemView.findViewById(R.id.CTL_ticketid);
        subject = (TextView) itemView.findViewById(R.id.CTL_subject);
        type = (TextView) itemView.findViewById(R.id.CTL_type);
        priority_text = (TextView) itemView.findViewById(R.id.CTL_priority_text);
    }

    @Override
    public void onClick(View view) {
      //  String forcastcode = forcastId.getText().toString();


//        Intent intent = new Intent(view.getContext(), WeatherDetail.class);
//        intent.putExtra("code", String.valueOf(fmodel.ForecastCode));
//
//        view.getContext().startActivity(intent);


    }
}