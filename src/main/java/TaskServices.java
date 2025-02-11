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

    private List<Task> loadTasksFromFile(String fileName) {
        List<Task> tasks = new ArrayList<>();
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Aqui precisamos de uma forma de reconstruir as tarefas a partir do arquivo.
                    tasks.add(new Task(line, Task.Difficulty.EASY)); // Ajuste a lógica de conversão
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar tarefas: " + e.getMessage());
        }

        return tasks;
    }

    //concluir uma task
    public void completeTask(String title) {
        List<Task> tasks = loadTasksFromFile(INCOMPLETE_TASKS_FILE);
        Task completedTask = null;

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                completedTask = task;
                break;
            }
        }

        if (completedTask != null) {
            tasks.remove(completedTask);
            completedTask.setStats(Task.Stats.FEITA);
            moveTaskToCompleteFile(completedTask);
            rewriteTasksFile(tasks, INCOMPLETE_TASKS_FILE);
            System.out.println("Tarefa '" + completedTask.getTitle() + "' concluída.");
        } else {
            System.out.println("Tarefa não foi encontrada.");
        }
    }

    //listar tasks concluídas
    public void listCompletedTasks() {
        System.out.println("Tarefas concluídas:");
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
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isEmpty = true;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }

            if (isEmpty) {
                System.out.println("Nenhuma tarefa encontrada.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " +e.getMessage());
        }
    }

    //mover task concluida para o .txt de concluidas
    private void moveTaskToCompleteFile(Task task) {
        saveTaskToFile(task, COMPLETED_TASKS_FILE);
        removeTaskFromFile(task.getTitle(), INCOMPLETE_TASKS_FILE);
    }

    //remover task do .txt de incompletas
    private void removeTaskFromFile(String title, String fileName) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean removed = false;

            while ((line = reader.readLine()) != null) {
                if (!line.equalsIgnoreCase(title)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    removed = true;
                }
            }

            if (!removed) {
                System.out.println("Tarefa não encontrada.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao remover a tarefa: " + e.getMessage());
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    public void editTask(String oldTitle, String newTitle, Task.Difficulty newDifficulty) {
        List<Task> tasks = loadTasksFromFile(INCOMPLETE_TASKS_FILE);
        boolean found = false;

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(oldTitle)) {
                task.setTitle(newTitle);
                task.setDifficulty(newDifficulty);
                found = true;
                break;
            }
        }

        if (found) {
            rewriteTasksFile(tasks, INCOMPLETE_TASKS_FILE);
            System.out.println("Tarefa atualizada com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void rewriteTasksFile(List<Task> tasks, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }

}

