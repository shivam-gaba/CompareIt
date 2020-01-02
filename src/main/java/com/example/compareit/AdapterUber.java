package com.example.compareit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUber extends RecyclerView.Adapter<AdapterUber.ViewHolder> {

    // adapter adapts the its view and its individual contents


    private ArrayList<UberListClass> uberList;

    AdapterUber(Context context, ArrayList<UberListClass> uberList)
    {
        this.uberList=uberList;
    }


    //viewHolder will have each item in the list
    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvCabName,tvCabFare;
        ImageView ivCabType;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvCabName=itemView.findViewById(R.id.tvCabName);
            tvCabFare=itemView.findViewById(R.id.tvCabFare);
            ivCabType=itemView.findViewById(R.id.ivCabType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),tvCabName.getText().toString()+" : "+tvCabFare.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterUber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cab_list_layout,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final AdapterUber.ViewHolder holder, final int position) {

        class Fare
        {

            @SuppressLint("DefaultLocale")
            private void getFare(double baseFare, double minFare, double perKM, double perMin)
            {
                double distance,minutes;

                distance=uberList.get(position).getDistance();
                minutes=uberList.get(position).getMinutes();

                double totalFare = baseFare+(perKM*distance)+(perMin*minutes);

                if(totalFare>minFare)
                {
                    holder.tvCabFare.setText(String.format("%.2f", totalFare)+" ₹");
                }
                else
                {
                    holder.tvCabFare.setText(minFare+" ₹");
                }
            }
        }

        holder.itemView.setTag(uberList.get(position));
        holder.tvCabName.setText(uberList.get(position).getCabType());
        holder.tvCabFare.setText("XXX $");

        if(uberList.get(position).getCabType().equalsIgnoreCase("uber go"))
        {
            holder.ivCabType.setImageResource(R.drawable.ubergo);
            Fare obj=new Fare();
            obj.getFare(31.50,42,6.30,1.05);
        }
        if(uberList.get(position).getCabType().equalsIgnoreCase("uber moto"))
        {
            holder.ivCabType.setImageResource(R.drawable.ubermoto);
            Fare obj=new Fare();
            obj.getFare(0,0,0,0);
        }
        if(uberList.get(position).getCabType().equalsIgnoreCase("uber hire go"))
        {
            holder.ivCabType.setImageResource(R.drawable.uberhirego);
            Fare obj=new Fare();
            obj.getFare(93,219,0,2.10);
        }
        if(uberList.get(position).getCabType().equalsIgnoreCase("uber hire premier"))
        {
            holder.ivCabType.setImageResource(R.drawable.uberhirepremier);
            Fare obj=new Fare();
            obj.getFare(153,279,0,2.10);
        }
        if(uberList.get(position).getCabType().equalsIgnoreCase("uber premier"))
        {
            holder.ivCabType.setImageResource(R.drawable.uberpremier);
            Fare obj=new Fare();
            obj.getFare(39.90,47.25,7.35,1.05);
        }
    }

    @Override
    public int getItemCount() {
        return uberList.size();
    }
}
