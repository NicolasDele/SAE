package API;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class SumTask extends RecursiveTask<Integer> {
    private int[] array;
    private int start;
    private int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 10) {
            // Cas de base : si la taille est petite, effectuez le calcul directement
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Divisez la tâche en deux sous-tâches
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            // Fourchez les sous-tâches
            leftTask.fork();
            rightTask.fork();

            // Joignez les résultats des sous-tâches
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // Combinez les résultats
            return leftResult + rightResult;
        }
    }
}



public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);

        int result = pool.invoke(task);

        System.out.println("La somme est : " + result);
    }
}