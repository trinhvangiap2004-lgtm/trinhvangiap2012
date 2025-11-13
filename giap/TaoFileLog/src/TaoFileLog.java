import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaoFileLog {
    public static void main(String[] args) {
        int soFile = 3000;
        int soDong = 20000;
        Random rand = new Random();

        SimpleDateFormat df = new SimpleDateFormat("dd_MM_yy");
        Calendar cal = Calendar.getInstance();

        File folder = new File("logs");
        if (!folder.exists()) folder.mkdir();

        for (int i = 1; i <= soFile; i++) {
            cal.set(2025, Calendar.JANUARY, 1);
            cal.add(Calendar.DAY_OF_YEAR, i);
            String fileName = "logs/log_" + df.format(cal.getTime()) + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int j = 1; j <= soDong; j++) {
                    // tạo ngẫu nhiên nội dung log
                    String line;
                    if (rand.nextInt(10000) == 0) // tỉ lệ nhỏ chứa từ khóa
                        line = "User " + rand.nextInt(100) + " login by 99 at " + new Date();
                    else
                        line = "Event " + j + " from server " + rand.nextInt(1000);

                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Đã tạo: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("✅ Hoàn tất tạo 3000 file log trong thư mục /logs");
    }
}
