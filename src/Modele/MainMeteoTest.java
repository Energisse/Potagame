package Modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainMeteoTest {
    public static void main(String[] args){
        float temperature=0;
        float humidite=0;
        try {
            FileReader fr = new FileReader("./src/donneesmeteo/Juin2023.csv");
            BufferedReader bfr = new BufferedReader(fr);
            String line = null;
            for (int i=0; i<2;i++) {
                line = bfr.readLine(); //on échappe la première ligne correspondant au nom des colonnes et on lit la 2nd qui est le début des valeurs du fichier
            }
            while(line !=null){
                String[] dataline= line.split(";");
                if (dataline[7].contains("mq")){
                    temperature=10;//valeur par défaut
                }
                else{
                    temperature=Float.parseFloat(dataline[7])-(float)273.15;//température en kelvin dans le fichier ramené en °C
                }
                //System.out.println("Temperature ="+temperature+"°C");
                if (dataline[9].contains("mq")){
                    humidite=11;
                }
                else{
                    humidite=Float.parseFloat(dataline[9]);
                }
                //System.out.println("Humidite ="+humidite+"%");
                line=bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
