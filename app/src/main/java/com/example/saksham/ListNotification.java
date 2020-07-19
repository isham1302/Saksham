package com.example.saksham;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListNotification extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterNotification adapterNotification;
    List<String> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);

        studentList= new ArrayList<>();

        recyclerView= findViewById(R.id.recyclerView_notification);
        adapterNotification= new AdapterNotification(studentList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterNotification);

        studentList.add("Payal Sharma");
        studentList.add("Jolly Sharma");
        studentList.add("Niharika Srivastava");
        studentList.add("Saloni Soni");
        studentList.add("Harshita Meena");
        studentList.add("Diya Sinha");
        studentList.add("Aditi Mishra");
        studentList.add("Heti Patel");
        studentList.add("Shreya Mishra");

        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    String deletedcard= null;
    List<String> acceptRequest= new ArrayList<String>();
    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position= viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    deletedcard=studentList.get(position);
                    studentList.remove(position);
                    adapterNotification.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, (CharSequence) deletedcard,Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            studentList.add(position,deletedcard);
                            adapterNotification.notifyItemInserted(position);
                        }
                    }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    final String studentName= studentList.get(position);
                    acceptRequest.add(studentName);
                    studentList.remove(position);
                    adapterNotification.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, (CharSequence) studentName+",Accepted.",Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    acceptRequest.remove(acceptRequest.lastIndexOf(studentName));
                                    studentList.add(position, studentName);
                                    adapterNotification.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(ListNotification.this, R.color.Red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(ListNotification.this, R.color.Green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_check_circle_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}