classDiagram
direction BT
class Companyia {
String name
int id
Set~Trajecte~ trajectescom
+ toString() String
}

    class CompanyiaCRUD {
	    + mostrarCompanyies() void
	    + canviaCompanyia() void
	    + creaCompanyia() void
	    + eliminarCompanyia() void
	    + afegirCompanyia() Set~Companyia~
    }

    class Estacio {
	    Set~Trajecte~ trajectesOrigen
	    String name
	    Set~Trajecte~ trajectesDesti
	    int idEstacio
	    + toString() String
	    - preRemove() void
    }

    class EstacioCRUD {
	    + mostrarEstacions() void
	    + creaEstacio() void
	    + canviaEstacio() void
	    + eliminarEstacio() void
    }

    class EstacioDAO {
    }

    class GenericDAO~T~ {
	    + save(T) void
	    + update(T) void
	    + findById(int) T
	    + endSession() void
	    + groupComp() List~Object[]~
	    + delete(T) void
	    + findByName(String) T
	    + findAll() List~T~
    }

    class GenericInterfaceDAO~T~ {
	    + save(T) void
	    + update(T) void
	    + delete(T) void
	    + findById(int) T
	    + findAll() List~T~
    }

    class GestioDBHR {
	    - printScreen(Terminal, String) void
	    + MenuSelect() void
	    + demanaHora() LocalTime?
	    + MenuDelete() void
	    + MenuUpdate() void
	    + MenuShowCompWithTrajects() void
	    + main(String[]) void
	    + MenuOptions() void
	    + MenuInsert() void
    }

    class HibernateUtil {
	    SessionFactory sessionFactory
	    - buildSessionFactory() SessionFactory
    }

    class Horari {
	    Trajecte trajecte
	    String hourDepart
	    String hourArribe
	    int idHorari
	    + toString() String
    }

    class HorariCRUD {
	    + creaHorari() void
	    + canviaHorari() void
	    + afegirHorari() Set~Horari~
	    + eliminarHorari() void
	    + mostrarHoraris() void
    }

    class HorariDAO {
    }

    class Std {
	    + readLine() String
    }

    class Trajecte {
	    Estacio estDesti
	    int id
	    Set~Companyia~ companyies
	    Estacio estOrigen
	    Set~Horari~ horaris
	    - preRemove() void
	    + toString() String
    }

    class TrajecteCRUD {
	    + eliminarTrajecte() void
	    + mostrarTrajectes() void
	    + creaTrajecte() void
	    + trajecteDisponible() boolean
	    + canviaTrajecte() void
	    + afegirTrajecte() Set~Trajecte~
    }

    class TrajecteDAO {
    }

    class CompanyiaDAO {
    }

	<<Interface>> GenericInterfaceDAO

    CompanyiaDAO ..> GenericDAO
    EstacioDAO ..> GenericDAO
    GenericDAO --|> GenericInterfaceDAO
    HorariDAO ..> GenericDAO
    TrajecteDAO ..> GenericDAO

    GestioDBHR --> CompanyiaCRUD 
    GestioDBHR --> EstacioCRUD
    GestioDBHR --> TrajecteCRUD 
    GestioDBHR --> HorariCRUD  

    CompanyiaCRUD --> CompanyiaDAO
    EstacioCRUD --> EstacioDAO
    TrajecteCRUD --> TrajecteDAO
    HorariCRUD --> HorariDAO

    CompanyiaCRUD --> Companyia
    EstacioCRUD --> Estacio
    TrajecteCRUD --> Trajecte
    HorariCRUD --> Horari
