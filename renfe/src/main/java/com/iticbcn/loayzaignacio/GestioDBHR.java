package com.iticbcn.loayzaignacio;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.iticbcn.loayzaignacio.DAO.GenericDAO;

public class GestioDBHR {
//Com veurem, aquesta booleana controla si volem sortir de l'aplicació.
    private static boolean sortirapp = false;
    private static SessionFactory session = HibernateUtil.getSessionFactory();
    private static GenericDAO<Estacio> estacioDAO = new GenericDAO<>(Estacio.class, session);
    private static GenericDAO<Horari> horariDAO = new GenericDAO<>(Horari.class, session);
    private static GenericDAO<Trajecte> trajecteDAO = new GenericDAO<>(Trajecte.class, session);
    private static GenericDAO<Companyia> companyiaDAO = new GenericDAO<>(Companyia.class, session);
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
                    List<Estacio> estacions = estacioDAO.findAll();
                    if(estacions.isEmpty()){
                        System.out.println("No hi ha estacions disponibles");
                    }else{
                        for (Estacio estacio : estacions) {
                            System.out.println(estacio);
                        }
                    }
                    break;
                case 2:
                    horariDAO.findAll();
                    List<Horari> horaris = horariDAO.findAll();
                    if(horaris.isEmpty()){
                        System.out.println("No hi ha horaris disponibles");
                    }else{
                        for (Horari horari : horaris) {
                            System.out.println(horari);
                        }
                    }
                    break;
                case 3:
                    trajecteDAO.findAll();
                    List<Trajecte> trajectes = trajecteDAO.findAll();
                    if(trajectes.isEmpty()){
                        System.out.println("No hi ha trajectes disponibles");
                    }else{
                        for (Trajecte trajecte : trajectes) {
                            System.out.println(trajecte);
                        }
                    }
                    break;
                case 4:
                    companyiaDAO.findAll();
                    List<Companyia> companyias = companyiaDAO.findAll();
                    if(companyias.isEmpty()){
                        System.out.println("No hi ha companyies disponibles");
                    }else{
                        for (Companyia companyia : companyias) {
                            System.out.println(companyia);
                        }
                    }
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
                    creaEstacio();
                    break;
                case 2:
                    creaHorari();
                    break;
                case 3:
                    creaTrajecte();
                    break;
                case 4:
                    creaCompanyia();
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
                    canviaEstacio();
                    break;
                case 2:
                    canviaHorari();
                    break;
                case 3:
                    canviaTrajecte();
                    break;
                case 4:
                    canviaCompanyia();
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
                    eliminarEstacio();
                    break;
                case 2:
                    eliminarHorari();
                    break;
                case 3:
                    eliminarTrajecte();
                    break;
                case 4:
                    eliminarCompanyia();
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

