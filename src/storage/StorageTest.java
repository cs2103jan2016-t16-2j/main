package storage;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;
import common.Task;
import common.TaskType;
import storage.Storage;
import common.State;
public class StorageTest {

	@Test
	public void testIO() {
		// instantiate State and Storage class for testing
		State state = new State();
		Storage storage = new Storage(state);
		
		// create one floating task and one normal task
		Task floatingTask = new Task("This is a floating Task");
		Task normalTask = new Task("This is a normal Task");
		floatingTask.setTaskType(TaskType.FLOATING);
		floatingTask.setDetail("floating");
		floatingTask.setVenue("floating");
		normalTask.setTaskType(TaskType.DEADLINE);
		normalTask.setDetail("deadline");
		normalTask.setVenue("deadline");
		
		// update the state just like logic do
		TreeSet<Task> normalTasks = new TreeSet<Task>();
		normalTasks.add(normalTask);
		state.setNormalTasks(normalTasks);
		
		ArrayList<Task> floatingTasks = new ArrayList<Task>();
		floatingTasks.add(floatingTask);
		state.setFloatingTasks(floatingTasks);
		
		// test1 : test writeTaskToJson()
		BufferedWriter testWriter;
		try {
			testWriter = new BufferedWriter(new FileWriter("storageTestFile.txt"));
			assertEquals(storage.writeTaskToJson(testWriter),true);
			testWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// test2 : test readCurrentTask()
		BufferedReader testReader;
		try {
			testReader = new BufferedReader(new FileReader("storageTestFile.txt"));
			// first input task is normal task
			Task inputTask = storage.readCurrentTask(testReader);
			assertEquals(inputTask.getContent(),"This is a normal Task");
			assertEquals(inputTask.getVenue(),"deadline");
			assertEquals(inputTask.getDetail(),"deadline");
			
			// second task is floating task
			Task inputTask2 = storage.readCurrentTask(testReader);
			assertEquals(inputTask2.getContent(),"This is a floating Task");
			assertEquals(inputTask2.getVenue(),"floating");
			assertEquals(inputTask2.getDetail(),"floating");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
