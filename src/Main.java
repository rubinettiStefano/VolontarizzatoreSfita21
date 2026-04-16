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

        for(Volontario v:possibili)
        {
            System.out.println(v);
        }

    }

    private static void reset()
    {
        db.executeDml("UPDATE volontario SET numero_presenze=0");
        System.out.println("PRESENZE RESETTATE");
    }
}
