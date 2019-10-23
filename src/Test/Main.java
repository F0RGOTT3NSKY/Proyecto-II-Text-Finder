package Test;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Task task = new TaskBuilder(5).setDescription("Hello").setSummary("Test").build();
        System.out.println(task.getDescription());
	}

}
