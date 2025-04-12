import java.io.FileWriter;
import java.io.IOException;

public class ResultExporter {
    public void ExportResults(long[][] times, int[][] rotationsResults, String fileName, int[] sizes) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.append("Size,List A,List B,List C,List D\n");
            for (int i = 0; i < times.length; i++) {
                writer.append(sizes[i] + "," + times[i][0] + "," + times[i][1] + "," + times[i][2] + "," + times[i][3] + "\n");
            }
            if (rotationsResults != null) {
                writer.append("Size,List A,List B,List C,List D\n");
                for (int i = 0; i < rotationsResults.length; i++) {
                    writer.append(sizes[i] + "," + rotationsResults[i][0] + "," + rotationsResults[i][1] + "," + rotationsResults[i][2] + "," + rotationsResults[i][3] + "\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

