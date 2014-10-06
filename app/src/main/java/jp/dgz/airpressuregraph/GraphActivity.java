package jp.dgz.airpressuregraph;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;


public class GraphActivity extends Activity {

    public static final String PRESSURE_DATA_FILE = "pressure.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        GraphView graphView = (GraphView) findViewById(R.id.graph_view);

        File file = new File(getFilesDir(), PRESSURE_DATA_FILE);

        Model model = new Model();
        addDummy(model);

        try {
            model = Model.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphView.setModel(model);
    }

    private void addDummy(Model model) {
        for (int i = -10; i <= 0; i++) {
            model.addData(System.currentTimeMillis() + i * 5 * 60 * 1000, 980 - i * 5);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
