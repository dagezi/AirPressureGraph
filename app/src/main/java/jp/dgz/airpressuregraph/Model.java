package jp.dgz.airpressuregraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));

        os.writeInt(pressures.size());

        for (Map.Entry<Long, Double> entry : pressures.entrySet()) {
            os.writeLong(entry.getKey());
            os.writeDouble(entry.getValue());
        }

        os.close();
    }

    public static Model load(File file) throws IOException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));

        int count = is.readInt();
        Model model = new Model();

        while (count > 0) {
            long time = is.readLong();
            double pressure = is.readDouble();
            model.addData(time, pressure);
        }
        is.close();

        return model;
    }
}
