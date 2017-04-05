package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class EleStopCal {
	public static void main(String[] args) {
		int X = 5, Y = 200;
		int[] A = {40, 40, 100, 80, 20};
		int[] B = {3, 3, 2, 2, 3};
		Elevator elevator = new Elevator(X, Y, 3);
		elevator.addPerson(A, B);
		int count = elevator.stopCal();
		System.out.println(count);
	}
}

class Person {
	public int weight;
	public int targetFloor;
	public int actualFloor;
	
	public Person(int _w, int _tf) {
		weight = _w;
		targetFloor = _tf;
		actualFloor = 0;
	}
}

class Elevator {
	public int maxPerson;
	public int maxWeight;
	public int maxFloor;
	public Queue<Person> persons;
	public List<Person> passengers;
	
	public Elevator(int _mp, int _mw, int _mf) {
		maxWeight = _mw;
		maxPerson = _mp;
		maxFloor = _mf;
		persons = new LinkedList<Person>();
		passengers = new ArrayList<Person>();
	}
	
	public int stopCal() {
		int stops = 0;
		
		while (!persons.isEmpty()) {
			while (isOneMorePersonFitting()) {
				passengers.add(persons.poll());
			}
			Map<Integer, List<Person>> tarFloorGrouped = passengers.stream().collect(Collectors.groupingBy(p -> p.targetFloor));
			for (Map.Entry<Integer, List<Person>> entry: tarFloorGrouped.entrySet()) {
				stops++;
			}
			stops++;
			passengers.clear();
		}
		return stops;
	}
	
	public boolean isOneMorePersonFitting() {
		return !persons.isEmpty() && 
				passengers.stream().mapToInt(p -> p.weight).sum() + persons.peek().weight < maxWeight &&
				passengers.size() < maxPerson;
	}
	
	public void addPerson(int[] A, int[] B) {
		for (int i = 0; i < A.length; i++) {
			if (A[i] > maxWeight || B[i] > maxFloor) {
				continue;
			}
			persons.add(new Person(A[i], B[i]));
		}
	}
}