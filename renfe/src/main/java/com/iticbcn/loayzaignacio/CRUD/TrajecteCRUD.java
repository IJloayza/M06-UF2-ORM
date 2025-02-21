package com.iticbcn.loayzaignacio.CRUD;

import com.iticbcn.loayzaignacio.Classes.*;
import com.iticbcn.loayzaignacio.DAO.TrajecteDAO;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrajecteCRUD {
    protected static TrajecteDAO trajecteDAO;
    public TrajecteCRUD(SessionFactory session) {
        if (trajecteDAO == null) {
            trajecteDAO = new TrajecteDAO(session);
        }
    }

    public static void creaTrajecte(){
        //Si no hi ha estacions disponibles no es pot crear un trajecte
        boolean seguir = true;
        if(EstacioCRUD.estacioDAO.findAll().size() < 2){
            System.out.println("No hi ha estacions disponibles o prous per crear un trajecte");
            seguir = false;
        }
        //Si no hi ha companyies disponibles no es pot crear un trajecte
        if(CompanyiaCRUD.companyiaDAO.findAll().isEmpty()){
            System.out.println("No hi ha companyies disponibles per crear un trajecte");
            seguir = false;
        }
        while (seguir) {
            List<Estacio> estacions = EstacioCRUD.estacioDAO.findAll();
            for (Estacio estacio : estacions) {
                System.out.println(estacio);
            }
            System.out.println("Tria un número d'estació d'origen per aquest trajecte");
            int idEstacioOrigen = Integer.parseInt(Std.readLine());
            Estacio estOrigen =(Estacio) EstacioCRUD.estacioDAO.findById(idEstacioOrigen);
            System.out.println("Tria un número d'estació de destí per aquest trajecte");
            int idEstacioDesti = Integer.parseInt(Std.readLine());
            Estacio estDesti =(Estacio) EstacioCRUD.estacioDAO.findById(idEstacioDesti);
            System.out.println("Afegir Companyies a aquest trajecte");
            Set<Companyia> companyies = CompanyiaCRUD.afegirCompanyia();
            System.out.println("Vols afegir horaris a aquest trajecte?(s Si, n No)");
            String choice = Std.readLine().toLowerCase();
            switch (choice) {
                case "s", "si":
                    if(HorariCRUD.horariDAO.findAll().isEmpty()){
                        System.out.println("No hi ha horaris disponibles per aquest trajecte");
                        System.out.println("Tria actualitzant després");
                        Trajecte trajecte = new Trajecte(estOrigen, estDesti, companyies);
                        trajecteDAO.save(trajecte);
                    }else{
                        Set<Horari> horaris = HorariCRUD.afegirHorari();
                        if (!horaris.isEmpty()) {
                            Trajecte trajecte = new Trajecte(estOrigen, estDesti, companyies, horaris);
                            trajecteDAO.save(trajecte);
                        }else{
                            System.out.println("No s'ha afegit cap horari al trajecte");
                        }
                    }
                    break;
                case "n", "no":
                    Trajecte trajecte = new Trajecte(estOrigen, estDesti, companyies);
                    trajecteDAO.save(trajecte);
                    break;

                default:
                    break;
            }
            System.out.println("Vols afegir més trajectes?(s Si, n No)");
            if(!Std.readLine().matches("[sS]")){
                seguir = false;
            }
        }
    }

    public static void canviaTrajecte(){
        boolean seguir = true;
        while(seguir){
            List<Trajecte> trajectes = trajecteDAO.findAll();
            if (trajectes.isEmpty()) {
                System.out.println("No hi ha trajectes disponibles per modificar");
                seguir = false;
            }else{
                for (Trajecte trajecte : trajectes) {
                    System.out.println(trajecte);
                }
                System.out.println("Tria un número de trajecte per modificar (c Cancelar)");
                String choice = Std.readLine();
                if(choice.toLowerCase().matches("[c]")){
                    seguir = false;
                    break;
                }
                int idTrajecte = Integer.parseInt(choice);
                Trajecte trajecte =(Trajecte) trajecteDAO.findById(idTrajecte);
                System.out.println("Que vols canviar d'aquest Trajecte? (eo EstOrigen, ed EstDesti, c Companyies, h Horaris)");
                switch (Std.readLine().toLowerCase()) {
                    case "eo", "estorigen":
                        List<Estacio> estacions = EstacioCRUD.estacioDAO.findAll();
                        for (Estacio estacio : estacions) {
                            System.out.println(estacio);
                        }
                        System.out.println("Tria un número d'estació d'origen per aquest trajecte (c Cancelar)");
                        int idEstacioOrigen = Integer.parseInt(Std.readLine());
                        Estacio estOrigen =(Estacio) EstacioCRUD.estacioDAO.findById(idEstacioOrigen);
                        trajecte.setEstOrigen(estOrigen);
                        trajecteDAO.update(trajecte);
                        break;
                    case "ed", "estdesti":
                        List<Estacio> estacionsDesti = EstacioCRUD.estacioDAO.findAll();
                        for (Estacio estacio : estacionsDesti) {
                            System.out.println(estacio);
                        }
                        System.out.println("Tria un número d'estació de destí per aquest trajecte");
                        int idEstacioDesti = Integer.parseInt(Std.readLine());
                        Estacio estDesti =(Estacio) EstacioCRUD.estacioDAO.findById(idEstacioDesti);
                        trajecte.setEstDesti(estDesti);
                        trajecteDAO.update(trajecte);
                        break;
                    case "c", "companyies":
                        Set<Companyia> companyies = CompanyiaCRUD.afegirCompanyia();
                        trajecte.setCompanyies(companyies);
                        trajecteDAO.update(trajecte);
                        break;
                    case "h", "horaris":
                        Set<Horari> horaris = HorariCRUD.afegirHorari();
                        trajecte.setHoraris(horaris);
                        trajecteDAO.update(trajecte);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void eliminarTrajecte(){
        boolean seguir = true;
        while(seguir){
            List<Trajecte> trajectes = trajecteDAO.findAll();
            if (trajectes.isEmpty()) {
                System.out.println("No hi ha trajectes disponibles per eliminar");
                seguir = false;
            }else{
                for (Trajecte trajecte : trajectes) {
                    System.out.println(trajecte);
                }
                System.out.println("Tria un número de trajecte per eliminar");
                int idTrajecte = Integer.parseInt(Std.readLine());
                Trajecte trajecte =(Trajecte) trajecteDAO.findById(idTrajecte);
                trajecteDAO.delete(trajecte);
                System.out.println("Vols eliminar més Trajectes?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static Set<Trajecte> afegirTrajecte(){
        List<Trajecte> trajectes = trajecteDAO.findAll();
        Set<Trajecte> trajectesCompanyia = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Trajecte trajecte : trajectes) {
                System.out.println(trajecte);
            }
            System.out.println("Tria un número de trajecte per afegir a la Companyia");
            int idTrajecte = Integer.parseInt(Std.readLine());
            Trajecte trajecte =(Trajecte) trajecteDAO.findById(idTrajecte);
            trajectesCompanyia.add(trajecte);
            System.out.println("Vols afegir més trajectes a la Companyia?(s Si, n No)");
            if(!Std.readLine().matches("[sS]")){
                sortirapp = false;
            }
        }
        return trajectesCompanyia;
    }

    public static boolean trajecteDisponible(){
        List<Trajecte> trajectes = trajecteDAO.findAll();
        if(trajectes.isEmpty()){
            return false;
        }
        return true;
    }

    public static void mostrarTrajectes() {
        List<Trajecte> trajectes = trajecteDAO.findAll();
        if(trajectes.isEmpty()){
            System.out.println("No hi ha trajectes disponibles");
        }else{
            for (Trajecte trajecte : trajectes) {
                System.out.println(trajecte);
            }
        }
    }
}
