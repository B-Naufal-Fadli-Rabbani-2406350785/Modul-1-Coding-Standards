
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