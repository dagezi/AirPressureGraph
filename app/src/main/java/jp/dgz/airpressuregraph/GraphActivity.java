package jp.dgz.airpressuregraph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class GraphActivity extends Activity {

    public static final String PRESSURE_DATA_FILE = "pressure.data";
    private TextView sampleCountView;
    private GraphView graphView;
    private File pressureDataFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graphView = (GraphView) findViewById(R.id.graph_view);
        sampleCountView = (TextView) findViewById(R.id.sample_count_view);

        pressureDataFile = new File(getFilesDir(), PRESSURE_DATA_FILE);

        Model model = new Model();
        addDummy(model);
        graphView.setModel(model);

        Intent sensingService = new Intent(this, SensingService.class);
        startService(sensingService);
    }

    private void addDummy(Model model) {
        for (int i = -10; i <= 0; i++) {
            model.addData(System.currentTimeMillis() + i * 5 * 60 * 1000, 980 - i * 5);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Model model = Model.load(pressureDataFile);
            graphView.setModel(model);
            sampleCountView.setText("Sample: " + model.pressures.size());
        } catch (IOException e) {
            e.printStackTrace();
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
