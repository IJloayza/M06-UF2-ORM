package com.iticbcn.loayzaignacio.CRUD;

import com.iticbcn.loayzaignacio.Classes.GestioDBHR;
import com.iticbcn.loayzaignacio.Classes.Horari;
import com.iticbcn.loayzaignacio.Classes.Std;
import com.iticbcn.loayzaignacio.Classes.Trajecte;
import com.iticbcn.loayzaignacio.DAO.HorariDAO;
import org.hibernate.SessionFactory;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HorariCRUD {
    protected static HorariDAO horariDAO;
    public HorariCRUD(SessionFactory session) {
        if (horariDAO == null) {
            horariDAO = new HorariDAO(session);
        }
    }

    public static void creaHorari(){
        boolean seguir = true;
        if (TrajecteCRUD.trajecteDAO.findAll().isEmpty()) {
            System.out.println("No hi ha trajectes disponibles per crear un horari");
            seguir = false;
        }
        while(seguir) {
            //Uso de LocalTime per evitar errors de formateig al moment d'introdiur l'hora
            //Preguntar l'hora de sortida
            LocalTime horaSortida = GestioDBHR.demanaHora();
            if(horaSortida == null){
                seguir = false;
                break;
            }
            //Preguntar l'hora d'arribada
            LocalTime horaArribada = GestioDBHR.demanaHora();
            if(horaArribada == null){
                seguir = false;
                break;
            }
            //Preguntar el trajecte
            List<Trajecte> trajectes = TrajecteCRUD.trajecteDAO.findAll();
            for (Trajecte trajecte : trajectes) {
                System.out.println(trajecte);
            }
            System.out.println("Tria un número de trajecte que tindrà aquest horari");
            int idTrajecte = Integer.parseInt(Std.readLine());
            Trajecte trajecte =(Trajecte) TrajecteCRUD.trajecteDAO.findById(idTrajecte);
            Horari horari = new Horari(horaSortida.toString(), horaArribada.toString(), trajecte);
            horariDAO.save(horari);
        }
    }

    public static void canviaHorari(){
        boolean seguir = true;
        while(seguir){
            List<Horari> horaris = horariDAO.findAll();
            if (horaris.isEmpty()) {
                System.out.println("No hi ha horaris disponibles per modificar");
                seguir = false;
            }else{
                for (Horari horari : horaris) {
                    System.out.println(horari);
                }
                System.out.println("Tria un número d'horari per modificar (c Cancelar)");
                String choice = Std.readLine();
                if(choice.toLowerCase().matches("[c]")){
                    seguir = false;
                    break;
                }
                int idHorari = Integer.parseInt(choice);
                Horari horari =(Horari) horariDAO.findById(idHorari);
                System.out.println("Que vols canviar d'aquest Horari? (hs HoraSortida, ha HoraArribada, t Trajecte)");
                switch (Std.readLine().toLowerCase()) {
                    case "hs", "horasortida":
                        LocalTime horaSortida = GestioDBHR.demanaHora();
                        horari.setHourDepart(horaSortida.toString());
                        horariDAO.update(horari);
                        break;
                    case "ha", "horaarribada":
                        LocalTime horaArribada = GestioDBHR.demanaHora();
                        horari.setHourArribe(horaArribada.toString());
                        horariDAO.update(horari);
                        break;
                    case "t", "trajecte":
                        List<Trajecte> trajectes = TrajecteCRUD.trajecteDAO.findAll();
                        for (Trajecte trajecte : trajectes) {
                            System.out.println(trajecte);
                        }
                        System.out.println("Tria un número de trajecte per aquest horari");
                        int idTrajecte = Integer.parseInt(Std.readLine());
                        Trajecte trajecte =(Trajecte) TrajecteCRUD.trajecteDAO.findById(idTrajecte);
                        horari.setTrajecte(trajecte);
                        horariDAO.update(horari);
                        break;

                    default:
                        break;
                }
                System.out.println("Vols modificar més Horaris?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static void eliminarHorari(){
        boolean seguir = true;
        while(seguir){
            List<Horari> horaris = horariDAO.findAll();
            if (horaris.isEmpty()) {
                System.out.println("No hi ha horaris disponibles per eliminar");
                seguir = false;
            }else{
                for (Horari horari : horaris) {
                    System.out.println(horari);
                }
                System.out.println("Tria un número d'horari per eliminar");
                int idHorari = Integer.parseInt(Std.readLine());
                Horari horari =(Horari) horariDAO.findById(idHorari);
                horariDAO.delete(horari);
                System.out.println("Vols eliminar més Horaris?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static Set<Horari> afegirHorari(){
        List<Horari> horaris = horariDAO.findAll();
        Set<Horari> horarisTrajecte = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Horari horari : horaris) {
                System.out.println(horari);
            }
            System.out.println("Tria un número de horari per afegir al Trajecte");
            int idHorari = Integer.parseInt(Std.readLine());
            Horari horari =(Horari) horariDAO.findById(idHorari);
            horarisTrajecte.add(horari);
            System.out.println("Vols afegir més Horaris al Trajecte?(s Si, n No)");
            if(!Std.readLine().matches("[sS]")){
                sortirapp = false;
            }
        }
        return horarisTrajecte;
    }

    public static void mostrarHoraris() {
        List<Horari> horaris = horariDAO.findAll();
        if(horaris.isEmpty()){
            System.out.println("No hi ha horaris disponibles");
        }else{
            for (Horari horari : horaris) {
                System.out.println(horari);
            }
        }
    }
}
