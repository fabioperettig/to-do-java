import java.time.LocalDate;

public class Task {

    //atributos
    private String title;
    private Difficulty difficulty;
    private Stats stats;
    private LocalDate creationDate;

    //enums (conjunto fixos de constantes)
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public enum Stats {
        PENDENTE,
        EM_PROGRESSO,
        FEITA
    }

    //construtor
    public Task(String title, Difficulty difficulty){
        this.title = title;
        this.difficulty = difficulty;
        this.stats = Stats.PENDENTE;
        this.creationDate = LocalDate.now();

    }


    //Getters e Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString(){
        return title + "\n" +
                "Dificuldade " +
                difficulty + "\n" +
                stats + "\n" +
                "Criada em: " +creationDate + "\n";
    }

}
