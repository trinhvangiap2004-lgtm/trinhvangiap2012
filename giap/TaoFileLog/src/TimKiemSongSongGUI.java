import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class TimKiemSongSongGUI extends JFrame {
    private JTextField txtKeyword, txtDirectory;
    private JButton btnFind, btnBrowse;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblStatus; // hiển thị tổng kết
    private int fileCount = 0;
    private final Set<String> fileDaTimThay = new HashSet<>();

    public TimKiemSongSongGUI() {
        setTitle("Find in Files - Song Song Search");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL TRÊN: Nhập thông tin =====
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel row1 = new JPanel(new BorderLayout(5, 5));
        JPanel row2 = new JPanel(new BorderLayout(5, 5));

        txtKeyword = new JTextField();
        txtDirectory = new JTextField("logs"); // thư mục mặc định
        btnFind = new JButton("Find All");
        btnBrowse = new JButton("Browse...");

        // sự kiện nút "Find All"
        btnFind.addActionListener(e -> timKiemSongSong());

        // sự kiện nút "Browse"
        btnBrowse.addActionListener(e -> chonThuMuc());

        row1.add(new JLabel("Find what: "), BorderLayout.WEST);
        row1.add(txtKeyword, BorderLayout.CENTER);
        row1.add(btnFind, BorderLayout.EAST);

        row2.add(new JLabel("Directory: "), BorderLayout.WEST);
        row2.add(txtDirectory, BorderLayout.CENTER);
        row2.add(btnBrowse, BorderLayout.EAST);

        topPanel.add(row1);
        topPanel.add(row2);
        add(topPanel, BorderLayout.NORTH);

        // ===== BẢNG KẾT QUẢ =====
        String[] columns = {"Tên file", "Dòng", "Nội dung chứa key"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== NHÃN TRẠNG THÁI =====
        lblStatus = new JLabel("Chưa thực hiện tìm kiếm", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblStatus, BorderLayout.SOUTH);
    }

    // ==== CHỌN THƯ MỤC ====
    private void chonThuMuc() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = chooser.getSelectedFile();
            txtDirectory.setText(selectedDir.getAbsolutePath());
        }
    }

    // ==== XỬ LÝ TÌM KIẾM SONG SONG ====
    private void timKiemSongSong() {
        String tuKhoa = txtKeyword.getText().trim();
        String thuMuc = txtDirectory.getText().trim();

        if (tuKhoa.isEmpty() || thuMuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng nhập từ khóa và chọn thư mục log!");
            return;
        }

        model.setRowCount(0);
        fileDaTimThay.clear();
        lblStatus.setText("🔎 Đang tìm kiếm...");

        File folder = new File(thuMuc);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy file log trong thư mục!");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (File file : files) {
            executor.submit(() -> {
                boolean timThayTrongFile = false;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    int dong = 0;
                    while ((line = reader.readLine()) != null) {
                        dong++;
                        if (line.contains(tuKhoa)) {
                            synchronized (model) {
                                model.addRow(new Object[]{file.getName(), dong, line});
                            }
                            timThayTrongFile = true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (timThayTrongFile) {
                    synchronized (fileDaTimThay) {
                        fileDaTimThay.add(file.getName());
                    }
                }
            });
        }

        executor.shutdown();
        new Thread(() -> {
            try {
                executor.awaitTermination(1, TimeUnit.HOURS);
                fileCount = fileDaTimThay.size();
                lblStatus.setText("✅ Hoàn tất tìm kiếm — Tìm thấy " + fileCount + " file chứa từ khóa");
                luuKetQuaRaFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ==== GHI KẾT QUẢ RA FILE ====
    private void luuKetQuaRaFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ketqua.txt"))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String file = model.getValueAt(i, 0).toString();
                String dong = model.getValueAt(i, 1).toString();
                String nd = model.getValueAt(i, 2).toString();
                writer.write("File: " + file + " | Dòng: " + dong + " | Nội dung: " + nd);
                writer.newLine();
            }
            writer.write("-----------------------------------------------------\n");
            writer.write("Tổng số file chứa từ khóa: " + fileCount);
            writer.newLine();
            System.out.println("✅ Đã lưu kết quả ra file ketqua.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimKiemSongSongGUI().setVisible(true));
    }
}