    private static void creaCompanyia(){
        boolean seguir = true;
        while(seguir) {
            //Preguntar el nom de la companyia a crear
            System.out.println("Quin nom tindrá la nova Companyia?(c Cancelar)");
            String name = Std.readLine();
            if(name.toLowerCase().matches("[c]")){
                seguir = false;
                break;
            }
            //Verificar si la companyia ja existeix
            if(companyiaDAO.findByName(name) != null){
                //Si ja existeix fer un avis a l'usuari i que provi amb una altre
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
                    if(!trajecteDisponible()){
                        throw new IllegalArgumentException("No hi ha trajectes disponibles per afegir a la Companyia");
                    }else{
                        //Crear la Companyia amb els trajectes i el nom i persistir
                        trajectes = afegirTrajecte();
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
    private static void creaEstacio(){
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
            Estacio estacioProva = estacioDAO.findByName(name);
            if(estacioProva != null){
                //Si ja existeix fer un avis a l'usuari i que provi amb una altre
                throw new IllegalArgumentException("La estació amb el nom de " + name + " ja existeix");
            }
            System.out.println("Afegir a la Estació un trajecte de desti i origen?(s Si, n No)");
            String choice = Std.readLine();
            if(choice.toLowerCase().matches("[s]")){
                if (trajecteDAO.findAll().isEmpty()) {
                    System.out.println("No hi ha trajectes disponibles per aquesta estació");
                    break;
                }
                System.out.println("Afegir trajectes d'origen per a l'estació " + name);
                Set<Trajecte> trajectesOrigen = afegirTrajecte();
                System.out.println("Afegir trajectes d'destí per a l'estació " + name);
                Set<Trajecte> trajectesDesti = afegirTrajecte();
                Estacio estacio = new Estacio(name, trajectesOrigen, trajectesDesti);
                estacioDAO.save(estacio);
            }else{
                Estacio estacio = new Estacio(name);
                estacioDAO.save(estacio);
            }

        }
    }
    private static void creaHorari(){
        boolean seguir = true;
        if (trajecteDAO.findAll().isEmpty()) {
            System.out.println("No hi ha trajectes disponibles per crear un horari");
            seguir = false;
        }
        while(seguir) {
            //Uso de LocalTime per evitar errors de formateig al moment d'introdiur l'hora
            //Preguntar l'hora de sortida
            LocalTime horaSortida = demanaHora();
            if(horaSortida == null){
                seguir = false;
                break;
            }
            //Preguntar l'hora d'arribada
            LocalTime horaArribada = demanaHora();
            if(horaArribada == null){
                seguir = false;
                break;
            }
            //Preguntar el trajecte
            List<Trajecte> trajectes = trajecteDAO.findAll();
            for (Trajecte trajecte : trajectes) {
                System.out.println(trajecte);
            }
            System.out.println("Tria un número de trajecte que tindrà aquest horari");
            int idTrajecte = Integer.parseInt(Std.readLine());
            Trajecte trajecte = trajecteDAO.findById(idTrajecte);
            Horari horari = new Horari(horaSortida.toString(), horaArribada.toString(), trajecte);
            horariDAO.save(horari);
        }
    }
    private static void creaTrajecte(){
        //Si no hi ha estacions disponibles no es pot crear un trajecte
        boolean seguir = true;
        if(estacioDAO.findAll().size() < 2){
            System.out.println("No hi ha estacions disponibles o prous per crear un trajecte");
            seguir = false;
        }
        //Si no hi ha companyies disponibles no es pot crear un trajecte
        if(companyiaDAO.findAll().isEmpty()){
            System.out.println("No hi ha companyies disponibles per crear un trajecte");
            seguir = false;
        }
        while (seguir) {
            List<Estacio> estacions = estacioDAO.findAll();
            for (Estacio estacio : estacions) {
                System.out.println(estacio);
            }
            System.out.println("Tria un número d'estació d'origen per aquest trajecte");
            int idEstacioOrigen = Integer.parseInt(Std.readLine());
            Estacio estOrigen = estacioDAO.findById(idEstacioOrigen);
            System.out.println("Tria un número d'estació de destí per aquest trajecte");
            int idEstacioDesti = Integer.parseInt(Std.readLine());
            Estacio estDesti = estacioDAO.findById(idEstacioDesti);
            System.out.println("Afegir Companyies a aquest trajecte");
            Set<Companyia> companyies = afegirCompanyia();         
            System.out.println("Vols afegir horaris a aquest trajecte?(s Si, n No)");
            String choice = Std.readLine().toLowerCase();
            switch (choice) {
                case "s", "si":
                if(horariDAO.findAll().isEmpty()){
                    System.out.println("No hi ha horaris disponibles per aquest trajecte");
                    System.out.println("Tria actualitzant després");
                    Trajecte trajecte = new Trajecte(estOrigen, estDesti, companyies);
                    trajecteDAO.save(trajecte);    
                }else{
                    Set<Horari> horaris = afegirHorari();
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

    private static void canviaCompanyia(){
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
                Companyia companyia = companyiaDAO.findById(idCompanyia);
                System.out.println("Que vols canviar d'aquesta companyia? (n Nom, t Trajectes)");
                switch (Std.readLine().toLowerCase()) {
                    case "n", "nom":
                        System.out.println("Quin nom vols posar a la Companyia?");
                        String name = Std.readLine();
                        companyia.setName(name);
                        companyiaDAO.update(companyia);
                        break;
                    case "t", "trajectes":
                        Set<Trajecte> trajectes = afegirTrajecte();
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

    private static void canviaEstacio(){
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
                Estacio estacio = estacioDAO.findById(idEstacio);
                System.out.println("Que vols canviar d'aquesta Estació? (n Nom, to TrajectesOrigen, td TrajectesDesti)");
                switch (Std.readLine().toLowerCase()) {
                    case "n", "nom":
                        System.out.println("Quin nom vols posar a l'Estació?");
                        String name = Std.readLine();
                        estacio.setName(name);
                        estacioDAO.update(estacio);
                        break;
                    case "to", "trajectesorigen":
                        Set<Trajecte> trajectesOrigen = afegirTrajecte();
                        estacio.setTrajectesOrigen(trajectesOrigen);
                        estacioDAO.update(estacio);
                        break;
                    case "td", "trajectesdesti":
                        Set<Trajecte> trajectesDesti = afegirTrajecte();
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

    private static void canviaHorari(){
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
                Horari horari = horariDAO.findById(idHorari);
                System.out.println("Que vols canviar d'aquest Horari? (hs HoraSortida, ha HoraArribada, t Trajecte)");
                switch (Std.readLine().toLowerCase()) {
                    case "hs", "horasortida":
                        LocalTime horaSortida = demanaHora();
                        horari.setHourDepart(horaSortida.toString());
                        horariDAO.update(horari);
                        break;
                    case "ha", "horaarribada":
                        LocalTime horaArribada = demanaHora();
                        horari.setHourArribe(horaArribada.toString());
                        horariDAO.update(horari);
                        break;
                    case "t", "trajecte":
                        List<Trajecte> trajectes = trajecteDAO.findAll();
                        for (Trajecte trajecte : trajectes) {
                            System.out.println(trajecte);
                        }
                        System.out.println("Tria un número de trajecte per aquest horari");
                        int idTrajecte = Integer.parseInt(Std.readLine());
                        Trajecte trajecte = trajecteDAO.findById(idTrajecte);
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

    private static void canviaTrajecte(){
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
                Trajecte trajecte = trajecteDAO.findById(idTrajecte);
                System.out.println("Que vols canviar d'aquest Trajecte? (eo EstOrigen, ed EstDesti, c Companyies, h Horaris)");
                switch (Std.readLine().toLowerCase()) {
                    case "eo", "estorigen":
                        List<Estacio> estacions = estacioDAO.findAll();
                        for (Estacio estacio : estacions) {
                            System.out.println(estacio);
                        }
                        System.out.println("Tria un número d'estació d'origen per aquest trajecte (c Cancelar)");
                        int idEstacioOrigen = Integer.parseInt(Std.readLine());
                        Estacio estOrigen = estacioDAO.findById(idEstacioOrigen);
                        trajecte.setEstOrigen(estOrigen);
                        trajecteDAO.update(trajecte);
                        break;
                    case "ed", "estdesti":
                        List<Estacio> estacionsDesti = estacioDAO.findAll();
                        for (Estacio estacio : estacionsDesti) {
                            System.out.println(estacio);
                        }
                        System.out.println("Tria un número d'estació de destí per aquest trajecte");
                        int idEstacioDesti = Integer.parseInt(Std.readLine());
                        Estacio estDesti = estacioDAO.findById(idEstacioDesti);
                        trajecte.setEstDesti(estDesti);
                        trajecteDAO.update(trajecte);
                        break;
                    case "c", "companyies":
                        Set<Companyia> companyies = afegirCompanyia();
                        trajecte.setCompanyies(companyies);
                        trajecteDAO.update(trajecte);
                        break;
                    case "h", "horaris":
                        Set<Horari> horaris = afegirHorari();
                        trajecte.setHoraris(horaris);
                        trajecteDAO.update(trajecte);
                        break;
                    default:
                        break;
                    }
            }
        }
    }

    private static void eliminarCompanyia(){
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
                Companyia companyia = companyiaDAO.findById(idCompanyia);
                companyiaDAO.delete(companyia);
                System.out.println("Vols eliminar més Companyies?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    private static void eliminarEstacio(){
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
                Estacio estacio = estacioDAO.findById(idEstacio);
                estacioDAO.delete(estacio);
                System.out.println("Vols eliminar més Estacions?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    private static void eliminarHorari(){
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
                Horari horari = horariDAO.findById(idHorari);
                horariDAO.delete(horari);
                System.out.println("Vols eliminar més Horaris?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    private static void eliminarTrajecte(){
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
                Trajecte trajecte = trajecteDAO.findById(idTrajecte);
                trajecteDAO.delete(trajecte);
                System.out.println("Vols eliminar més Trajectes?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    seguir = false;
                }
            }
        }
    }

    private static Set<Trajecte> afegirTrajecte(){
        List<Trajecte> trajectes = trajecteDAO.findAll();
        Set<Trajecte> trajectesCompanyia = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Trajecte trajecte : trajectes) {
                System.out.println(trajecte);
            }
            System.out.println("Tria un número de trajecte per afegir a la Companyia");
                int idTrajecte = Integer.parseInt(Std.readLine());
                Trajecte trajecte = trajecteDAO.findById(idTrajecte);
                trajectesCompanyia.add(trajecte);
                System.out.println("Vols afegir més trajectes a la Companyia?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    sortirapp = false;
                }
        }
        return trajectesCompanyia;
    }
    private static Set<Companyia> afegirCompanyia(){
        List<Companyia> companyies = companyiaDAO.findAll();
        Set<Companyia> companyiesTrajecte = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Companyia companyia : companyies) {
                System.out.println(companyia);
            }
            System.out.println("Tria un número de companyia per afegir al Trajecte");
                int idCompanyia = Integer.parseInt(Std.readLine());
                Companyia companyia = companyiaDAO.findById(idCompanyia);
                companyiesTrajecte.add(companyia);
                System.out.println("Vols afegir més Companyies al Trajecte?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    sortirapp = false;
                }
        }
        return companyiesTrajecte;
    }
    private static Set<Horari> afegirHorari(){
        List<Horari> horaris = horariDAO.findAll();
        Set<Horari> horarisTrajecte = new HashSet<>();
        boolean sortirapp = true;
        while (sortirapp) {
            for (Horari horari : horaris) {
                System.out.println(horari);
            }
            System.out.println("Tria un número de horari per afegir al Trajecte");
                int idHorari = Integer.parseInt(Std.readLine());
                Horari horari = horariDAO.findById(idHorari);
                horarisTrajecte.add(horari);
                System.out.println("Vols afegir més Horaris al Trajecte?(s Si, n No)");
                if(!Std.readLine().matches("[sS]")){
                    sortirapp = false;
                }
        }
        return horarisTrajecte;
    }
    private static boolean trajecteDisponible(){
        List<Trajecte> trajectes = trajecteDAO.findAll();
        if(trajectes.isEmpty()){
            return false;
        }
        return true;
    }

    public static LocalTime demanaHora(){
        boolean horaValida = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        while (horaValida) {
            System.out.println("Introdueix l'hora de sortida en format HH:mm 24 hores(c Cancelar)");
            String hora = Std.readLine();
            if(hora.toLowerCase().matches("[c]")){
                horaValida = false;
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