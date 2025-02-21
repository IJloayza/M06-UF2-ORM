package com.iticbcn.loayzaignacio.CRUD;

import com.iticbcn.loayzaignacio.Classes.Companyia;
import com.iticbcn.loayzaignacio.DAO.CompanyiaDAO;
import com.iticbcn.loayzaignacio.Classes.Std;
import com.iticbcn.loayzaignacio.Classes.Trajecte;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyiaCRUD {
    protected static CompanyiaDAO companyiaDAO;
    public CompanyiaCRUD(SessionFactory session) {
        if (companyiaDAO == null) {
            companyiaDAO = new CompanyiaDAO(session);
        }
    }

    public static void mostrarCompanyies() {
        List<Companyia> companyias = companyiaDAO.findAll();
        if(companyias.isEmpty()){
            System.out.println("No hi ha companyies disponibles");
        }else{
            for (Companyia companyia : companyias) {
                System.out.println(companyia);
            }
        }
    }

    public static void creaCompanyia(){
        boolean seguir = true;
        while(seguir) {
            //Cal preguntar el nom de la companyia a crear
            System.out.println("Quin nom tindrá la nova Companyia?(c Cancelar)");
            String name = Std.readLine();
            if(name.toLowerCase().matches("[c]")){
                seguir = false;
                break;
            }
            //Verificar si la companyia ja existeix
            if(companyiaDAO.findByName(name) != null){
                //Si ja existeix fer un avís a l'usuari i que provi amb un altre
                throw new IllegalArgumentException("La companyia amb el nom de " + name + " ja existeix");
            }
            //Veure si hi ha Trajectes disponibles per assignar a aquesta companyia

            System.out.println("Afegir a la Companyia un trajecte o crear un Trajecte(a Afegir, d Afegir després)");
            String choice = Std.readLine();
            Set<Trajecte> trajectes;
            Companyia companyia;
            switch (choice.toLowerCase()) {
                case "d", "després": {
                    companyia = new Companyia(name);
                    companyiaDAO.save(companyia);
                    break;
                }
                case "a", "afegir": {

                    //Verificar si hi ha trajectes existents per afegir a la companyia
                    if(!TrajecteCRUD.trajecteDisponible()){
                        throw new IllegalArgumentException("No hi ha trajectes disponibles per afegir a la Companyia");
                    }else{
                        //Crear la Companyia amb els trajectes i el nom i persistir
                        trajectes = TrajecteCRUD.afegirTrajecte();
                        companyia = new Companyia(name, trajectes);
                        companyiaDAO.save(companyia);
                    }
                    break;
                }
                default:
                    System.out.print("Opcio no vàlida");
                    creaCompanyia();
                    break;
            }
        }
    }

    public static void canviaCompanyia(){
        boolean seguir = true;
        while(seguir){
            List<Companyia> companyies = companyiaDAO.findAll();
            if (companyies.isEmpty()) {
                System.out.println("No hi ha companyies disponibles per modificar");
                seguir = false;
            }else{
                for (Companyia companyia : companyies) {
                    System.out.println(companyia);
                }
                System.out.println("Tria un número de companyia per modificar (c Cancelar)");
                String choice = Std.readLine();
                if(choice.toLowerCase().matches("[c]")){
                    seguir = false;
                    break;
                }
                int idCompanyia = Integer.parseInt(choice);
                Companyia companyia =(Companyia) companyiaDAO.findById(idCompanyia);
                System.out.println("Que vols canviar d'aquesta companyia? (n Nom, t Trajectes)");
                switch (Std.readLine().toLowerCase()) {
                    case "n", "nom":
                        System.out.println("Quin nom vols posar a la Companyia?");
                        String name = Std.readLine();
                        companyia.setName(name);
                        companyiaDAO.update(companyia);
                        break;
                    case "t", "trajectes":
                        Set<Trajecte> trajectes = TrajecteCRUD.afegirTrajecte();
                        companyia.setTrajectescom(trajectes);
                        companyiaDAO.update(companyia);
                        break;
                    default:
                        break;
                }
                System.out.println("Vols modificar més Companyies?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static void eliminarCompanyia(){
        boolean seguir = true;
        while(seguir){
            List<Companyia> companyies = companyiaDAO.findAll();
            if (companyies.isEmpty()) {
                System.out.println("No hi ha companyies disponibles per eliminar");
                seguir = false;
            }else{
                for (Companyia companyia : companyies) {
                    System.out.println(companyia);
                }
                System.out.println("Tria un número de companyia per eliminar");
                int idCompanyia = Integer.parseInt(Std.readLine());
                Companyia companyia =(Companyia) companyiaDAO.findById(idCompanyia);
                companyiaDAO.delete(companyia);
                System.out.println("Vols eliminar més Companyies?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static Set<Companyia> afegirCompanyia(){
        List<Companyia> companyies = companyiaDAO.findAll();
        Set<Companyia> companyiesTrajecte = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Companyia companyia : companyies) {
                System.out.println(companyia);
            }
            System.out.println("Tria un número de companyia per afegir al Trajecte");
            int idCompanyia = Integer.parseInt(Std.readLine());
            Companyia companyia =(Companyia) companyiaDAO.findById(idCompanyia);
            companyiesTrajecte.add(companyia);
            System.out.println("Vols afegir més Companyies al Trajecte?(s Si, n No)");
            if(!Std.readLine().matches("[sS]")){
                sortirapp = false;
            }
        }
        return companyiesTrajecte;
    }
}
