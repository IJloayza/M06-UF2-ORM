package com.iticbcn.loayzaignacio.Classes;

import java.io.IOException;

import com.iticbcn.loayzaignacio.CRUD.CompanyiaCRUD;
import com.iticbcn.loayzaignacio.CRUD.EstacioCRUD;
import com.iticbcn.loayzaignacio.CRUD.HorariCRUD;
import com.iticbcn.loayzaignacio.CRUD.TrajecteCRUD;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.iticbcn.loayzaignacio.DAO.GenericDAO;

public class GestioDBHR {
//Com veurem, aquesta booleana controla si volem sortir de l'aplicació.
    private static boolean sortirapp = false;
        public static void main(String[] args) {
        try{
            // Conectar amb MariaDB
                while (sortirapp == false) {
                    MenuOptions();
                }
        } catch (Exception e) {
            System.err.println("Error al crear connexió, dades inválides " + e.getMessage());
        }finally{
            GenericDAO.endSession();
        }
    }

    public static void MenuOptions() throws InterruptedException, IOException {

        Terminal terminal = TerminalBuilder.builder().system(true).build();
        String message = "";
        message = "==================";
        printScreen(terminal, message);

        message = "CONSULTA BD Renfe";
        printScreen(terminal, message);

        message = "==================";
        printScreen(terminal, message);


        message = "OPCIONS";
        printScreen(terminal, message);

        message = "1. CONSULTAR TOTES LES DADES";
        printScreen(terminal, message);

        message = "2. INSERIR NOU REGISTRE";
        printScreen(terminal, message);

        message = "3. MODIFICAR UN REGISTRE";
        printScreen(terminal, message);

        message = "4. ESBORRAR UN REGISTRE";
        printScreen(terminal, message);

        message = "5. MOSTRAR QUANTS TRAJECTES OFEREIX CADA COMPANYIA";
        printScreen(terminal, message);

        message = "9. SORTIR";
        printScreen(terminal, message);


        message = "Introdueix l'opcio tot seguit >> ";
        printScreen(terminal, message);

        int opcio = Integer.parseInt(Std.readLine());

        switch(opcio) {
            case 1:
                //Preguntem de quina taula volem mostrar
                MenuSelect();
                break;
            case 2:
                MenuInsert();
                break;
            case 3:
                MenuUpdate();
                break;
            case 4:
                MenuDelete();
                break;
            case 5:
                MenuShowCompWithTrajects();
                break;
            case 9:
                //sortim
                System.out.println("Adéu!!");
                sortirapp = true;
                break;
            default:
                System.out.print("Opcio no vàlida");
                MenuOptions();
        }
    
    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(7);
        }
        System.out.println();
    }

    public static void MenuSelect(){
        //Mostrar totes les dades de TRAINS 
        boolean dispOptions = true;
        while (dispOptions) {

            System.out.println("De quina taula vols mostrar les dades?");
            System.out.println("1. ESTACIO");
            System.out.println("2. HORARI");
            System.out.println("3. TRAJECTE");
            System.out.println("4. COMPANYIA");
            System.out.println("5. SORTIR");

            int opcio = Integer.parseInt(Std.readLine());
            switch (opcio) {
                case 1:
                    EstacioCRUD.mostrarEstacions();

                    break;
                case 2:
                    HorariCRUD.mostrarHoraris();
                    break;
                case 3:
                    TrajecteCRUD.mostrarTrajectes();
                    break;
                case 4:
                    CompanyiaCRUD.mostrarCompanyies();
                    break;
                case 5:
                    dispOptions = false;
                    break;
                default:
                    System.out.print("Opcio no vàlida");
                    MenuSelect();
                break;
            }

        }
    }

    public static void MenuInsert(){
        boolean insertMore = true;

        while (insertMore) {
            System.out.println("A quina taula vols afegir un registre?");
            System.out.println("1. ESTACIO");
            System.out.println("2. HORARI");
            System.out.println("3. TRAJECTE");
            System.out.println("4. COMPANYIA");
            System.out.println("5. SORTIR");

            int opcio = Integer.parseInt(Std.readLine());
            switch (opcio) {
                case 1:
                    EstacioCRUD.creaEstacio();
                    break;
                case 2:
                    HorariCRUD.creaHorari();
                    break;
                case 3:
                    TrajecteCRUD.creaTrajecte();
                    break;
                case 4:
                    CompanyiaCRUD.creaCompanyia();
                    break;
                case 5:
                    insertMore = false;
                    break;
            
                default:
                    System.out.print("Opcio no vàlida");
                    MenuSelect();
                break;
            }

        }
                            
    }

    public static void MenuUpdate() {
        boolean updateMore = true;
        while(updateMore){
            System.out.println("De quina taula vols canviar el registre?");
            System.out.println("1. ESTACIO");
            System.out.println("2. HORARI");
            System.out.println("3. TRAJECTE");
            System.out.println("4. COMPANYIA");
            System.out.println("5. SORTIR");

            int opcio = Integer.parseInt(Std.readLine());
            switch (opcio) {
                case 1:
                    EstacioCRUD.canviaEstacio();
                    break;
                case 2:
                    HorariCRUD.canviaHorari();
                    break;
                case 3:
                    TrajecteCRUD.canviaTrajecte();
                    break;
                case 4:
                    CompanyiaCRUD.canviaCompanyia();
                    break;
                case 5:
                    updateMore = false;
                    break;
            
                default:
                    System.out.print("Opcio no vàlida");
                    MenuUpdate();
                break;
            }
        }
    }

    public static void MenuDelete() {
        boolean updateMore = true;
        while(updateMore){
            System.out.println("De quina taula vols eliminar un registre?");
            System.out.println("1. ESTACIO");
            System.out.println("2. HORARI");
            System.out.println("3. TRAJECTE");
            System.out.println("4. COMPANYIA");
            System.out.println("5. SORTIR");

            int opcio = Integer.parseInt(Std.readLine());
            switch (opcio) {
                case 1:
                    EstacioCRUD.eliminarEstacio();
                    break;
                case 2:
                    HorariCRUD.eliminarHorari();
                    break;
                case 3:
                    TrajecteCRUD.eliminarTrajecte();
                    break;
                case 4:
                    CompanyiaCRUD.eliminarCompanyia();
                    break;
                case 5:
                    updateMore = false;
                    break;
            
                default:
                    System.out.print("Opcio no vàlida");
                    MenuUpdate();
                break;
            }
        }
    }

    public static void MenuShowCompWithTrajects() {
      List<Object[]> objects = GenericDAO.groupComp();
      for (Object[] object : objects) {
          for (int i = 0; i < object.length; i++) {
            if (i % 2 == 0) {
                System.out.println("Estació: " + object[i] + " Numero de Trajectes: " + object[i+1]);                
            }
          }
      }
    }

    public static LocalTime demanaHora(){
        boolean horaValida = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        while (horaValida) {
            System.out.println("Introdueix l'hora de sortida en format HH:mm 24 hores(c Cancelar)");
            String hora = Std.readLine();
            if(hora.toLowerCase().matches("[c]")){
                break;
            }
            try {
                LocalTime time = LocalTime.parse(hora, formatter);
                return time;
            } catch (DateTimeException e) {
                System.out.println("Format d'hora no vàlid");
            }
        }
        return null;
    } 
}