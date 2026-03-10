
Tutorial Modul 2
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
= Selama latihan ini, saya memperbaiki masalah UnnecessaryModifier yang dilaporkan oleh pemindai kode PMD. 
Pemindai tersebut menandai beberapa method (create, findAll, findById, update, deleteProductById) di dalam 
interface ProductService.java karena secara eksplisit dideklarasikan dengan modifier public.
Strategi saya untuk memperbaikinya adalah dengan menghapus kata kunci public dari semua deklarasi method di dalam interface tersebut. 
Dalam bahasa Java, method yang dideklarasikan di dalam sebuah interface secara bawaan (implisit) sudah bersifat public dan abstract. 
Oleh karena itu, menuliskan modifier public secara eksplisit adalah hal yang redundant.

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
= Ya, saya rasa implementasi saat ini telah berhasil memenuhi definisi dari Continuous Integration (CI) maupun Continuous Deployment (CD). 
Untuk Continuous Integration, workflow di GitHub Actions dikonfigurasi agar berjalan secara otomatis setiap kali ada perubahan yang push atau pull request
ke repository. Workflow ini mengintegrasikan dan memverifikasi kode dengan menjalankan test suite secara otomatis, mengukur code coverage menggunakan JaCoCo,
serta menganalisis keamanan dan kualitas kode menggunakan OSSF Scorecard dan PMD. Untuk Continuous Deployment, proses ini mengotomatiskan pengiriman dengan
men-deploy kode secara langsung ke lingkungan aplikasi. Dengan menghubungkan repository ke Platform as a Service (PaaS), setiap perubahan kode yang sudah
lulus tes dan merge ke branch main akan pull dan di-deploy secara otomatis tanpa intervensi manual, sehingga mempercepat pengiriman produk perangkat lunak.


Tutorial Modul 3

1) Explain what principles you apply to your project!
= - SRP = Memisahkan CarController menjadi class tersendiri
- LSP = Menghapus extends ProductController dari CarController. CarController mengurus entitas yang benar-benar berbeda, sehingga ia tidak bisa menjadi subclass atau pengganti dari ProductController.
- DIP = Mengubah dependency injection dari yang sebelumnya bergantung pada implementasi konkrit (CarServiceImpl) menjadi bergantung pada abstraksi/interface (CarService).

2) Explain the advantages of applying SOLID principles to your project with examples.
= Perawatan Lebih Mudah (SRP): Dengan memisahkan ProductController dan CarController, developer yang sedang memperbaiki fitur Car tidak akan secara tidak sengaja merusak logika rute Product. Navigasi di dalam kode juga menjadi jauh lebih mudah.

Hierarki Kode yang Logis dan Aman (LSP): Dengan menghapus relasi inheritance (extends ProductController) pada CarController, struktur kode menjadi lebih masuk akal. Keuntungannya, kita terhindar dari bug atau perilaku tak terduga (misalnya CarController tanpa sengaja mewarisi dan menjalankan method milik ProductController yang tidak relevan dengan Car).

Loose Coupling & Fleksibilitas (DIP): Karena CarController sekarang bergantung pada interface CarService, kode kita menjadi tidak terlalu terikat (loosely coupled). Jika di masa depan kita memutuskan untuk membuat implementasi baru, misalnya CarServiceDatabaseImpl yang terhubung ke PostgreSQL, kita tidak perlu mengubah kode CarController sama sekali. Kita hanya perlu menginjeksi implementasi yang baru tersebut.

3) Explain the disadvantages of not applying SOLID principles to your project with examples.
= Kode Rapuh dan Bug yang Tidak Terduga (Pelanggaran LSP): Jika CarController tetap melakukan extends ke ProductController, setiap perubahan yang dilakukan pada method bawaan di ProductController dapat secara tidak terduga mengubah sifat CarController.

Sulit untuk Diuji (Pelanggaran DIP): Jika CarController bergantung langsung pada CarServiceImpl, kode tersebut akan terikat erat dengan logika aslinya. Jika kita ingin menulis Unit Test yang terisolasi untuk CarController, kita tidak akan bisa melakukan mocking pada service layer dengan mudah. Hal ini memaksa kita untuk melakukan integration test (menguji controller dan service secara bersamaan), bukan unit test yang sesungguhnya.

Spaghetti Code (Pelanggaran SRP): Menyatukan banyak controller ke dalam satu file akan membuat file tersebut sangat panjang dan sulit dibaca. Ketika beberapa developer bekerja pada file yang sama untuk fitur yang berbeda, risiko terjadinya merge conflict akan meningkat drastis.


Tutorial 4
1) Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best
   Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this
   TDD flow is useful enough for you or not. If not, explain things that you need to do next time
   you make more tests.
= Berdasarkan pengalaman saya mengerjakan tutorial ini, alur TDD (Red - Green - Refactor) sangat 
membantu untuk mengcover kemungkinan case yang perlu kita buat. TDD memaksa saya untuk memikirkan 
skenario penggunaan, validasi (seperti pada Order dan Payment), serta edge cases sebelum menulis 
implementasi kodenya. Hal ini memberikan rasa aman karena saya tahu kode yang saya tulis di fase 
'Green' sudah pasti teruji. Untuk ke depannya, saya perlu lebih teliti memikirkan unhappy path di 
awal agar test coverage menjadi lebih komprehensif.

2) You have created unit tests in Tutorial. Now reflect whether your tests have successfully
   followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you
   create more tests.
= Saya merasa unit test yang telah dibuat sudah cukup mengikuti prinsip F.I.R.S.T:
- Fast: Tes berjalan sangat cepat karena menggunakan data mock (Mockito) dan tidak bergantung pada
database eksternal.
- Independent: Setiap tes tidak bergantung satu sama lain. Penggunaan anotasi @BeforeEach memastikan
state (seperti list produk atau order) selalu di-reset ke kondisi awal sebelum tes baru dijalankan.
- Repeatable: Tes dapat dijalankan di lingkungan mana pun (lokal maupun CI/CD) dan akan selalu 
menghasilkan output yang konsisten.
- Self-Validating: Tes sudah secara otomatis memvalidasi dirinya sendiri menggunakan assertEquals, 
assertTrue, assertThrows, dll tanpa perlu inspeksi manual.
- Timely: Tes ditulis tepat waktu, yaitu sesaat sebelum kode produksi (implementasi) ditulis, sesuai
dengan alur TDD.
