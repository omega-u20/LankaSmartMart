package com.junkyard.lankasmartmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rvCategories);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem("Groceries", R.drawable.carrot, "#FFF3E0"));
        list.add(new CategoryItem("Household", R.drawable.house, "#E3F2FD"));
        list.add(new CategoryItem("Personal Care", R.drawable.lotion_bottle, "#FCE4EC"));
        list.add(new CategoryItem("Stationery", R.drawable.books, "#F3E5F5"));
        list.add(new CategoryItem("Meats", R.drawable.cut_of_meat, "#FFEBEE"));
        list.add(new CategoryItem("Fruits", R.drawable.red_apple, "#E8F5E9"));

        rv.setAdapter(new CategoryAdapter(list));
    }
}
