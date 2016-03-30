package facade;
import common.*;
import parser.TimeParser;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class WallistModelTest {

	@Test
	public void test() {
		WallistModel wm = new WallistModel();
		State state = wm.getState();
		state.setContent("Boxin is testing");
		//testing task constructor
		Task newTask = new Task(state);
		wm.process("clear float");
		wm.process("clear deadline");

		//testing add a floating task
		wm.process("add Boxin is testing");
		assertEquals(newTask.getContent(), state.getFloatingTasks().get(0).getContent());

		//testing add a task with deadline
		wm.process("add boxin is testing something on 11/11/11 11:11");
		String dateString = "11/11/11 11:11";
		Date date= TimeParser.stringToDate(dateString);
		//System.out.println(state.getNormalTasks().size());
		assertEquals(date, state.getNormalTasks().first().getEndDate());
		System.out.println(state.getNormalTasks().first().getContent());
		assertEquals("boxin is testing something", state.getNormalTasks().first().getContent());
		//parser bug for extra space
		
		//testing add another deadline task for correct order
		wm.process("add boxin is testing something very late on 12/11/11 11:11");
		assertEquals("boxin is testing something very late", state.getNormalTasks().last().getContent());
		//System.out.println(state.getNormalTasks().last().getContent());

		// testing the very late date -- boundary testing
		String smallest = TimeParser.dateToString(new Date(Long.MIN_VALUE));
		wm.process("add boxin is testing something very late2 on 12/11/35 11:12");
		//System.out.println(state.getNormalTasks().last().getContent());
		assertEquals("boxin is testing something very late2", state.getNormalTasks().last().getContent());
				
		//testing delete
		wm.process("delete 1 deadline");
		assertEquals("boxin is testing something very late", state.getNormalTasks().first().getContent());
					
		//testing update
		wm.process("update 1 deadline hahahaha");
		assertEquals("hahahaha", state.getNormalTasks().first().getContent());
		
		//testing tick
		wm.process("tick 1 deadline hahahaha");
		assertEquals(true, state.getNormalTasks().first().getIsFinished());
				
		//testing clear
		wm.process("clear deadline");
		assertEquals(0, state.getNormalTasks().size());
			
		//System.out.println(newTask.getContent());
		//System.out.println(state.getFloatingTasks().get(0).getContent());
	}

}