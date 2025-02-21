package com.iticbcn.loayzaignacio.CRUD;

import com.iticbcn.loayzaignacio.DAO.EstacioDAO;
import com.iticbcn.loayzaignacio.Classes.Estacio;
import com.iticbcn.loayzaignacio.Classes.Std;
import com.iticbcn.loayzaignacio.Classes.Trajecte;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

public class EstacioCRUD {
    protected static EstacioDAO estacioDAO;
    public EstacioCRUD(SessionFactory session) {
        if (estacioDAO == null) {
            estacioDAO = new EstacioDAO(session);
        }
    }

    public static void mostrarEstacions() {
        List<Estacio> estacions = estacioDAO.findAll();
        if(estacions.isEmpty()){
            System.out.println("No hi ha companyies disponibles");
        }else{
            for (Estacio estacio : estacions) {
                System.out.println(estacio);
            }
        }
    }

    public static void creaEstacio(){
        boolean seguir = true;
        while(seguir) {
            //Preguntar el nom de la estació a crear
            System.out.println("Quin nom tindrá la nova Estació?(c Cancelar)");
            String name = Std.readLine();
            if(name.toLowerCase().matches("[c]")){
                seguir = false;
                break;
            }
            //Verificar si la companyia ja existeix
            Estacio estacioProva =(Estacio) estacioDAO.findByName(name);
            if(estacioProva != null){
                //Si ja existeix fer un avis a l'usuari i que provi amb un altre
                throw new IllegalArgumentException("La estació amb el nom de " + name + " ja existeix");
            }
            System.out.println("Afegir a la Estació un trajecte de desti i origen?(s Si, n No)");
            String choice = Std.readLine();
            if(choice.toLowerCase().matches("[s]")){
                if (TrajecteCRUD.trajecteDAO.findAll().isEmpty()) {
                    System.out.println("No hi ha trajectes disponibles per aquesta estació");
                    break;
                }
                System.out.println("Afegir trajectes d'origen per a l'estació " + name);
                Set<Trajecte> trajectesOrigen = TrajecteCRUD.afegirTrajecte();
                System.out.println("Afegir trajectes d'destí per a l'estació " + name);
                Set<Trajecte> trajectesDesti = TrajecteCRUD.afegirTrajecte();
                Estacio estacio = new Estacio(name, trajectesOrigen, trajectesDesti);
                estacioDAO.save(estacio);
            }else{
                Estacio estacio = new Estacio(name);
                estacioDAO.save(estacio);
            }

        }
    }

    public static void canviaEstacio(){
        boolean seguir = true;
        while(seguir){
            List<Estacio> estacions = estacioDAO.findAll();
            if (estacions.isEmpty()) {
                System.out.println("No hi ha estacions disponibles per modificar");
                seguir = false;
            }else{
                for (Estacio estacio : estacions) {
                    System.out.println(estacio);
                }
                System.out.println("Tria un número d'estació per modificar (c Cancelar)");
                String choice = Std.readLine();
                if(choice.toLowerCase().matches("[c]")){
                    seguir = false;
                    break;
                }
                int idEstacio = Integer.parseInt(choice);
                Estacio estacio =(Estacio) estacioDAO.findById(idEstacio);
                System.out.println("Que vols canviar d'aquesta Estació? (n Nom, to TrajectesOrigen, td TrajectesDesti)");
                switch (Std.readLine().toLowerCase()) {
                    case "n", "nom":
                        System.out.println("Quin nom vols posar a l'Estació?");
                        String name = Std.readLine();
                        estacio.setName(name);
                        estacioDAO.update(estacio);
                        break;
                    case "to", "trajectesorigen":
                        Set<Trajecte> trajectesOrigen = TrajecteCRUD.afegirTrajecte();
                        estacio.setTrajectesOrigen(trajectesOrigen);
                        estacioDAO.update(estacio);
                        break;
                    case "td", "trajectesdesti":
                        Set<Trajecte> trajectesDesti = TrajecteCRUD.afegirTrajecte();
                        estacio.setTrajectesDesti(trajectesDesti);
                        estacioDAO.update(estacio);
                        break;

                    default:
                        break;
                }
                System.out.println("Vols modificar més Estacions?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    public static void eliminarEstacio(){
        boolean seguir = true;
        while(seguir){
            List<Estacio> estacions = estacioDAO.findAll();
            if (estacions.isEmpty()) {
                System.out.println("No hi ha estacions disponibles per eliminar");
                seguir = false;
            }else{
                for (Estacio estacio : estacions) {
                    System.out.println(estacio);
                }
                System.out.println("Tria un número d'estació per eliminar");
                int idEstacio = Integer.parseInt(Std.readLine());
                Estacio estacio =(Estacio) estacioDAO.findById(idEstacio);
                estacioDAO.delete(estacio);
                System.out.println("Vols eliminar més Estacions?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }
}
