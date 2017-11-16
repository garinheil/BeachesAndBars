package heil1gd.cps496.cmich.edu.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BeachFavsActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView listFavorites;
    TextView header;
    String[] beach_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        header = findViewById(R.id.textFavsHeader);
        header.setText(getString(R.string.beach_favs_header));

        beach_favorites = getResources().getStringArray(R.array.beach_favorites);
        listFavorites = findViewById(R.id.listFavorites);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beach_favorites);
        listFavorites.setAdapter(adapter);
        listFavorites.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
