import java.util.Scanner;

public class Main {  // Corrigido de "main" para "Main"
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskServices taskServices = new TaskServices();

        boolean running = true;

        while (true) {
            System.out.println("\n📌 MENU DA TO-DO LIST");
            System.out.println("1️⃣ Adicionar nova tarefa");
            System.out.println("2️⃣ Listar tarefas pendentes");
            System.out.println("3️⃣ Concluir uma tarefa");
            System.out.println("4️⃣ Listar tarefas concluídas");
            System.out.println("5️⃣ Editar uma tarefa");
            System.out.println("6️⃣ Sair");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (option) {
                case 1:
                    System.out.println("Digite o título da tarefa: ");
                    String title = scanner.nextLine();
                    System.out.println("Dificuldade (EASY, MEDIUM, HARD): ");
                    String difficultyInput = scanner.nextLine().toUpperCase();

                    Task.Difficulty difficulty;
                    try {
                        difficulty = Task.Difficulty.valueOf(difficultyInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Dificuldade inválida! Escolha entre: EASY, MEDIUM ou HARD. Usando EASY por padrão.");
                        difficulty = Task.Difficulty.EASY;
                    }

                    Task newTask = new Task(title, difficulty);
                    taskServices.addTask(newTask);
                    break;

                case 2:
                    taskServices.listIncompleteTasks();
                    break;

                case 3:
                    System.out.println("Digite o título da tarefa concluída: ");
                    String completedTitle = scanner.nextLine();
                    taskServices.completeTask(completedTitle);
                    break;

                case 4:
                    taskServices.listCompletedTasks();
                    break;

                case 5:
                    System.out.print("Digite o título da tarefa que deseja editar: ");
                    String oldTitle = scanner.nextLine();

                    System.out.print("Digite o novo título: ");
                    String newTitle = scanner.nextLine();

                    System.out.print("Nova dificuldade (EASY, MEDIUM, HARD): ");
                    String newDifficultyInput = scanner.nextLine().toUpperCase();

                    Task.Difficulty newDifficulty;
                    try {
                        newDifficulty = Task.Difficulty.valueOf(newDifficultyInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Dificuldade inválida! Usando EASY por padrão.");
                        newDifficulty = Task.Difficulty.EASY;
                    }

                    taskServices.editTask(oldTitle, newTitle, newDifficulty);
                    break;

                case 6:
                    System.out.println("Saindo do programa... 🚀");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}