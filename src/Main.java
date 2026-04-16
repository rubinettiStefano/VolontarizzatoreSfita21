import com.generation.db.DbSfita;
import entities.Volontario;

import java.nio.IntBuffer;
import java.util.List;
import java.util.Scanner;

public class Main
{
    static Scanner sc = new Scanner(System.in);
    static DbSfita db = new DbSfita("volontarizzatore");
    static void main()
    {
        int cmd = 0;
        do {
            System.out.println("Dammi comando (1 per volontario,0 per reset)");
            cmd= Integer.parseInt(sc.nextLine());
            switch (cmd)
            {
                case 0 -> reset();
                case 1 -> scegliVolontario();
                case -1 -> System.out.println("CIAO CIAO");
            }
        }while (cmd!=-1);
    }

    private static void scegliVolontario()
    {
        //con db.read(...) leggo la Map<Integer,Map<String,String>> dal db
        //la passo come parametro a Volontario.convertRows(), che la trasforma in una List<Volontario>
        List<Volontario> possibili = Volontario.convertRows(
                                                    db.read("""
                                                                    SELECT * FROM volontario
                                                                    WHERE numero_presenze=(
                                                                        SELECT MIN(numero_presenze)
                                                                        FROM volontario
                                                                        );
                                                        """)
                                                );

        //come fare scelta casuale con valori pesati
        //1) somma di tutti i valori per definire lunghezza segmento
        int somma = 0;
        for(Volontario v : possibili)
            somma+=v.getInvisibilita();

        double randomico = Math.random()*somma;

        int totaleFinoAdOra = 0;

//        Martina,De Luca,0,3
        Volontario scelto=null;

        for(Volontario v:possibili)
        {
            totaleFinoAdOra+=v.getInvisibilita();
            if(randomico<=totaleFinoAdOra) {
                scelto = v;
                break;
            }
        }

        System.out.println("Il/la volontario/a scelto/a è "+scelto.getNome()+" "+scelto.getCognome());
        db.executeDml("update volontario set numero_presenze=numero_presenze+1 where id= "+scelto.getId());
    }

    private static void reset()
    {
        db.executeDml("UPDATE volontario SET numero_presenze=0");
        System.out.println("PRESENZE RESETTATE");
    }
}
