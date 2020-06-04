//package com.example.tictac;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.amazonaws.amplify.generated.graphql.ListGamesQuery;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyAdapterGame extends RecyclerView.Adapter<MyAdapterGame.ViewHolder> {
//
//    private List<Game> mData = new ArrayList<>();;
//    private LayoutInflater mInflater;
//    private Context context;
//    DatabaseReference reff;
//    long number = 0;
//
//    // data is passed into the constructor
//    MyAdapterGame(Context context) {
//        this.mInflater = LayoutInflater.from(context);
//        reff = FirebaseDatabase.getInstance().getReference().child("Game");
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                    number=(dataSnapshot.getChildrenCount());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recyclerview_row_game, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//       // ListGamesQuery.Item item = mData.get(position);
//        //holder.txt_name.setText(item.name());
//        //holder.txt_description.setText(item.description());
//        //holder.txt_host.setText(item.player1());;
//        //final String element = item.name();
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//               // Log.e("mój tag",element);
//               // for(int i=0; i<mData.size();i++) {
//                    //if(mData.get(i))
//               // }
//
//                Intent intent =new Intent(context, GameActivity.class);
//                //Toast.makeText(context, "joł",Toast.LENGTH_SHORT).show();
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    // total number of rows
//    @Override
//    public int getItemCount() {
//        return mData.size();
//        //return (int) number;
//    }
//
//    // resets the list with a new set of data
//    public void setItems(Game game) {
//        Log.e("mój items", game.getPlayer1());
////        List<ListGamesQuery.Item> active = items;
////        for(int i=0; i<items.size();i++){
////            if(items.get(i).active() == null)
////                active.remove(items.get(i));
////        }
//        mData.add(game);
//    }
//
//    // stores and recycles views as they are scrolled off screen
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txt_name;
//        TextView txt_description;
//        TextView txt_host;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            context = itemView.getContext();
//            txt_name = itemView.findViewById(R.id.txt_name);
//            txt_description = itemView.findViewById(R.id.txt_description);
//            txt_host = itemView.findViewById(R.id.txt_host);
//        }
//    }
//}