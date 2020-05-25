package br.com.adolfobocchi.videoplay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adolfobocchi.videoplay.R;
import br.com.adolfobocchi.videoplay.adapter.AdapterVideo;
import br.com.adolfobocchi.videoplay.listener.RecyclerItemClickListener;
import br.com.adolfobocchi.videoplay.model.Item;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private AdapterVideo adapterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("VideoPlayer");
        setSupportActionBar( toolbar );


        //Recupera vídeos
        carregaVideos();

        //Configura métodos para SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos( query );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                carregaVideos();
                configurarRecyclerView();
            }
        });

    }

    private void carregaVideos() {
        videos.add(new Item("https://www.youtube.com/embed/lXzren74H18", "POCOYO - Pintinho amarelinho","https://i.pinimg.com/originals/99/d4/33/99d4336da8e3c599102caf91ac8cb512.jpg"));
        videos.add(new Item("https://www.youtube.com/embed/YJsFrk-RvLU", "POCOYO - Coelho da páscoa","https://i.ytimg.com/vi/eef1s0_oD6Y/maxresdefault.jpg"));
        videos.add(new Item("https://www.youtube.com/embed/qehQvsOJs2M", "POCOYO - Natal marciano","https://i.ytimg.com/vi/YuDlW2mgT9I/hqdefault.jpg"));
        configurarRecyclerView();
    }

    public void configurarRecyclerView(){
        adapterVideo = new AdapterVideo(videos, this);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter( adapterVideo );

        //Configura evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Item video = videos.get(position);
                                String urlVideo = video.url;

                                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                                i.putExtra("urlVideo", urlVideo );
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    private void recuperarVideos(String pesquisa) {

       videos = videos.stream()
                .filter(item -> item.titulo.contains(pesquisa) )
                .collect(Collectors.<Item>toList());
        configurarRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem( item );

        return true;
    }
}
