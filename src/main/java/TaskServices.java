import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskServices {

    private static final String INCOMPLETE_TASKS_FILE = "incomplete_tasks.txt";
    private static final String COMPLETED_TASKS_FILE = "completed_tasks.txt";

    private List<Task> myTasks = new ArrayList<>();

    //adicionar task
    public void addTask(Task task) {
        myTasks.add(task);
        saveTaskToFile(task, INCOMPLETE_TASKS_FILE);
        System.out.println( task.getTitle() + " adicionado à lista.");
    }

    //listar tasks pendentes
    public void listIncompleteTasks() {
        System.out.println("Tarefas pendentes:");
        readTasksFromFile(INCOMPLETE_TASKS_FILE);
    }

    //concluir uma task
    public void completeTask(String title) {
        Task completedTask = null;

        for (Task task : myTasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                completedTask = task;
                break;
            }
        }

        if (completedTask != null) {
            myTasks.remove(completedTask);
            completedTask.setStats(Task.Stats.FEITA);
            moveTaskToCompleteFile(completedTask);
            System.out.println( completedTask.getTitle() + " concluído.");

        } else {
            System.out.println("Tarefa não foi encontrada.");
        }
    }

    //listar tasks concluídas
    public void listCompletedTasks() {
        System.out.println("tarefas concluídas:");
        readTasksFromFile(COMPLETED_TASKS_FILE);
    }

    //salvar task no arquivo .txt
    private void saveTaskToFile(Task task, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer. write(task.toString());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Erro ao salvar a tarefa: " + e.getMessage());
        }
    }

    //ler tasks de um arquivo
    private void readTasksFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " +e.getMessage());
        }
    }

    //mover task concluida para o .txt de concluidas
    private void moveTaskToCompleteFile(Task task) {
        saveTaskToFile(task, COMPLETED_TASKS_FILE);
        removeTaskFromFile(task, INCOMPLETE_TASKS_FILE);
    }

    //remover task do .txt de incompletas
    private void removeTaskFromFile (Task task, String fileName) {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer =  new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(task.getTitle())) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            reader.close();
            writer.close();

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover a tarefa: " + e.getMessage());
        }
    }
}
