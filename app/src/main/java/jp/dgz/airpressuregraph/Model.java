package jp.dgz.airpressuregraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Model {
    public Map<Long, Double> pressures;

    public Model() {
        pressures = new TreeMap<Long, Double>();
    }

    public void addData(long time, double pressureHpa) {
        pressures.put(time, pressureHpa);
    }

    public void save(File file) throws IOException {
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));

        for (Map.Entry<Long, Double> entry : pressures.entrySet()) {
            os.writeLong(entry.getKey());
            os.writeDouble(entry.getValue());
        }

        os.close();
    }

    public static Model load(File file) throws IOException {
        DataInputStream is = new DataInputStream(new FileInputStream(file));

        Model model = new Model();

        while (is.available() >= 4) {
            long time = is.readLong();
            double pressure = is.readDouble();
            model.addData(time, pressure);
        }
        is.close();

        return model;
    }
}
