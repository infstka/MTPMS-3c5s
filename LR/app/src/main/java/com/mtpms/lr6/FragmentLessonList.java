package com.mtpms.lr6;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentLessonList extends Fragment implements OnSelectedRecipeListener {

    private ListAdapter adapter;
    private List<Lesson> recipes;
    FragmentContentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);
        recipes = JSONHelper.importFromJSON(getActivity());
        if(recipes!=null){
            adapter = new ListAdapter(getContext(), (ArrayList<Recipe>) recipes);
            binding.listview.setAdapter(adapter);
            Toast.makeText(getActivity(), "Data restored", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Not opened files", Toast.LENGTH_LONG).show();
        }
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                FragmentLessonDir fragment2 = new FragmentLessonDir();
                ListView listview = binding.listview.findViewById(R.id.listview);
                registerForContextMenu(listview);
                ShowRecipe(position);
            }
        });
        return binding.getRoot();
    }

    private void ShowRecipe(int position) {
        Recipe recipe = (Recipe) recipes.toArray()[position];
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Intent intent = new Intent(getActivity(), MainActivity2.class);
            intent.putExtra("name", recipe.name);
            intent.putExtra("category", recipe.category);
            intent.putExtra("time", recipe.time);
            intent.putExtra("ingredients", recipe.ingredients);
            intent.putExtra("recipe", recipe.recipe);
            startActivity(intent);
        }
    }

    FragmentContent2Binding binding2;

    @Override
    public void onRecipeSelected(int position)
    {
        Recipe recipe = (Recipe) recipes.toArray()[position];


        binding2.nameRecipe.setText(recipe.name);
        binding2.categoryRecipe.setText(recipe.category);
        binding2.timeRecipe.setText(recipe.time);
        binding2.ingredientsRecipe.setText(recipe.ingredients);
        binding2.recipeRecipe.setText(recipe.recipe);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "Удалить");
        menu.add(0, 1, 0, "Изменить");
        menu.add(0, 2, 0, "Просмотреть");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case 0:
                Dialog dialogDel = new Dialog(super.getContext());
                dialogDel.setContentView(R.layout.dialog);
                TextView textViewDel = dialogDel.findViewById(R.id.dialog_info);
                textViewDel.setText("Вы хотите удалить этот рецепт?");
                Button yes = dialogDel.findViewById(R.id.dialog_button_yes);
                Button no = dialogDel.findViewById(R.id.dialog_button_no);
                dialogDel.show();
                yes.setOnClickListener(v->
                {
                    DeleteRecipe(info.position);
                    dialogDel.cancel();
                });
                no.setOnClickListener(v -> dialogDel.cancel());
                break;
            case 1:
                Dialog dialog = new Dialog(super.getContext());
                dialog.setContentView(R.layout.dialog);
                TextView textView = dialog.findViewById(R.id.dialog_info);
                textView.setText("Вы хотите изменить этот рецепт?");
                Button Yes = dialog.findViewById(R.id.dialog_button_yes);
                Button No = dialog.findViewById(R.id.dialog_button_no);
                dialog.show();
                Yes.setOnClickListener(v->
                {
                    ChangeRecipe(info.position);
                    dialog.cancel();
                });
                No.setOnClickListener(v -> dialog.cancel());

                break;
            case 2:
                ShowRecipe(info.position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteRecipe(int position) {
        Recipe recipe = (Recipe) recipes.toArray()[position];
        recipes.remove(recipe);
        JSONHelper.exportToJSON(super.getContext(), recipes);
        adapter.notifyDataSetChanged();
    }

    private void ChangeRecipe(int position) {
        Intent intent = new Intent(getActivity(), MainActivity4.class);
        Recipe recipe = (Recipe) recipes.toArray()[position];
        intent.putExtra("name", recipe.name);
        intent.putExtra("category", recipe.category);
        intent.putExtra("time", recipe.time);
        intent.putExtra("ingredients", recipe.ingredients);
        intent.putExtra("recipe", recipe.recipe);
        intent.putExtra("position", position);

        startActivity(intent);
    }

}