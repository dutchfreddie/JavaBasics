package concurrency.countdownlatch;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		
		// a mixed-double tennis game requires four players; so wait for four players
		// (i.e., four threads) to join to start the game
		System.out.println("Reserving tennis court \n As soon as four players arrive, game will start");
		CyclicBarrier barrier = new CyclicBarrier(4, new MixedDoubleTennisGame());
		new Player(barrier, "G I Joe");
		new Player(barrier, "Dora");
		new Player(barrier, "Tintin");
		new Player(barrier, "Barbie");

	}
}

	//The run() method in this thread should be called only when four players are ready to start the game
	class MixedDoubleTennisGame extends Thread {
		public void run() {
			System.out.println("All four players ready, game starts \n Love all...");
		}
	}
	
	// This thread simulates arrival of a player.
	// Once a player arrives, he/she should wait for other players to arrive
	class Player extends Thread {
		CyclicBarrier waitPoint;
		public Player(CyclicBarrier barrier, String name) {
			this.setName(name);
			waitPoint = barrier;
			this.start();
		}
		
		public void run() {
			Random rn = new Random();
			int low = 4000;int high = 10000;
			int time = rn.nextInt(high-low)+low;
			System.out.println("Player " + getName() + " is on his/her way ");
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Player " + getName() + " is ready  at time " + time);
			try {
				waitPoint.await(); // await for all four players to arrive
			} catch(BrokenBarrierException | InterruptedException exception) {
				System.out.println("An exception occurred while waiting... " + exception);
			}
		}
	}
