ELASTICSEARCH 📘 1. Giới thiệu

Chương trình mang tên "Tìm Kiếm Tệp Nhanh" là một ứng dụng desktop viết bằng Java, giúp người dùng tìm kiếm một từ khóa cụ thể trong nhiều tệp văn bản nằm trong thư mục đã chọn. Ứng dụng có giao diện đồ họa Swing, cho phép nhập từ khóa, chọn thư mục và xem kết quả tìm kiếm theo thời gian thực.

Ứng dụng hỗ trợ tìm kiếm song song đa luồng, đảm bảo tốc độ cao ngay cả với thư mục chứa nhiều tệp. Tất cả kết quả tìm kiếm đều được tự động lưu vào tệp ketqua.txt trong thư mục được chọn.

⚙️ 2. Các tính năng chính

Ứng dụng có giao diện đơn giản, thân thiện, giúp người dùng dễ dàng nhập từ khóa và chọn thư mục. Người dùng có thể tìm bất kỳ từ khóa nào trong các tệp .txt, không phân biệt chữ hoa chữ thường. Chương trình sử dụng ExecutorService kết hợp với Semaphore để xử lý nhiều tệp đồng thời, tăng hiệu suất tìm kiếm. Mọi kết quả khớp đều được ghi lại tự động vào tệp ketqua.txt. Tệp này cũng được loại trừ khỏi quá trình tìm kiếm để tránh đệ quy.

Ngoài ra, chương trình hiển thị tóm tắt tổng số dòng tìm thấy, số tệp chứa từ khóa và thời gian thực hiện. Nó xử lý các lỗi đọc tệp một cách nhẹ nhàng, không làm gián đoạn toàn bộ quá trình, và tìm kiếm chạy trong một luồng nền, đảm bảo GUI luôn phản hồi.

🏗️ 3. Cấu trúc chương trình

Lớp chính là TimKiemLogSongSong_GUI, mở rộng từ JFrame. Trong đó có các thành phần chính: JTextField để nhập từ khóa và đường dẫn thư mục, JButton để duyệt thư mục và bắt đầu tìm kiếm, JTextArea để hiển thị kết quả, JLabel để hiển thị tóm tắt tìm kiếm, và JFileChooser để chọn thư mục. Đồng thời, chương trình sử dụng ExecutorService để quản lý các luồng song song và Semaphore để giới hạn truy cập I/O cùng lúc.

🧠 4. Cách thức hoạt động

Người dùng nhập từ khóa và chọn thư mục chứa các tệp văn bản. Khi nhấn “Tìm tất cả”, chương trình khởi chạy một luồng nền để GUI không bị đóng băng. Nó liệt kê tất cả các tệp .txt trong thư mục (ngoại trừ ketqua.txt) và khởi tạo nhiều luồng để quét các tệp đồng thời.

Mỗi dòng chứa từ khóa sẽ được ghi lại bao gồm tên tệp, số dòng và nội dung dòng. Kết quả được hiển thị trên giao diện và ghi vào tệp ketqua.txt. Sau khi hoàn tất, chương trình hiển thị tóm tắt: số dòng tìm thấy, số tệp chứa từ khóa và thời gian thực hiện.

Ví dụ: “Đã tìm thấy 135 dòng trong 12 tệp. Thời gian: 3,47 giây. Kết quả lưu tại: D:...\ketqua.txt”.

💻 5. Cách sử dụng

Đầu tiên, biên dịch và chạy chương trình:

javac TimKiemLogSongSong_GUI.java
java TimKiemLogSongSong_GUI


Nhập từ khóa muốn tìm kiếm, ví dụ “doanh thu” hay “thu nhập”, sau đó chọn thư mục chứa các tệp văn bản. Nhấn “Tìm tất cả” để chương trình quét các tệp và hiển thị kết quả trong GUI. Mọi kết quả sẽ được tự động lưu vào ketqua.txt để người dùng mở lại sau.

🧮 6. Ví dụ kết quả

Nếu tìm từ khóa “doanh nghiệp” trong thư mục D:\T540p\Document\CV - thuế TNCN, GUI có thể hiển thị:

baocao1.txt - Dòng 23: Doanh thu năm 2023 tăng 15%
baocao2.txt - Dòng 88: Lợi nhuận doanh nghiệp giảm nhẹ
thueTNCN.txt - Dòng 45: Thu nhập từ hoạt động doanh nghiệp


Tóm tắt:

Đã tìm thấy 135 dòng trong 12 tệp. Thời gian: 3,47 giây. Kết quả lưu tại D:\T540p\Document\CV - thuế TNCN\ketqua.txt


Trong tệp ketqua.txt, nội dung giống với GUI hiển thị.

🧾 7. Thông số kỹ thuật

Chương trình sử dụng Java (JDK 8 trở lên), thư viện Swing cho GUI, ExecutorService và Semaphore để xử lý đa luồng. Đầu vào là từ khóa và đường dẫn thư mục, đầu ra là GUI và tệp ketqua.txt. Hỗ trợ tệp .txt với mã hóa UTF-8.

🧾 8. Các phần mở rộng

Có thể cải tiến chương trình bằng cách thêm thanh tiến trình %, tìm kiếm đệ quy trong thư mục con, giao diện tối, lưu lịch sử từ khóa, lọc theo loại tệp (.log, .csv), hoặc tìm kiếm nâng cao bằng biểu thức chính quy.

🏁 9. Kết luận

Ứng dụng “Tìm Kiếm Tệp Nhanh” kết hợp hiệu quả giữa xử lý song song trong Java và giao diện Swing, giúp tìm kiếm văn bản trong nhiều tệp thực tế một cách nhanh chóng, trực quan và mượt mà..
