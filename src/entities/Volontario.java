package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Volontario
{
    public static Volontario convertRow(Map<String,String> row)
    {
        Volontario v = new Volontario();
        v.setId(Integer.parseInt(row.get("id")));
        v.setNome(row.get("nome"));
        v.setCognome(row.get("cognome"));
        v.setNumero_presenze(Integer.parseInt(row.get("numero_presenze")));
        v.setInvisibilita(Integer.parseInt(row.get("invisibilita")));

        return v;
    }

    public static List<Volontario> convertRows(Map<Integer,Map<String,String>> table)
    {
        List<Volontario> res = new ArrayList<>();
        for(Integer i:table.keySet())
            res.add(convertRow(table.get(i)));
        return res;
    }

    private int id;
    private String nome,cognome;
    private int numero_presenze,invisibilita;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getNumero_presenze() {
        return numero_presenze;
    }

    public void setNumero_presenze(int numero_presenze) {
        this.numero_presenze = numero_presenze;
    }

    public int getInvisibilita() {
        return invisibilita;
    }

    public void setInvisibilita(int invisibilita) {
        this.invisibilita = invisibilita;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Volontario that)) return false;
        return id == that.id && numero_presenze == that.numero_presenze && invisibilita == that.invisibilita && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, numero_presenze, invisibilita);
    }

    @Override
    public String toString() {
        return "Volontario{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}
