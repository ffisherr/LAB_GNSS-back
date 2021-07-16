scp -C -i C:\\Users\\andre\\.ssh\\id_rsa_1 -r C:\\Work\\BMSTU\\GNSS\\lab-gnss\\target\\lab-gnss-0.0.1-SNAPSHOT.jar anpopov@lab-gnss.bmstu.ru:/home/anpopov

ssh lab-gnss "sudo systemctl stop lab-gnss-app;
sudo mv /home/anpopov/lab-gnss-0.0.1-SNAPSHOT.jar /opt/lab-gnss/back/;
sudo systemctl start lab-gnss-app"
